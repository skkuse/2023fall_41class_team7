package com.skku.se7.service.converter.code;

import com.skku.se7.error.exceptions.MaliciousCodeException;
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
    static private String[] imports = {"Runtime", "Process", "reflect", "net"};
    static private String[] words = {"Runtime", "Process", "reflect", "net"};

    public JavaValidator(){
        for(String s : words){
            wordsToValidate.add(s);
        }
    }
    public void isMalicious(String[] parsedUserCode){
        boolean malicious = false;
        for(String s : parsedUserCode){
            if(wordsToValidate.contains(s)){
                throw new MaliciousCodeException();
            }
        }
    }

    private void validateImport(String code) {
        if(code.contains("java.lang.Runtime")) throw new MaliciousImportException();
        if(code.contains("java.lang.reflect")) throw new MaliciousImportException();
        if(code.contains("java.io")) throw new MaliciousImportException();
        if(code.contains("java.nio")) throw new MaliciousImportException();
        if(code.contains("java.net")) throw new MaliciousImportException();
        if(code.contains("java.lang.Thread")) throw new MaliciousImportException();
    }
    private void validateCode(String code) {
        new Runtime().exec()
        if(code.contains("Thread")) throw new MaliciousCodeException();
        if(code.contains("")) throw new MaliciousCodeException();
        if(code.contains("Thread")) throw new MaliciousCodeException();
        if(code.contains("Thread")) throw new MaliciousCodeException();
        if(code.contains("Thread")) throw new MaliciousCodeException();

    }
}
