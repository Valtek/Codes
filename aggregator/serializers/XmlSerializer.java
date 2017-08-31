package com.alexander.aggregator.serializers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class XmlSerializer extends Serializer {
    public XmlSerializer() {
        super();
    }

    @Override
    public void serialize(HashMap<String, ArrayList<String>> data) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document doc = factory.newDocumentBuilder().newDocument();

            ArrayList<String> A = new ArrayList<>();
            for(String key : data.keySet())
                A.add(key);

            Element root = doc.createElement("root");
            doc.appendChild(root);

            for(int i = 0; i < data.get(A.get(0)).size(); i++){

                Element row = doc.createElement("row");
                root.appendChild(row);

                Element item1 = doc.createElement(A.get(0));
                item1.setTextContent(data.get(A.get(0)).get(i));
                row.appendChild(item1);

                Element item2 = doc.createElement(A.get(1));
                item2.setTextContent(data.get(A.get(1)).get(i));
                row.appendChild(item2);
            }

            File file = new File(filePath);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(file));

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (TransformerException ex) {
            ex.printStackTrace(System.out);
        }


    }
}
