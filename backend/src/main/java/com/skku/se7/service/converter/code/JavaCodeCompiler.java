package com.skku.se7.service.converter.code;

import com.skku.se7.error.exceptions.CompileException;
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

    public static String[] parseUserCode(String code){
        String[] arr = code.split("[.]|[)]|[(]| |[{]|[}]|\n|;");
        return arr;
    }

    public static String findClassName(String[] parsedUserCode){
        String className = null;

        for(int idx=0; idx<parsedUserCode.length; idx++){
            if(parsedUserCode[idx].equals("class") && idx+1<parsedUserCode.length){
                className = parsedUserCode[idx+1];
            }
            if (parsedUserCode[idx].equals("main")) return className;
        }
        return null;
    }

    public static String[] createFile(String className, String code){
        //String curPath = JavaCodeCompiler.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String filePath = curPath + className + ".java";
        String delPath = curPath + className + ".class";

        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }

        file = new File(delPath);
        if(file.exists()){
            file.delete();
        }

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

        OutputStream outputStream = new OutputStream() {
            private StringBuilder sb = new StringBuilder();

            @Override
            public void write(int b) throws IOException {
                this.sb.append((char) b);
            }

            @Override
            public String toString() {
                return sb.toString();
            }
        };
        compiler.run(null, System.out, outputStream, sourceFile.getPath());
        if(!outputStream.toString().isBlank()) {
            log.warn("outputStream.toString() : {}", outputStream.toString());
            throw new CompileException();
        }
    }

}
