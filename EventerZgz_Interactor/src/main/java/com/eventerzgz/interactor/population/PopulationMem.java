package com.eventerzgz.interactor.population;

import java.util.List;

import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.exception.EventZgzException;

/**
 * Created by joseluis on 21/3/15.
 */
public class PopulationMem implements PopulationDataSource {

    private static PopulationMem instance;
    private static List<Population> eventCached;

    private PopulationMem(){

    }

    public static PopulationMem getInstance(){
        synchronized (PopulationMem.class){
            if(instance == null){
                if(instance == null){
                    instance  = new PopulationMem();
                }
            }
        }
        return instance;
    }

    public static void setPopulationsCached(List<Population> eventCached) {
        PopulationMem.eventCached = eventCached;
    }

    @Override
    public List<Population> getAllPopulations() throws EventZgzException {
        return eventCached;
    }
}
