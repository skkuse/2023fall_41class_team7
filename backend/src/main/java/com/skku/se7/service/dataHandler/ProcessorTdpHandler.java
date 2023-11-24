package com.skku.se7.service.dataHandler;

import com.skku.se7.dto.ProcessorModelTdp;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
    private final String path = "src/main/resources/";
    private Map<String, Double> cpuModelTdps = new HashMap<>();
    private Map<String, Double> gpuModelTdps = new HashMap<>();

    public ProcessorTdpHandler() throws ParserConfigurationException, IOException, SAXException {
        String fileName = "cpu_spec.xml";
        log.info("======================= Opening CPU - TDP File... : {} =======================", fileName);
        Document document = getDocument(path + fileName);
        convertDocToObject(document, cpuModelTdps);
        log.info("total Information count : {}", cpuModelTdps.size());
        log.info("======================= Finish Reading CPU - TDP File : {} =======================", fileName);

        fileName = "gpu_spec.xml";
        log.info("======================= Opening GPU - TDP File... : {} =======================", fileName);
        document = getDocument(path + fileName);
        convertDocToObject(document, gpuModelTdps);
        log.info("total Information count : {}", gpuModelTdps.size());
        log.info("======================= Finish Reading GPU - TDP File : {} =======================", fileName);
    }

    private static void convertDocToObject(Document document, Map<String ,Double> processorModelTdps) {
        NodeList models = document.getElementsByTagName("model");
        NodeList tdps = document.getElementsByTagName("tdp");
        log.info("model : tdp");
        for (int i = 0; i < models.getLength(); i++) {
            String model = models.item(i).getChildNodes().item(0).getNodeValue();
            Double tdp = Double.valueOf(tdps.item(i).getChildNodes().item(0).getNodeValue());
            log.info("{} : {}", model, tdp);
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
        return gpuModelTdps.containsKey(modelName);
    }

    public List<String> getGpuModelNames() {
        return new ArrayList<>(gpuModelTdps.keySet());
    }
    public List<String> getCpuModelNames() {
        return new ArrayList<>(cpuModelTdps.keySet());
    }
}
