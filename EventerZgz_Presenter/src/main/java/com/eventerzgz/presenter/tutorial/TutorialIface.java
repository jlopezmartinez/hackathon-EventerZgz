package com.eventerzgz.presenter.tutorial;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.commons.Population;
import com.eventerzgz.presenter.BasePresenterIface;

import java.util.List;

/**
 * Created by joseluis on 21/3/15.
 */
public interface TutorialIface extends BasePresenterIface {

    public void fechedCategories(List<Category> categoryList);
    public void fechedPopulation(List<Population> populationList);

}
