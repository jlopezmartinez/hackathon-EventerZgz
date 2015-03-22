package com.eventerzgz.interactor.category;

import java.util.List;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.exception.EventZgzException;

/**
 * Created by joseluis on 21/3/15.
 */
public interface CategoryDataSource {

    public List<Category> getAllCategories() throws EventZgzException;

}
