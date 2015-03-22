package com.eventerzgz.model.transformers;

import com.eventerzgz.model.commons.Coordinates;

import org.simpleframework.xml.transform.Transform;

/**
 * Created by Omar on 21/03/2015.
 */
public class CoordinatesTransformer implements Transform<Coordinates> {


    @Override
    public Coordinates read(String value) throws Exception {
        return new Coordinates(value);
    }

    @Override
    public String write(Coordinates value) throws Exception {
        return value.toString();
    }


}
