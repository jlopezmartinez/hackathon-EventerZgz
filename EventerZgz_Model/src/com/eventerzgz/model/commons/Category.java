package com.eventerzgz.model.commons;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import com.eventerzgz.model.Base;

import com.eventerzgz.model.exception.EventZgzException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Created by joseluis on 21/3/15.
 */
public class Category extends Base {

    private String sImage;

    public static List<Category> doParseList(String sRawObj) throws EventZgzException{
        return SparqlBaseParser.doParseList(sRawObj, Category.class);
    }

    //GETTERS & SETTERS
    public String getsImage() {
        return sImage;
    }

    public void setsImage(String sImage) {
        this.sImage = sImage;
    }

    public String getFieldWithUri(String sFieldValue){
        return "http://"+sFieldValue;
    }

}
