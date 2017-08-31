package com.alexander.aggregator.parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class XmlParser extends Parser {
    public XmlParser() {
        super();
    }

    @Override
    protected HashMap<String, ArrayList<String>> parse() {
        HashMap<String, ArrayList<String>> data = new HashMap<>();
        try {
            StringBuilder documentBuilder = new StringBuilder();
            for (String line : fileContents) {
                documentBuilder.append(line);
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(documentBuilder.toString())));

            Node root = document.getDocumentElement();
            NodeList rows = root.getChildNodes();

            for (int i = 0; i < rows.getLength(); i++) {
                Node row = rows.item(i);

                if (row.getNodeType() != Node.TEXT_NODE) {
                    NodeList props = row.getChildNodes();
                    for(int j = 0; j < props.getLength(); j++) {
                        Node prop = props.item(j);

                        if (prop.getNodeType() != Node.TEXT_NODE) {
                            if (data.containsKey(prop.getNodeName()))
                                data.get(prop.getNodeName()).add(prop.getChildNodes().item(0).getTextContent());
                            else{
                                data.put(prop.getNodeName(), new ArrayList<>());
                                data.get(prop.getNodeName()).add(prop.getChildNodes().item(0).getTextContent());
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }

        return data;
    }
}
