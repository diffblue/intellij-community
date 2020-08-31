// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.execution.junit2.inspection;

import com.intellij.codeInsight.daemon.ImplicitUsageProvider;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.PsiEnumConstantImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.LocalSearchScope;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

public class JunitEnumSourceImplicitUsageProvider implements ImplicitUsageProvider {

  private static final String ENUM_SOURCE = "org.junit.jupiter.params.provider.EnumSource";

  @Override
  public boolean isImplicitUsage(@NotNull PsiElement element) {
    if (element instanceof PsiEnumConstantImpl) {
      PsiClass psiClass = ((PsiEnumConstantImpl)element).getContainingClass();
      String className = psiClass != null ? psiClass.getName() : null;
      if (className == null) return false;
      SearchScope useScope = psiClass.getUseScope();

      if (!(useScope instanceof LocalSearchScope)) {
        PsiSearchHelper searchHelper = PsiSearchHelper.getInstance(psiClass.getProject());
        PsiSearchHelper.SearchCostResult cheapEnough = searchHelper.isCheapEnoughToSearch(className, (GlobalSearchScope)useScope, null,
                                                                                          null);
        if (cheapEnough == PsiSearchHelper.SearchCostResult.ZERO_OCCURRENCES ||
            cheapEnough == PsiSearchHelper.SearchCostResult.TOO_MANY_OCCURRENCES) {
          return false;
        }
      }
      return ReferencesSearch.search(psiClass, useScope, false)
        .anyMatch(reference -> {
          PsiElement referenceElement = reference.getElement();
          return isReferencedInsideEnumSourceAnnotation(referenceElement);
        });
    }
    return false;
  }

  private static boolean isReferencedInsideEnumSourceAnnotation(PsiElement referenceElement) {
    PsiAnnotation annotation = PsiTreeUtil.getParentOfType(referenceElement, PsiAnnotation.class);
    if (annotation != null) {
      String annotationName = annotation.getQualifiedName();
      if (ENUM_SOURCE.equals(annotationName) && annotation.getAttributes().size() == 1) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isImplicitRead(@NotNull PsiElement element) {
    return false;
  }

  @Override
  public boolean isImplicitWrite(@NotNull PsiElement element) {
    return false;
  }
}
