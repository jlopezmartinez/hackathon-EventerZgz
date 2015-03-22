package com.eventerzgz.presenter.service;

import android.content.Context;
import com.eventerzgz.model.event.Event;

/**
 * Created by joseluis on 22/3/15.
 */
public interface AlarmIface
{
    public void deliverNotification(Context context,String title,String sId,Event event);
}
