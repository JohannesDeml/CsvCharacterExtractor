/*
 * --------------------------------------------------------------------------------------------------------------------
 * <copyright file="ConfigReader.java" company="Supyrb">
 *   Copyright (c) 2018 Supyrb. All rights reserved.
 * </copyright>
 * <author>
 *   Johannes Deml
 *   send@johannesdeml.com
 * </author>
 * --------------------------------------------------------------------------------------------------------------------
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ConfigReader {

    public ConfigReader() {
    }

    public void parseXmlFile(String relativeConfigPath, ConfigData configData) {
        //get the factory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document dom = null;

        try {

            //Using factory get an instance of document builder
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            dom = documentBuilder.parse(relativeConfigPath);


        }catch(ParserConfigurationException pce) {
            pce.printStackTrace();
        }catch(SAXException se) {
            se.printStackTrace();
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }

        if(dom == null) {
            System.out.println("Couldn't read config file, proceeding with default values");
            return;
        }

        Element root = dom.getDocumentElement();

        NodeList inNodes = root.getElementsByTagName("in");
        if(inNodes == null || inNodes.getLength() <= 0) {
            System.out.println("Couldn't find in node in config file, proceeding with default values");
        }
        else {
            Element node = (Element)inNodes.item(0);
            configData.setInPath(getTextValue(node, "path", configData.getInPath()));
        }


        NodeList outNodes = root.getElementsByTagName("out");
        if(outNodes == null || outNodes.getLength() <= 0) {
            System.out.println("Couldn't find out node in config file, proceeding with default values");
        }
        else {
            Element node = (Element)outNodes.item(0);
            configData.setOutPath(getTextValue(node, "path", configData.getOutPath()));
        }

        NodeList modificationNodes = root.getElementsByTagName("modification");
        if(modificationNodes == null || modificationNodes.getLength() <= 0) {
            System.out.println("Couldn't find modification node in config file, proceeding with default values");
        }
        else {
            Element node = (Element)modificationNodes.item(0);
            configData.setIncludeCharacters(getTextValue(node, "include-characters", configData.getIncludeCharacters()));
            configData.setExcludeCharacters(getTextValue(node, "exclude-characters", configData.getExcludeCharacters()));
        }
    }

    private String getTextValue(Element element, String tagName, String defaultValue) {
        if(element == null) {
            return defaultValue;
        }
        NodeList nodes = element.getElementsByTagName(tagName);
        if(nodes == null || nodes.getLength() == 0) {
            return defaultValue;
        }
        return nodes.item(0).getTextContent();
    }
}
