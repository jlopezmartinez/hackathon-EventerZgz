package com.eventerzgz.model.event;

import com.eventerzgz.model.Base;
import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.commons.Coordinates;
import com.eventerzgz.model.commons.ExtraInfo;
import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.exception.EventZgzException;
import com.eventerzgz.model.transformers.CoordinatesTransformer;
import com.eventerzgz.model.transformers.DateFormatTransformer;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by joseluis on 20/3/15.
 */
public class Event extends Base {

    @Element(name="startDate", required = false)
    private Date dStartDate;

    @Element(name="endDate", required = false)
    private Date dEndDate;

    @Element(name="lastUpdated", required = false)
    private Date dLastUpdate;

    @Element(name="description", required = false)
    private String sDescription;

    @Element(name="destacada", required = false)
    private boolean bHighlighted;

    @Element(name="tipoEntrada", required = false)
    private String sTicketType;

    @Element(name="image", required = false)
    private String sImage;

    @Element(name="web", required = false)
    private String sWeb;

    @ElementList(name="temas", required = false)
    private List<Category> categoryList;


    @Element(name="actolugar", required = false)
    @Path("subEvent")
    private SubEvent subEvent;

    @ElementList(name="poblacion", required = false)
    private List<Population> populationList;

    @ElementList(name="anexo", required = false)
    private List<ExtraInfo> extraInfoList;

    @Element(name="coordinates", required = false)
    @Path("geometry")
    private Coordinates objCoordinates;


    public static List<Event> doParse(String sRawObj) throws EventZgzException {
        Persister persister = new Persister();
        List<Event> eventList = new ArrayList<>(0);
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            RegistryMatcher m = new RegistryMatcher();
            m.bind(Date.class, new DateFormatTransformer(format));
            m.bind(Coordinates.class, new CoordinatesTransformer());
            persister = new Persister(m);
            Event.SparqlEventList sparqlEventList = persister.read(Event.SparqlEventList.class, sRawObj, false);
            eventList = sparqlEventList.getList();
        } catch (Exception e) {
            throw new  EventZgzException(e);
        }
        return eventList;

    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static void main(String[] list) throws Exception {
        String xml = readFile("C:/temp/eventos.xml", Charset.defaultCharset());
        List<Event> events = doParse(xml);

        System.out.println(events);
    }

    public String getsWeb() {
        return sWeb;
    }

    public void setsWeb(String sWeb) {
        this.sWeb = sWeb;
    }

    private static class SparqlEventList {
        @ElementList
        private List<Event> result;

        public List<Event> getList() {
            return result;
        }
    }


    //GETTERS & SETTERS
    public Date getdEndDate() {
        return dEndDate;
    }

    public void setdEndDate(Date dEndDate) {
        this.dEndDate = dEndDate;
    }

    public Date getdLastUpdate() {
        return dLastUpdate;
    }

    public void setdLastUpdate(Date dLastUpdate) {
        this.dLastUpdate = dLastUpdate;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public boolean isbHighlighted() {
        return bHighlighted;
    }

    public void setbHighlighted(boolean bHighlighted) {
        this.bHighlighted = bHighlighted;
    }

    public String getsTicketType() {
        return sTicketType;
    }

    public void setsTicketType(String sTicketType) {
        this.sTicketType = sTicketType;
    }

    public String getsImage() {
        return sImage;
    }

    public void setsImage(String sImage) {
        this.sImage = sImage;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Population> getPopulationList() {
        return populationList;
    }

    public void setPopulationList(List<Population> populationList) {
        this.populationList = populationList;
    }

    public List<ExtraInfo> getExtraInfoList() {
        return extraInfoList;
    }

    public void setExtraInfoList(List<ExtraInfo> extraInfoList) {
        this.extraInfoList = extraInfoList;
    }

    public Coordinates getObjCoordinates() {
        return objCoordinates;
    }

    public void setObjCoordinates(Coordinates objCoordinates) {
        this.objCoordinates = objCoordinates;
    }


    public Date getdStartDate() {
        return dStartDate;
    }

    public void setdStartDate(Date dStartDate) {
        this.dStartDate = dStartDate;
    }
    public SubEvent getSubEvent() {
        return subEvent;
    }

    public void setSubEvent(SubEvent subEvent) {
        this.subEvent = subEvent;
    }

    @Override
    public String toString() {
        return "\nEvent{" +
                "dStartDate=" + dStartDate +
                ", dEndDate=" + dEndDate +
                ", dLastUpdate=" + dLastUpdate +
                //", sDescription='" + sDescription + '\'' +
                ", bHighlighted=" + bHighlighted +
                ", sTicketType='" + sTicketType + '\'' +
                ", sImage='" + sImage + '\'' +
                ", sWeb='" + sWeb + '\'' +
                ", categoryList=" + categoryList +
                ", subEvent=" + subEvent +
                ", populationList=" + populationList +
                ", extraInfoList=" + extraInfoList +
                ", objCoordinates=" + objCoordinates +
                '}'+ super.toString();
    }
}
