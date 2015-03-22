package com.eventerzgz.interactor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;
import com.eventerzgz.model.exception.EventZgzException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by joseluis on 21/3/15.
 */
public class BaseRest extends BaseInteractor{

    protected final int TIMEOUT = 500;


    protected String doHTTPGet(String sUrl) throws EventZgzException
    {
        try
        {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(sUrl);
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);

            Log.i(TAG, "URL" + sUrl + "HTTP Response ->" + httpResponse.getStatusLine().getStatusCode());

            if(httpResponse != null
                    && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                return getHttpContent(httpResponse.getEntity());
            }
            else
            {
                String message = httpResponse != null ? getHttpContent(httpResponse.getEntity()):"Respuesta vacia";
                throw new EventZgzException(message);
            }
        }catch (IOException exception)
        {
            throw new EventZgzException(exception);
        }

    }

    public String getHttpContent(HttpEntity httpEntity) throws IOException{
        return convertInputStreamToString(httpEntity.getContent());
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException
    {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder result = new StringBuilder();

        try
        {
            while ((line = bufferedReader.readLine()) != null)
                result.append(line);
            return result.toString();
        } finally
        {
            inputStream.close();
        }
    }
}
