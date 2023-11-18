package com.skku.se7;

import com.skku.se7.service.JavaCodeCompiler;
import com.skku.se7.service.JavaRunner;
import com.skku.se7.service.synchronizedJavaRunner;
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

    @Autowired
    synchronizedJavaRunner synchronizedJavaRunner;
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
        String[] arrPath = javaCodeCompiler.createFile(className, javaCode);
        String filePath = arrPath[0];
        String delPath = arrPath[1];
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
        String[] arrPath = javaCodeCompiler.createFile(className, javaCode);
        String filePath = arrPath[0];
        String delPath = arrPath[1];
        javaCodeCompiler.compileSourceFile(filePath);
        //컴파일 완료
        javaRunner.loadFileAndExecute(curPath, className);
    }

    @Test
    void deleteFileTest() throws Exception{
        String javaCode = "public class TestJavaRunner{"
                + "public static void main(String[] args) {"
                + "System.out.println(\"Wow it woks!!\");"
                + "}"
                + "}";
        String curPath = javaCodeCompiler.getCurPath();
        String className = javaCodeCompiler.findClassName(javaCode);
        Assertions.assertEquals(className, "TestJavaRunner");
        String[] arrPath = javaCodeCompiler.createFile(className, javaCode);
        String filePath = arrPath[0];
        String delPath = arrPath[1];
        javaCodeCompiler.compileSourceFile(filePath);
        //컴파일 완료
        javaRunner.loadFileAndExecute(curPath, className);
        javaRunner.deleteFile(filePath, delPath);
    }

    @Test
    void synchronousTest() throws Exception{
        String javaCode = "public class TestJavaRunner{"
                + "public static void main(String[] args) {"
                + "System.out.println(\"Wow it woks!!\");"
                + "}"
                + "}";
        synchronizedJavaRunner.executeSynchronously(javaCode);
    }
}
