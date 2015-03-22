package com.eventerzgz.interactor.category;

import java.util.List;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.exception.EventZgzException;

/**
 * Created by joseluis on 21/3/15.
 */
public class CategoryMem implements CategoryDataSource {

    private static CategoryMem instance;
    private static List<Category> eventCached;

    private CategoryMem(){

    }

    public static CategoryMem getInstance(){
        synchronized (CategoryMem.class){
            if(instance == null){
                if(instance == null){
                    instance  = new CategoryMem();
                }
            }
        }
        return instance;
    }

    public static void setCategoriesCached(List<Category> eventCached) {
        CategoryMem.eventCached = eventCached;
    }

    @Override
    public List<Category> getAllCategories() throws EventZgzException {
        return eventCached;
    }
}
