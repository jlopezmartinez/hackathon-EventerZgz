package com.eventerzgz.model.commons;

/**
 * Created by joseluis on 21/3/15.
 */
public class Coordinates
{
    private float fLatitude;
    private float fLonguide;

    public Coordinates(String coordinates) {
        String[] coords = coordinates.split(",");
        this.fLonguide = Float.parseFloat(coords[0]);
        this.fLatitude = Float.parseFloat(coords[1]);
    }

    public static Coordinates doParse(String sRawObj){
        return new Coordinates("0,0");
    }


    //GETTERS & SETTERS
    public float getfLatitude() {
        return fLatitude;
    }

    public void setfLatitude(float fLatitude) {
        this.fLatitude = fLatitude;
    }

    public float getfLonguide() {
        return fLonguide;
    }

    public void setfLonguide(float fLonguide) {
        this.fLonguide = fLonguide;
    }


    @Override
    public String toString() {
        return "Coordinates{" +
                "fLatitude=" + fLatitude +
                ", fLonguide=" + fLonguide +
                '}';
    }
}
