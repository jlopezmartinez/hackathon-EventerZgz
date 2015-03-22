package com.eventerzgz.interactor.population;

import java.util.List;

import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.exception.EventZgzException;

/**
 * Created by joseluis on 21/3/15.
 */
public interface PopulationDataSource {

    public List<Population> getAllPopulations() throws EventZgzException;

}
