package com.skku.se7;

import com.skku.se7.service.JavaCodeCompiler;
import com.skku.se7.service.JavaRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JavaRunnerTest {

    @Autowired
    JavaRunner javaRunner;
    @Autowired
    JavaCodeCompiler javaCodeCompiler;
    @Test
    void findClassNameTest() throws Exception{
        String javaCode = "public class TestJavaRunner{"
                + "public static void main(String[] args) {"
                + "System.out.println(\"Wow it woks!!\");"
                + "}"
                + "}";
        String className = javaCodeCompiler.findClassName(javaCode);
        Assertions.assertEquals(className, "TestJavaRunner");
    }

    @Test
    void createFileTest() throws Exception{
        String javaCode = "public class TestJavaRunner{"
                + "public static void main(String[] args) {"
                + "System.out.println(\"Wow it woks!!\");"
                + "}"
                + "}";
        String curPath = javaCodeCompiler.getCurPath();
        String className = javaCodeCompiler.findClassName(javaCode);
        String filePath = javaCodeCompiler.createFile(className, javaCode);
        javaCodeCompiler.compileSourceFile(filePath);
    }

    @Test
    void compilerAndRunJavaCodeTest() throws Exception {
        String javaCode = "public class TestJavaRunner{"
                + "public static void main(String[] args) {"
                + "System.out.println(\"Wow it woks!!\");"
                + "}"
                + "}";
        String curPath = javaCodeCompiler.getCurPath();
        String className = javaCodeCompiler.findClassName(javaCode);
        Assertions.assertEquals(className, "TestJavaRunner");
        String filePath = javaCodeCompiler.createFile(className, javaCode);
        javaCodeCompiler.compileSourceFile(filePath);
        //컴파일 완료
        javaRunner.loadFileAndExecute(curPath, className);
    }
}
