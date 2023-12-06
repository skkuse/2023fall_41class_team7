package com.skku.se7.service.converter.code;

import com.skku.se7.error.exceptions.MaliciousImportException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class JavaValidator {
    static private Set<String> wordsToValidate = new HashSet<>();
    static private String[] words = {"Runtime", "Process", "net", "nio", "io"};

    public JavaValidator(){
        for(String s : words){
            wordsToValidate.add(s);
        }
    }
    public static void isMalicious(String[] parsedUserCode){
        for(String s : parsedUserCode){
            if(wordsToValidate.contains(s)){
                throw new MaliciousImportException();
            }
        }
    }
}
