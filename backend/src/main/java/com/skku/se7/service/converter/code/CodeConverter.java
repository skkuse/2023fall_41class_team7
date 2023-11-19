package com.skku.se7.service.converter.code;

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

    public synchronized Long executeSynchronously(String code) throws Exception {
        String curPath = javaCodeCompiler.getCurPath();
        String className = javaCodeCompiler.findClassName(code);
        String[] arrPath = javaCodeCompiler.createFile(className, code);
        String filePath = arrPath[0];
        String delPath = arrPath[1];
        javaCodeCompiler.compileSourceFile(filePath);
        //compile 완료

        long timeConsumed = javaRunner.loadFileAndExecute(curPath, className);
        //JVM에 로드 후 main함수 실행
        return timeConsumed;
    }
}
