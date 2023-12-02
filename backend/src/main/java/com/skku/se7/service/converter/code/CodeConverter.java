package com.skku.se7.service.converter.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Time;

@Slf4j
@Component
@RequiredArgsConstructor
public class CodeConverter {
    private final JavaCodeCompiler javaCodeCompiler;
    private final JavaRunner javaRunner;
    private final JavaValidator javaValidator;

    public synchronized Long executeSynchronously(String code) throws Exception {
        long timeLimit = 5000;
        String curPath = javaCodeCompiler.getCurPath();
        //System.out.println("cur path is : " + curPath);
        String className = javaCodeCompiler.findClassName(code);
        String[] arrPath = javaCodeCompiler.createFile(className, code);
        String filePath = arrPath[0];//.java
        String delPath = arrPath[1];//.class

        javaCodeCompiler.compileSourceFile(filePath);
        //compile 완료

        //long timeConsumed = javaRunner.loadFileAndExecute(curPath, className);
        //JVM에 로드 후 main함수 실행
        long timeConsumed = javaRunner.createProcessAndExecute(timeLimit, curPath, className);
        javaRunner.deleteFile(filePath, delPath);
        return timeConsumed;
    }
}

