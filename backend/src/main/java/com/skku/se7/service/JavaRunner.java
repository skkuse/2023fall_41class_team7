package com.skku.se7.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;

@Slf4j
@Component
public class JavaRunner {
    public static long loadFileAndExecute(String curPath, String className) throws Exception {
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(curPath).toURI().toURL()});
        Class<?> cls = Class.forName(className, true, classLoader);
        Object obj = cls.newInstance();
        Method method = obj.getClass().getMethod("main", String[].class);

        String[] arg = new String[0];
        //CheckTimeConsumed
        long startTime = System.currentTimeMillis();
        method.invoke(null, (Object) arg);//main함수 실행
        long timeConsumed = System.currentTimeMillis()-startTime;
        //
        return timeConsumed;
    }

    public static void deleteFile(String filePath, String delPath){
        File file = new File(filePath);//.java파일 삭제
        if(file.exists()){
            file.delete();
            //System.out.println("파일 삭제 완료!!");
        }

        file = new File(delPath);//.class파일 삭제
        if(file.exists()){
            file.delete();
        }
    }
}
