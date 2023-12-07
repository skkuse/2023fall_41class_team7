package com.skku.se7.service.converter.code;

import com.skku.se7.error.exceptions.CannotFindMainMethodException;
import com.skku.se7.error.exceptions.TimeOutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
        String[] parsedUserCode = JavaCodeCompiler.parseUserCode(code);
        JavaValidator.isMalicious(parsedUserCode);
        String className = JavaCodeCompiler.findClassName(parsedUserCode);
        if (className == null) throw new CannotFindMainMethodException();
        String[] arrPath = JavaCodeCompiler.createFile(className, code);
        String filePath = arrPath[0];//.java
        String delPath = arrPath[1];//.class

        JavaCodeCompiler.compileSourceFile(filePath);
        //compile 완료

        try {
            long timeConsumed = JavaRunner.createProcessAndExecute(timeLimit, curPath, className);
            if (timeConsumed == -1L) throw new TimeOutException();
            return timeConsumed;
        } finally {
            JavaRunner.deleteFile(filePath, delPath);
        }
    }
}


