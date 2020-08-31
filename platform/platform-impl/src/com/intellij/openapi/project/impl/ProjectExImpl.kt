// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.openapi.project.impl

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.impl.LaterInvocator
import com.intellij.openapi.components.StorageScheme
import com.intellij.openapi.components.impl.stores.IProjectStore
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.project.ProjectStoreOwner
import com.intellij.serviceContainer.AlreadyDisposedException
import com.intellij.serviceContainer.ComponentManagerImpl
import com.intellij.util.ExceptionUtil
import com.intellij.util.TimedReference
import com.intellij.util.concurrency.SynchronizedClearableLazy
import com.intellij.util.io.systemIndependentPath
import com.intellij.util.messages.impl.MessageBusEx
import org.jetbrains.annotations.ApiStatus
import org.jetbrains.annotations.TestOnly
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicReference

private val DISPOSE_EARLY_DISPOSABLE_TRACE = Key.create<String>("ProjectImpl.DISPOSE_EARLY_DISPOSABLE_TRACE")

@ApiStatus.Internal
open class ProjectExImpl(filePath: Path, projectName: String?) : ProjectImpl(ApplicationManager.getApplication() as ComponentManagerImpl), ProjectStoreOwner {
  private val earlyDisposable: AtomicReference<Disposable?> = AtomicReference(Disposer.newDisposable())

  @Volatile
  var isTemporarilyDisposed = false
    private set

  private val isLight: Boolean

  private var componentStoreValue = SynchronizedClearableLazy {
    ApplicationManager.getApplication().getService(ProjectStoreFactory::class.java).createStore(this)
  }

  init {
    @Suppress("LeakingThis")
    putUserData(CREATION_TIME, System.nanoTime())

    @Suppress("LeakingThis")
    registerServiceInstance(Project::class.java, this, fakeCorePluginDescriptor)

    myName = projectName
    // light project may be changed later during test, so we need to remember its initial state
    //noinspection TestOnlyProblems
    // light project may be changed later during test, so we need to remember its initial state
    isLight = ApplicationManager.getApplication().isUnitTestMode && filePath.toString().contains(LIGHT_PROJECT_NAME)
  }

  final override var componentStore: IProjectStore
    get() = componentStoreValue.value
    set(value) {
      if (componentStoreValue.isInitialized()) {
        throw java.lang.IllegalStateException("store is already initialized")
      }
      componentStoreValue.value = value
    }

  final override fun getStateStore() = componentStore

  final override fun getProjectFilePath() = componentStore.projectFilePath

  final override fun getProjectFile(): VirtualFile? {
    return LocalFileSystem.getInstance().findFileByPath(componentStore.projectFilePath)
  }

  final override fun getBaseDir(): VirtualFile? {
    return LocalFileSystem.getInstance().findFileByNioFile(componentStore.projectBasePath)
  }

  final override fun getBasePath() = componentStore.projectBasePath.systemIndependentPath

  override fun getPresentableUrl(): String? {
    val store = componentStore
    return if (store.storageScheme == StorageScheme.DIRECTORY_BASED) store.projectBasePath.systemIndependentPath else store.projectFilePath
  }

  override fun getLocationHash(): String {
    val prefix = if (componentStore.storageScheme == StorageScheme.DIRECTORY_BASED) "" else name
    return "$prefix${Integer.toHexString((presentableUrl ?: name).hashCode())}"
  }

  final override fun getWorkspaceFile(): VirtualFile? {
    return LocalFileSystem.getInstance().findFileByPath(componentStore.workspaceFilePath ?: return null)
  }

  final override fun isLight() = isLight

  @ApiStatus.Internal
  final override fun activityNamePrefix() = "project "

  @TestOnly
  fun setTemporarilyDisposed(value: Boolean) {
    if (isTemporarilyDisposed == value) {
      return
    }

    if (value && super.isDisposed()) {
      throw IllegalStateException("Project was already disposed, flag temporarilyDisposed cannot be set to `true`")
    }

    if (!value) {
      val newDisposable = Disposer.newDisposable()
      if (!earlyDisposable.compareAndSet(null, newDisposable)) {
        throw IllegalStateException("earlyDisposable must be null on second opening of light project")
      }
    }

    // Must be not only on temporarilyDisposed = true, but also on temporarilyDisposed = false,
    // because events fired for temporarilyDisposed project between project close and project open and it can lead to cache population.
    // Message bus implementation can be complicated to add owner.isDisposed check before getting subscribers, but as bus is a very important subsystem,
    // better to not add any non-production logic

    // light project is not disposed, so, subscriber cache contains handlers that will handle events for a temporarily disposed project,
    // so, we clear subscriber cache. `isDisposed` for project returns `true` if `temporarilyDisposed`, so, handler will be not added.
    (messageBus as MessageBusEx).clearAllSubscriberCache()
    isTemporarilyDisposed = value
  }

  @ApiStatus.Experimental
  @ApiStatus.Internal
  final override fun getEarlyDisposable(): Disposable {
    if (isDisposed) {
      throw AlreadyDisposedException("$this is disposed already")
    }
    return earlyDisposable.get() ?: throw createEarlyDisposableError("earlyDisposable is null for")
  }

  fun disposeEarlyDisposable() {
    if (LOG.isDebugEnabled || ApplicationManager.getApplication().isUnitTestMode) {
      LOG.debug("dispose early disposable: ${toString()}")
    }

    val disposable = earlyDisposable.getAndSet(null) ?: throw createEarlyDisposableError("earlyDisposable was already disposed")
    if (ApplicationManager.getApplication().isUnitTestMode) {
      putUserData(DISPOSE_EARLY_DISPOSABLE_TRACE, ExceptionUtil.currentStackTrace())
    }
    Disposer.dispose(disposable)
  }

  private fun createEarlyDisposableError(error: String): RuntimeException {
    return IllegalStateException("$error for ${toString()}\n---begin of dispose trace--" +
                                getUserData(DISPOSE_EARLY_DISPOSABLE_TRACE) +
                                "}---end of dispose trace---\n")
  }

  final override fun isDisposed(): Boolean {
    return super.isDisposed() || isTemporarilyDisposed
  }

  @Synchronized
  final override fun dispose() {
    val app = ApplicationManager.getApplication()
    // dispose must be under write action
    app.assertWriteAccessAllowed()
    val projectManager = ProjectManager.getInstance() as ProjectManagerImpl

    // can call dispose only via com.intellij.ide.impl.ProjectUtil.closeAndDispose()
    if (projectManager.isProjectOpened(this)) {
      throw IllegalStateException("Must call .dispose() for a closed project only. See ProjectManager.closeProject() or ProjectUtil.closeAndDispose().")
    }

    super.dispose()

    componentStoreValue.valueIfInitialized?.release()

    if (!app.isDisposed) {
      @Suppress("DEPRECATION")
      app.messageBus.syncPublisher(ProjectLifecycleListener.TOPIC).afterProjectClosed(this)
    }
    projectManager.updateTheOnlyProjectField()
    TimedReference.disposeTimed()
    LaterInvocator.purgeExpiredItems()
  }

  final override fun toString(): String {
    val store = componentStoreValue.valueIfInitialized
    return "Project(name=$myName, containerState=${if (isTemporarilyDisposed) "disposed temporarily" else containerStateName}" +
           ", componentStore=" + (if (store == null) "<not initialized>" else if (store.storageScheme == StorageScheme.DIRECTORY_BASED) store.projectBasePath.toString() else store.projectFilePath) + ")"
  }
}