package com.skku.se7.service;

import com.skku.se7.dto.LocationIntensity;
import com.skku.se7.dto.enums.Continent;
import com.skku.se7.error.exceptions.NoMatchCountryException;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LocationHandler {
    private final String path = "src/main/resources/";
    private List<LocationIntensity> locationIntensities = new ArrayList<>();

    public LocationHandler() throws ParserConfigurationException, IOException, SAXException {
        Document document = getDocument(path + "intensity.xml");
        convertDocToObject(document, locationIntensities);
    }

    private static void convertDocToObject(Document document, List<LocationIntensity> locationIntensities) {
        NodeList continentNames = document.getElementsByTagName("continentName");
        NodeList countryNames = document.getElementsByTagName("countryName");
        NodeList regionNames = document.getElementsByTagName("regionName");
        NodeList carbonIntensities = document.getElementsByTagName("carbonIntensity");
        for (int i = 0; i < continentNames.getLength(); i++) {
            Continent continent = Continent.valueOf(continentNames.item(i).getChildNodes().item(0).getNodeValue());
            String countryName = countryNames.item(i).getChildNodes().item(0).getNodeValue();
            String regionName = regionNames.item(i).getChildNodes().item(0).getNodeValue();
            Double intensity = Double.valueOf(carbonIntensities.item(i).getChildNodes().item(0).getNodeValue());
            locationIntensities.add(new LocationIntensity(continent, countryName, regionName, intensity));
        }
    }

    private static Document getDocument(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        return builder.parse(fileName);
    }

    public boolean validateCountry(Continent continent, String country) {
        return locationIntensities.stream()
                .filter(l -> l.getContinent().equals(continent))
                .anyMatch(l -> l.getCountry().equals(country));
    }

    public boolean validateRegion(Continent continent, String country, String region) {
        return locationIntensities.stream()
                .filter(l -> l.getContinent().equals(continent))
                .filter(l -> l.getCountry().equals(country))
                .anyMatch(l -> l.getRegion().equals(region));
    }

    public List<String> getContinents() {
        return new ArrayList<>(locationIntensities.stream()
                .map(LocationIntensity::getContinent)
                .map(Continent::name)
                .collect(Collectors.toSet()));
    }

    public List<String> getCountries(Continent continent) {
        return new ArrayList<>(locationIntensities.stream()
                .filter(l -> l.getContinent().equals(continent))
                .map(LocationIntensity::getCountry)
                .collect(Collectors.toSet()));
    }

    public List<String> getRegions(Continent continent, String country) {
        return new ArrayList<>(locationIntensities.stream()
                .filter(l -> l.getContinent().equals(continent))
                .filter(l -> l.getCountry().equals(country))
                .map(LocationIntensity::getRegion)
                .collect(Collectors.toSet()));
    }

    public Double getCarbonIntensity(Continent continent, String country, String region) {
        return locationIntensities.stream()
                .filter(l -> l.getContinent().equals(continent))
                .filter(l -> l.getCountry().equals(country))
                .filter(l -> l.getRegion().equals(region))
                .findFirst()
                .orElseThrow(NoMatchCountryException::new)
                .getCarbonIntensity();
    }
}
