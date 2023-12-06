package com.skku.se7.service;

import com.skku.se7.error.exceptions.CannotFindMainMethodException;
import com.skku.se7.error.exceptions.CompileException;
import com.skku.se7.error.exceptions.MaliciousCodeException;
import com.skku.se7.error.exceptions.TimeOutException;
import com.skku.se7.service.converter.code.JavaCodeCompiler;
import com.skku.se7.service.converter.code.JavaRunner;
import com.skku.se7.service.converter.code.CodeConverter;
import com.skku.se7.service.converter.code.JavaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeoutException;

@SpringBootTest
public class JavaRunnerTest {

    @Autowired
    JavaRunner javaRunner;
    @Autowired
    JavaCodeCompiler javaCodeCompiler;

    @Autowired
    JavaValidator javaValidator;

    @Autowired
    CodeConverter codeConverter;
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

    /**
     * input :
     * expect result :
     */
    @Test
    public void findClassName_NoMainMethod_CaanotFindMainMethodException() throws Exception{
        //given
        String javaCode = "public class TestJavaRunner{"
                + "public static void min(String[] args) {"
                + "System.out.println(\"Wow it woks!!\");"
                + "}"
                + "}";

        //mocking

        //when
        Assertions.assertThrows(
                CannotFindMainMethodException.class, () ->
                        codeConverter.executeSynchronously(javaCode));
        //then

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
        long a = codeConverter.executeSynchronously(javaCode);

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
        Assertions.assertThrows(MaliciousCodeException.class, () -> javaValidator.isMalicious(arr));
    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void compileFail() throws Exception{
        //given
        String wrongAccessDefinition = " publc class TestJavaRunner{"
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
        String wrongClassIndicator = " public clas TestJavaRunner{"
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
        String wrongMethodParam = " public class TestJavaRunner{"
                + " public static void main(String] args) {"
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
        String wrongIntegerBound = " public class TestJavaRunner{"
                + " public static void main(String[] args) {"
                + " System.out.println(\"Wow it woks!!\");"
                + " for(int i=0; i<10000003000; i++){"
                + " System.out.println(\"abcd\");"
                + " }"
                + " long j=-100000;"
                + " for(long k=-100000; k<1000000000; k++){"
                + " j++;"
                + " }"
                + " }"
                + " }";
        String wrongBoundary = " public class TestJavaRunner{"
                + " public static void main(String[] args) {"
                + " System.out.println(\"Wow it woks!!\");"
                + " for(int i=0; i<13000; i++){"
                + " System.out.println(\"abcd\");"
                + " }"
                + " long j=-100000;"
                + " for(long k=-100000; k<1000000000; k++){"
                + " j++;"
                + " }"
                + ""
                + " }";

        //when
        Assertions.assertThrows(CompileException.class, () -> codeConverter.executeSynchronously(wrongBoundary));
        Assertions.assertThrows(CompileException.class, () -> codeConverter.executeSynchronously(wrongAccessDefinition));
        Assertions.assertThrows(CompileException.class, () -> codeConverter.executeSynchronously(wrongMethodParam));
        Assertions.assertThrows(CompileException.class, () -> codeConverter.executeSynchronously(wrongIntegerBound));

        Assertions.assertThrows(CannotFindMainMethodException.class, () -> codeConverter.executeSynchronously(wrongClassIndicator));
        //then

    }

    /**
     * input :
     * expect result :
     */
    @Test
    public void Timeout() throws Exception{
        //given
        String infiniteLoop = " public class TestJavaRunner{"
                + " public static void main(String[] args) {"
                + " System.out.println(\"Wow it woks!!\");"
                + " for(int i=0; i<13000; i++){"
                + " System.out.println(\"abcd\");"
                + " }"
                + " long j=-100000;"
                + " while(true){"
                + " System.out.println(j);"
                + " }"
                + ""
                + " }";

        //when

        //then
        Assertions.assertThrows(TimeOutException.class, () -> codeConverter.executeSynchronously(infiniteLoop));

    }
}
