package com.skku.se7.service.converter.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
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
import java.util.concurrent.TimeUnit;

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

    public static long createProcessAndExecute(long timeLimit, String curPath, String className) throws Exception{//return time consumed by User code
        long timeConsumed = 0;

        try {
            String[] cmd = {"/bin/bash", "-c", "cd " +curPath + " && java " + className};
            long startTime = System.currentTimeMillis();
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor(timeLimit, TimeUnit.MILLISECONDS);
            timeConsumed = System.currentTimeMillis()-startTime;

            if(process.isAlive()){
                process.destroy();
                timeConsumed = -1;//if process exceed limit time, return -1
            }else{
                /*
                InputStream inputStream = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); // 결과 출력
                }
                */
            }
        }catch (IOException | InterruptedException e){}

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
