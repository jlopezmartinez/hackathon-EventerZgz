package com.eventerzgz.model.commons;

import com.eventerzgz.model.Base;
import com.eventerzgz.model.exception.EventZgzException;

import java.util.List;

/**
 * Created by joseluis on 21/3/15.
 */
public class Population extends Base
{

    public static List<Population> doParseList(String sRawObj) throws EventZgzException {
        return SparqlBaseParser.doParseList(sRawObj, Population.class);
    }




}
