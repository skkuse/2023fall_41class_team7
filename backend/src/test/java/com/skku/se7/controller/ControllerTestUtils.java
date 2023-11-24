package com.skku.se7.controller;

import org.springframework.restdocs.snippet.Attributes;

public interface ControllerTestUtils {
    String LINK_CONTINENT_TYPE = "link:common/continent.html[대륙 정보,role=\"popup\"]";
    static Attributes.Attribute example(String value){
        return new Attributes.Attribute("example", value);
    }
}
