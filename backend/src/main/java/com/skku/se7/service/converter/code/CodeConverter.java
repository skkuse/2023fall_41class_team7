package com.skku.se7.service.converter.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CodeConverter {
    private final JavaCodeCompiler javaCodeCompiler;
    private final JavaRunner javaRunner;
    private final JavaValidator javaValidator;

    /**
     * 1. Compile 실패
     * 2. 허가 안되는 code
     * 3. 시간초과
     */

    public synchronized Long executeSynchronously(String code) throws Exception {
        long timeLimit = 10000;
        String curPath = javaCodeCompiler.getCurPath();
        //System.out.println("cur path is : " + curPath);
        String[] parsedUserCode = javaCodeCompiler.parseUserCode(code);
        if(javaValidator.isMalicious(parsedUserCode)){
            throw new Exception();
        }

        """
                public clas Main { public static void main(String[] args) { int i = 1; float f = 1.1f; double d = 1.2; boolean b = true; char c = 'a'; System. out.println(i); System. out.println(f); System. out.println(d); System. out.println(b); System. out.println(c); } }
                """
        String className = javaCodeCompiler.findClassName(parsedUserCode);
        if(className == null) throw new
        String[] arrPath = javaCodeCompiler.createFile(className, code);
        String filePath = arrPath[0];//.java
        String delPath = arrPath[1];//.class

        javaCodeCompiler.compileSourceFile(filePath);
        //compile 완료

        try {
            long timeConsumed = javaRunner.createProcessAndExecute(timeLimit, curPath, className);
            if(timeConsumed == -1L) throw new TimeoutException();
            return timeConsumed;
        }finally {
            javaRunner.deleteFile(filePath, delPath);
        }
    }
}


