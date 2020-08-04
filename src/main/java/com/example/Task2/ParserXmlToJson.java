package com.example.Task2;

import com.example.Utils.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserXmlToJson extends AbstractParser {

    public void action() {
        clearBeforeStart();
        doParse();
    }

    public void doParse() {
        saveToFile("./data2.json", parseXML());
    }

    public List<Employee> parseXML() {
        List<Employee> list = new ArrayList<>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File("./data.xml"));
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList) xPath.compile("/staff/employee").evaluate(document, XPathConstants.NODESET);
            for (int iEl = 0; iEl < nl.getLength(); iEl++) {
                if (Node.ELEMENT_NODE == nl.item(iEl).getNodeType()) {
                    Element row = (Element) nl.item(iEl);
                    Element[] empAttr = {
                            (Element) row.getElementsByTagName("id").item(0),
                            (Element) row.getElementsByTagName("firstName").item(0),
                            (Element) row.getElementsByTagName("lastName").item(0),
                            (Element) row.getElementsByTagName("country").item(0),
                            (Element) row.getElementsByTagName("age").item(0)
                    };
                    list.add(new Employee(
                            Long.parseLong(empAttr[0].getTextContent()),
                            empAttr[1].getTextContent(),
                            empAttr[2].getTextContent(),
                            empAttr[3].getTextContent(),
                            Integer.parseInt(empAttr[4].getTextContent())
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void clearBeforeStart() {
        try {
            clearFile(new File("./data2.json"));
        } catch (IOException e) {}
    }

}
