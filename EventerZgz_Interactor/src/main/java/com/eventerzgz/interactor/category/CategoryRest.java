package com.eventerzgz.interactor.category;

import java.util.List;

import android.util.Log;
import com.eventerzgz.interactor.BaseRest;
import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.exception.EventZgzException;

/**
 * Created by joseluis on 21/3/15.
 */
public class CategoryRest extends BaseRest implements CategoryDataSource {
    @Override
    public List<Category> getAllCategories()  throws EventZgzException
    {

        String sUrl = "http://datos.zaragoza.es/sparql?default-graph-uri=&query=%0D%0APREFIX+tema%3A+%3Chttp%3A%2F%2Fvocab.linkeddata.es%2Fkos%2Fcultura-ocio%2Fsubtema%2F%3E%0D%0ASELECT+DISTINCT+%3Fid+%3Fdesc+WHERE+%7B%09%0D%0A%3FuriCont+a+tema%3Aevento-zaragoza%3B%0D%0A+++skos%3Anotation+%3Fid%3B%0D%0A+++skos%3AprefLabel+%3Fdesc.%09%09%0D%0A%7D&format=application%2Fsparql-results%2Bxml";
        String sXml = doHTTPGet(sUrl);
        Log.i(TAG, sXml);

        return Category.doParseList(sXml);

    }
}
