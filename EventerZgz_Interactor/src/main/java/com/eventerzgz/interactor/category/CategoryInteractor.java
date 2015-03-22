package com.eventerzgz.interactor.category;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.exception.EventZgzException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 21/03/2015.
 */
public class CategoryInteractor {

    public static List<Category> getCategories() throws EventZgzException
    {
        List<Category> categoriesList;

        if(CategoryMem.getInstance().getAllCategories() != null && CategoryMem.getInstance().getAllCategories().size() > 0){
            categoriesList = CategoryMem.getInstance().getAllCategories();
        }else{
            categoriesList = new CategoryRest().getAllCategories();
            CategoryMem.getInstance().setCategoriesCached(categoriesList);
        }

        return categoriesList;
    }


}
