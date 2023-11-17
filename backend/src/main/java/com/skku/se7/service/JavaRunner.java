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
    public static void loadFileAndExecute(String curPath, String className) throws Exception {
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(curPath).toURI().toURL()});
        Class<?> cls = Class.forName(className, true, classLoader);
        Object obj = cls.newInstance();
        Method method = obj.getClass().getMethod("main", String[].class);

        String[] arg = new String[0];
        method.invoke(null, (Object) arg);//main함수 실행
    }
}
