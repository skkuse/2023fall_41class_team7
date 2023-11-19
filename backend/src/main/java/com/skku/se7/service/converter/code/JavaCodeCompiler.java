package com.skku.se7.service.converter.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.tools.*;
import java.io.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JavaCodeCompiler {
    private static String curPath = JavaCodeCompiler.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    public String getCurPath(){
        return  curPath;
    }

    public static String findClassName(String code){
        String[] arr = code.split("[.]|[)]|[(]| |[{]|[}]");
        String className = null;
        for(int idx=0; idx<arr.length; idx++){
            if(arr[idx].equals("class") && idx+1<arr.length){
                className = arr[idx+1];
            }
            if (arr[idx].equals("main")) break;
        }
        return className;
    }

    public static String[] createFile(String className, String code){
        //String curPath = JavaCodeCompiler.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String filePath = curPath + className + ".java";
        String delPath = curPath + className + ".class";
        String[] arrPath = new String[2];
        arrPath[0] = filePath;
        arrPath[1] = delPath;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            writer.write(code);//파일에 코드 내용 작성
            //System.out.println("자바 파일 생성" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrPath;
    }

    public static void compileSourceFile(String filePath){
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        File sourceFile = new File(filePath);

        try {
            compiler.run(null, System.out, System.out, sourceFile.getPath());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
