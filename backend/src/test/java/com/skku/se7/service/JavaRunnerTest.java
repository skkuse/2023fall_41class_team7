package com.skku.se7.service;

import com.skku.se7.service.converter.code.JavaCodeCompiler;
import com.skku.se7.service.converter.code.JavaRunner;
import com.skku.se7.service.converter.code.CodeConverter;
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
    CodeConverter CodeConverter;
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
        //javaRunner.loadFileAndExecute(curPath, className);
        long returnTime = javaRunner.createProcessAndExecute(2000, curPath, className);
        //System.out.println("time is : " + returnTime + "(Milisecond)");
        javaRunner.deleteFile(filePath, delPath);
    }

    @Test
    void synchronousTest() throws Exception{
        String javaCode = "public class TestJavaRunner{"
                + "public static void main(String[] args) {"
                + "System.out.println(\"Wow it woks!!\");"
                + "for(int i=0; i<1000000; i++){"
                + "System.out.println(\"abcd\");"
                + "}"
                + "while(true){}"
                + "}"
                + "}";
        long a = CodeConverter.executeSynchronously(javaCode);

        System.out.println("time is " +  a);
    }
}
