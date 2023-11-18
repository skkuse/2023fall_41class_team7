package com.skku.se7;

import com.skku.se7.service.LocationHandler;
import com.skku.se7.service.ProcessorTdpHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Configuration
public class Se7Configuration {
    @Bean
    public ProcessorTdpHandler processorTdpHandler() throws ParserConfigurationException, IOException, SAXException {
        return new ProcessorTdpHandler();
    }
    @Bean
    public LocationHandler locationHandler() throws ParserConfigurationException, IOException, SAXException {
        return new LocationHandler();
    }
}
