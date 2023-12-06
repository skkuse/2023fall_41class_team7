package com.skku.se7.service;

import com.skku.se7.service.converter.code.JavaCodeCompiler;
import com.skku.se7.service.converter.code.JavaRunner;
import com.skku.se7.service.converter.code.CodeConverter;
import com.skku.se7.service.converter.code.JavaValidator;
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
    JavaValidator javaValidator;

    @Autowired
    CodeConverter CodeConverter;
    @Test
    void findClassNameTest() throws Exception{
        String javaCode = "public class TestJavaRunner{"
                + "public static void main(String[] args) {"
                + "System.out.println(\"Wow it woks!!\");"
                + "}"
                + "}";
        String[] parsedUserCode = javaCodeCompiler.parseUserCode(javaCode);
        String className = javaCodeCompiler.findClassName(parsedUserCode);
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
        String[] parsedUserCode = javaCodeCompiler.parseUserCode(javaCode);
        String className = javaCodeCompiler.findClassName(parsedUserCode);
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
        String[] parsedUserCode = javaCodeCompiler.parseUserCode(javaCode);
        String className = javaCodeCompiler.findClassName(parsedUserCode);
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
        String[] parsedUserCode = javaCodeCompiler.parseUserCode(javaCode);
        String className = javaCodeCompiler.findClassName(parsedUserCode);
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
    void synchronousTest() throws Exception {
        String javaCode = " public class TestJavaRunner{"
                + " public static void main(String[] args) {"
                + " System.out.println(\"Wow it woks!!\");"
                + " for(int i=0; i<13000; i++){"
                + " System.out.println(\"abcd\");"
                + " }"
                + " long j=-100000;"
                + " for(long k=-100000; k<1000000000; k++){"
                + " j++;"
                + " }"
                + " }"
                + " }";
        long a = CodeConverter.executeSynchronously(javaCode);

        System.out.println("time is " +  a);
    }

    @Test
    void validateTest(){
        String javaCode = "import.java.lang.Runtime;"
                + " public class TestJavaRunner{"
                + " public static void main(String[] args) {"
                + " System.out.println(\"Wow it woks!!\");"
                + " for(int i=0; i<13000; i++){"
                + " System.out.println(\"abcd\");"
                + " }"
                + " long j=-100000;"
                + " for(long k=-100000; k<1000000000; k++){"
                + " j++;"
                + " }"
                + " }"
                + " }";
        String[] arr = javaCode.split("[.]|[)]|[(]| |[{]|[}]|\n|;");
        boolean isMalicious = javaValidator.isMalicious(arr);
        Assertions.assertEquals(isMalicious, false);
    }
}
