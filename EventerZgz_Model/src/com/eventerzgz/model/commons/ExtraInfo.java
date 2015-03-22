package com.eventerzgz.model.commons;

import com.eventerzgz.model.Base;

import org.simpleframework.xml.Element;

/**
 * Created by joseluis on 21/3/15.
 */
public class ExtraInfo
{
    @Element(name="codAnexo", required = false)
    private String sCodAnexo;

    @Element(name="imagen", required = false)
    private String sImage;

    @Element(name="documento", required = false)
    private String sDocument;

    @Element(name="nombreDocumento", required = false)
    private String sDocumentName;


    public static ExtraInfo doParse(String sRawObj){
        return new ExtraInfo();
    }


    //GETTERS & SETTERS
    public String getsCodAnexo() {
        return sCodAnexo;
    }

    public void setsCodAnexo(String sCodAnexo) {
        this.sCodAnexo = sCodAnexo;
    }

    public String getsImage() {
        return sImage;
    }

    public void setsImage(String sImage) {
        this.sImage = sImage;
    }

    public String getsDocument() {
        return sDocument;
    }

    public void setsDocument(String sDocument) {
        this.sDocument = sDocument;
    }

    public String getsDocumentName() {
        return sDocumentName;
    }

    public void setsDocumentName(String sDocumentName) {
        this.sDocumentName = sDocumentName;
    }

    @Override
    public String toString() {
        return "ExtraInfo{" +
                "sCodAnexo='" + sCodAnexo + '\'' +
                ", sImage='" + sImage + '\'' +
                ", sDocument='" + sDocument + '\'' +
                ", sDocumentName='" + sDocumentName + '\'' +
                "} ";
    }
}
