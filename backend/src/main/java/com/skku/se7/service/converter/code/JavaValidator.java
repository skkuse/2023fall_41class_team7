package com.skku.se7.service.converter.code;

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
    static private String[] words = {"Runtime", "Process", "reflect", "net"};

    public JavaValidator(){
        for(String s : words){
            wordsToValidate.add(s);
        }
    }
    public static boolean isMalicious(String[] parsedUserCode){
        boolean malicious = false;
        for(String s : parsedUserCode){
            if(wordsToValidate.contains(s)){
                malicious = true;
                break;
            }
        }
        return malicious;
    }
}
