package com.eventerzgz.model.commons;

import com.eventerzgz.model.Base;

import org.simpleframework.xml.Element;

/**
 * Created by joseluis on 21/3/15.
 */
public class Place
 extends Base{
    @Element(name="direccion", required = false)
    private String sAddress;

    @Element(name="cp", required = false)
    private String sCP;

    @Element(name="localidad", required = false)
    private String sTown;

    @Element(name="provincia", required = false)
    private String sProvince;

    @Element(name="pais", required = false)
    private String sCountry;

    @Element(name="telefono", required = false)
    private String sTelephone;

    @Element(name="fax", required = false)
    private String sFax;

    @Element(name="mail", required = false)
    private String sMail;

    @Element(name="autobuses", required = false)
    private String sBus;

    @Element(name="accesibilidad", required = false)
    private String sAccessibility;

    private Coordinates objCoordinates;

    public static Place doParse(String sRawObj){
        return new Place();
    }


    //GETTERS & SETTERS

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public String getsCP() {
        return sCP;
    }

    public void setsCP(String sCP) {
        this.sCP = sCP;
    }

    public String getsTown() {
        return sTown;
    }

    public void setsTown(String sTown) {
        this.sTown = sTown;
    }

    public String getsProvince() {
        return sProvince;
    }

    public void setsProvince(String sProvince) {
        this.sProvince = sProvince;
    }

    public String getsCountry() {
        return sCountry;
    }

    public void setsCountry(String sCountry) {
        this.sCountry = sCountry;
    }

    public String getsTelephone() {
        return sTelephone;
    }

    public void setsTelephone(String sTelephone) {
        this.sTelephone = sTelephone;
    }

    public String getsFax() {
        return sFax;
    }

    public void setsFax(String sFax) {
        this.sFax = sFax;
    }

    public String getsMail() {
        return sMail;
    }

    public void setsMail(String sMail) {
        this.sMail = sMail;
    }

    public String getsBus() {
        return sBus;
    }

    public void setsBus(String sBus) {
        this.sBus = sBus;
    }

    public String getsAccessibility() {
        return sAccessibility;
    }

    public void setsAccessibility(String sAccessibility) {
        this.sAccessibility = sAccessibility;
    }

    public Coordinates getObjCoordinates() {
        return objCoordinates;
    }

    public void setObjCoordinates(Coordinates objCoordinates) {
        this.objCoordinates = objCoordinates;
    }

    @Override
    public String toString() {
        return "Place{" +
                "sAddress='" + sAddress + '\'' +
                ", sCP='" + sCP + '\'' +
                ", sTown='" + sTown + '\'' +
                ", sProvince='" + sProvince + '\'' +
                ", sCountry='" + sCountry + '\'' +
                ", sTelephone='" + sTelephone + '\'' +
                ", sFax='" + sFax + '\'' +
                ", sMail='" + sMail + '\'' +
                ", sBus='" + sBus + '\'' +
                ", sAccessibility='" + sAccessibility + '\'' +
                ", objCoordinates=" + objCoordinates +
                "} " + super.toString();
    }
}
