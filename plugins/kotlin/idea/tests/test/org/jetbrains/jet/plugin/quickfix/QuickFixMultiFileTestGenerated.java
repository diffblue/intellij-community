/*
 * Copyright 2010-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.plugin.quickfix;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.io.File;
import java.util.regex.Pattern;
import org.jetbrains.jet.JetTestUtils;
import org.jetbrains.jet.test.InnerTestClasses;
import org.jetbrains.jet.test.TestMetadata;

import org.jetbrains.jet.plugin.quickfix.AbstractQuickFixMultiFileTest;

/** This class is generated by {@link org.jetbrains.jet.generators.tests.GenerateTests}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/quickfix")
@InnerTestClasses({QuickFixMultiFileTestGenerated.AddStarProjections.class, QuickFixMultiFileTestGenerated.AutoImports.class, QuickFixMultiFileTestGenerated.Modifiers.class, QuickFixMultiFileTestGenerated.Nullables.class, QuickFixMultiFileTestGenerated.TypeImports.class, QuickFixMultiFileTestGenerated.Variables.class})
public class QuickFixMultiFileTestGenerated extends AbstractQuickFixMultiFileTest {
    public void testAllFilesPresentInQuickfix() throws Exception {
        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/quickfix"), Pattern.compile("^(\\w+)\\.before\\.Main\\.kt$"), true);
    }
    
    @TestMetadata("idea/testData/quickfix/addStarProjections")
    @InnerTestClasses({})
    public static class AddStarProjections extends AbstractQuickFixMultiFileTest {
        public void testAllFilesPresentInAddStarProjections() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/quickfix/addStarProjections"), Pattern.compile("^(\\w+)\\.before\\.Main\\.kt$"), true);
        }
        
        public static Test innerSuite() {
            TestSuite suite = new TestSuite("AddStarProjections");
            suite.addTestSuite(AddStarProjections.class);
            return suite;
        }
    }
    
    @TestMetadata("idea/testData/quickfix/autoImports")
    public static class AutoImports extends AbstractQuickFixMultiFileTest {
        public void testAllFilesPresentInAutoImports() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/quickfix/autoImports"), Pattern.compile("^(\\w+)\\.before\\.Main\\.kt$"), true);
        }
        
        @TestMetadata("classImport.before.Main.kt")
        public void testClassImport() throws Exception {
            doTestWithExtraFile("idea/testData/quickfix/autoImports/classImport.before.Main.kt");
        }
        
        @TestMetadata("extensionFunctionImport.before.Main.kt")
        public void testExtensionFunctionImport() throws Exception {
            doTestWithExtraFile("idea/testData/quickfix/autoImports/extensionFunctionImport.before.Main.kt");
        }
        
        @TestMetadata("functionImport.before.Main.kt")
        public void testFunctionImport() throws Exception {
            doTestWithExtraFile("idea/testData/quickfix/autoImports/functionImport.before.Main.kt");
        }
        
        @TestMetadata("importInFirstPartInQualifiedExpression.before.Main.kt")
        public void testImportInFirstPartInQualifiedExpression() throws Exception {
            doTestWithExtraFile("idea/testData/quickfix/autoImports/importInFirstPartInQualifiedExpression.before.Main.kt");
        }
        
        @TestMetadata("importInFirstPartInUserType.before.Main.kt")
        public void testImportInFirstPartInUserType() throws Exception {
            doTestWithExtraFile("idea/testData/quickfix/autoImports/importInFirstPartInUserType.before.Main.kt");
        }
        
        @TestMetadata("noImportForFunInQualifiedNotFirst.before.Main.kt")
        public void testNoImportForFunInQualifiedNotFirst() throws Exception {
            doTestWithExtraFile("idea/testData/quickfix/autoImports/noImportForFunInQualifiedNotFirst.before.Main.kt");
        }
        
        @TestMetadata("noImportForPrivateClass.before.Main.kt")
        public void testNoImportForPrivateClass() throws Exception {
            doTestWithExtraFile("idea/testData/quickfix/autoImports/noImportForPrivateClass.before.Main.kt");
        }
        
        @TestMetadata("noImportInImports.before.Main.kt")
        public void testNoImportInImports() throws Exception {
            doTestWithExtraFile("idea/testData/quickfix/autoImports/noImportInImports.before.Main.kt");
        }
        
        @TestMetadata("noImportInQualifiedExpressionNotFirst.before.Main.kt")
        public void testNoImportInQualifiedExpressionNotFirst() throws Exception {
            doTestWithExtraFile("idea/testData/quickfix/autoImports/noImportInQualifiedExpressionNotFirst.before.Main.kt");
        }
        
        @TestMetadata("noImportInQualifiedUserTypeNotFirst.before.Main.kt")
        public void testNoImportInQualifiedUserTypeNotFirst() throws Exception {
            doTestWithExtraFile("idea/testData/quickfix/autoImports/noImportInQualifiedUserTypeNotFirst.before.Main.kt");
        }
        
        @TestMetadata("noImportInSafeQualifiedExpressionNotFirst.before.Main.kt")
        public void testNoImportInSafeQualifiedExpressionNotFirst() throws Exception {
            doTestWithExtraFile("idea/testData/quickfix/autoImports/noImportInSafeQualifiedExpressionNotFirst.before.Main.kt");
        }
        
        @TestMetadata("packageClass.before.Main.kt")
        public void testPackageClass() throws Exception {
            doTestWithExtraFile("idea/testData/quickfix/autoImports/packageClass.before.Main.kt");
        }
        
    }
    
    @TestMetadata("idea/testData/quickfix/modifiers")
    @InnerTestClasses({Modifiers.AddOpenToClassDeclaration.class})
    public static class Modifiers extends AbstractQuickFixMultiFileTest {
        public void testAllFilesPresentInModifiers() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/quickfix/modifiers"), Pattern.compile("^(\\w+)\\.before\\.Main\\.kt$"), true);
        }
        
        @TestMetadata("idea/testData/quickfix/modifiers/addOpenToClassDeclaration")
        @InnerTestClasses({AddOpenToClassDeclaration.FinalJavaClass.class})
        public static class AddOpenToClassDeclaration extends AbstractQuickFixMultiFileTest {
            public void testAllFilesPresentInAddOpenToClassDeclaration() throws Exception {
                JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/quickfix/modifiers/addOpenToClassDeclaration"), Pattern.compile("^(\\w+)\\.before\\.Main\\.kt$"), true);
            }
            
            @TestMetadata("idea/testData/quickfix/modifiers/addOpenToClassDeclaration/finalJavaClass")
            @InnerTestClasses({FinalJavaClass.JavaCode.class})
            public static class FinalJavaClass extends AbstractQuickFixMultiFileTest {
                public void testAllFilesPresentInFinalJavaClass() throws Exception {
                    JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/quickfix/modifiers/addOpenToClassDeclaration/finalJavaClass"), Pattern.compile("^(\\w+)\\.before\\.Main\\.kt$"), true);
                }
                
                @TestMetadata("idea/testData/quickfix/modifiers/addOpenToClassDeclaration/finalJavaClass/javaCode")
                @InnerTestClasses({})
                public static class JavaCode extends AbstractQuickFixMultiFileTest {
                    public void testAllFilesPresentInJavaCode() throws Exception {
                        JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/quickfix/modifiers/addOpenToClassDeclaration/finalJavaClass/javaCode"), Pattern.compile("^(\\w+)\\.before\\.Main\\.kt$"), true);
                    }
                    
                    public static Test innerSuite() {
                        TestSuite suite = new TestSuite("JavaCode");
                        suite.addTestSuite(JavaCode.class);
                        return suite;
                    }
                }
                
                public static Test innerSuite() {
                    TestSuite suite = new TestSuite("FinalJavaClass");
                    suite.addTestSuite(FinalJavaClass.class);
                    suite.addTest(JavaCode.innerSuite());
                    return suite;
                }
            }
            
            public static Test innerSuite() {
                TestSuite suite = new TestSuite("AddOpenToClassDeclaration");
                suite.addTestSuite(AddOpenToClassDeclaration.class);
                suite.addTest(FinalJavaClass.innerSuite());
                return suite;
            }
        }
        
        public static Test innerSuite() {
            TestSuite suite = new TestSuite("Modifiers");
            suite.addTestSuite(Modifiers.class);
            suite.addTest(AddOpenToClassDeclaration.innerSuite());
            return suite;
        }
    }
    
    @TestMetadata("idea/testData/quickfix/nullables")
    @InnerTestClasses({})
    public static class Nullables extends AbstractQuickFixMultiFileTest {
        public void testAllFilesPresentInNullables() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/quickfix/nullables"), Pattern.compile("^(\\w+)\\.before\\.Main\\.kt$"), true);
        }
        
        public static Test innerSuite() {
            TestSuite suite = new TestSuite("Nullables");
            suite.addTestSuite(Nullables.class);
            return suite;
        }
    }
    
    @TestMetadata("idea/testData/quickfix/typeImports")
    public static class TypeImports extends AbstractQuickFixMultiFileTest {
        public void testAllFilesPresentInTypeImports() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/quickfix/typeImports"), Pattern.compile("^(\\w+)\\.before\\.Main\\.kt$"), true);
        }
        
        @TestMetadata("importFromAnotherFile.before.Main.kt")
        public void testImportFromAnotherFile() throws Exception {
            doTestWithExtraFile("idea/testData/quickfix/typeImports/importFromAnotherFile.before.Main.kt");
        }
        
    }
    
    @TestMetadata("idea/testData/quickfix/variables")
    @InnerTestClasses({})
    public static class Variables extends AbstractQuickFixMultiFileTest {
        public void testAllFilesPresentInVariables() throws Exception {
            JetTestUtils.assertAllTestsPresentByMetadata(this.getClass(), "org.jetbrains.jet.generators.tests.GenerateTests", new File("idea/testData/quickfix/variables"), Pattern.compile("^(\\w+)\\.before\\.Main\\.kt$"), true);
        }
        
        public static Test innerSuite() {
            TestSuite suite = new TestSuite("Variables");
            suite.addTestSuite(Variables.class);
            return suite;
        }
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite("QuickFixMultiFileTestGenerated");
        suite.addTestSuite(QuickFixMultiFileTestGenerated.class);
        suite.addTest(AddStarProjections.innerSuite());
        suite.addTestSuite(AutoImports.class);
        suite.addTest(Modifiers.innerSuite());
        suite.addTest(Nullables.innerSuite());
        suite.addTestSuite(TypeImports.class);
        suite.addTest(Variables.innerSuite());
        return suite;
    }
}
