package com.eventerzgz.model.commons;

import com.eventerzgz.model.Base;
import com.eventerzgz.model.exception.EventZgzException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Omar on 21/03/2015.
 */
public class SparqlBaseParser {

    public static <T extends Base> List<T> doParseList(String sRawObj, Class<T> klazz) throws EventZgzException {

        List<T> list = new ArrayList<T>();

        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(new InputSource(new StringReader(sRawObj)));

            XPathExpression tag_id = xPath.compile("/sparql/results/result");
            NodeList nodeList = (NodeList) tag_id.evaluate(xmlDocument, XPathConstants.NODESET);
            T base;
            int j;
            for (int i = 0; i < nodeList.getLength(); i++) {
                base = klazz.getConstructor().newInstance();
                //System.out.println(i);
                j = i + 1;
                String id = xPath.compile("/*[name()='sparql']/*[name()='results']/*[name()='result'][" + j + "]/*[name()='binding'][@name='id']/*[name()='literal']/text()").evaluate(xmlDocument);
                String title = xPath.compile("/*[name()='sparql']/*[name()='results']/*[name()='result'][" + j + "]/*[name()='binding'][@name='desc']/*[name()='literal']/text()").evaluate(xmlDocument);
                System.out.println(i);
                base.setId(id);
                base.setsTitle(title);
                list.add(base);
            }
        } catch (Exception e) {
            throw new EventZgzException(e);
        }

        return list;
    }
}
