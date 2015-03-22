package com.eventerzgz.interactor.population;

import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.exception.EventZgzException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 21/03/2015.
 */
public class PopulationInteractor {

    public static List<Population> getPopulations() throws EventZgzException
    {
        List<Population> populationsList;

        if(PopulationMem.getInstance().getAllPopulations() != null && PopulationMem.getInstance().getAllPopulations().size() > 0){
            populationsList = PopulationMem.getInstance().getAllPopulations();
        }else{
            populationsList = new PopulationRest().getAllPopulations();
            PopulationMem.getInstance().setPopulationsCached(populationsList);
        }

        return populationsList;
    }


}
