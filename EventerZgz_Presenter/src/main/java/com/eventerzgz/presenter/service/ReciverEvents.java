package com.eventerzgz.presenter.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by joseluis on 21/3/15.
 */
public class ReciverEvents extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, EventService.class));
    }
}
