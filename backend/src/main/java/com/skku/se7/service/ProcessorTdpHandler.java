package com.skku.se7.service;

import com.skku.se7.dto.ProcessorModelTdp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ProcessorTdpHandler {
    @Value("${cpuModelFile}")
    private String cpuModelFile;
    @Value("${gpuModelFile}")
    private String gpuModelFile;

    private Map<String, Double> cpuModelTdps = new HashMap<>();
    private Map<String, Double> gpuModelTdps = new HashMap<>();

    public ProcessorTdpHandler() throws ParserConfigurationException, IOException, SAXException {
        Document document = getDocument(cpuModelFile);
        convertDocToObject(document, cpuModelTdps);

        document = getDocument(gpuModelFile);
        convertDocToObject(document, gpuModelTdps);
    }

    private static void convertDocToObject(Document document, Map<String ,Double> processorModelTdps) {
        NodeList models = document.getElementsByTagName("model");
        NodeList tdps = document.getElementsByTagName("tdp");
        for (int i = 0; i < models.getLength(); i++) {
            String model = models.item(i).getNodeValue();
            Double tdp = Double.valueOf(tdps.item(i).getNodeValue());
            processorModelTdps.put(model, tdp);
        }
    }

    private static Document getDocument(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        return builder.parse(fileName);
    }

    public Double getCpuTdpByModelName(String modelName) {
        return cpuModelTdps.get(modelName);
    }
    public Double getGpuTdpByModelName(String modelName) {
        return gpuModelTdps.get(modelName);
    }

    public boolean validateCpuModel(String modelName) {
        return cpuModelTdps.containsKey(modelName);
    }

    public boolean validateGpuModel(String modelName) {
        log.info("gpuModelTdps.containsKey(modelName) : {}", gpuModelTdps.containsKey(modelName));
        return gpuModelTdps.containsKey(modelName);
    }
}
