package com.skku.se7.controller;

import org.springframework.restdocs.snippet.Attributes;

public interface ControllerTestUtils {
    static Attributes.Attribute example(String value){
        return new Attributes.Attribute("example", value);
    }
}
