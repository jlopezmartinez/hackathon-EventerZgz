package com.eventerzgz.interactor.population;

import java.util.List;

import android.util.Log;
import com.eventerzgz.interactor.BaseRest;
import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.exception.EventZgzException;

/**
 * Created by joseluis on 21/3/15.
 */
public class PopulationRest extends BaseRest implements PopulationDataSource {
    @Override
    public List<Population> getAllPopulations()  throws EventZgzException
    {

        String sUrl = "http://datos.zaragoza.es/sparql?query=PREFIX+population%3A+%3Chttp%3A%2F%2Fvocab.linkeddata.es%2Fkos%2Fcultura-ocio%2Fpoblacion-destinataria%2Fevento-zaragoza%3E%0D%0ASELECT+DISTINCT+%3Fid+%3Fdesc+WHERE+%7B+++%0D%0A%3FuriCont+a+population%3A%3B%0D%0A+++skos%3Anotation+%3Fid%3B%0D%0A+++skos%3AprefLabel+%3Fdesc.++++++++%0D%0A%7D&format=application%2Fsparql-results%2Bxml";
        String sXml = doHTTPGet(sUrl);
        Log.i(TAG, sXml);

        return Population.doParseList(sXml);

    }
}
