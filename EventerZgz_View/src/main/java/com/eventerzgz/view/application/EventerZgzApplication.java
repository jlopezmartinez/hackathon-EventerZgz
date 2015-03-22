package com.eventerzgz.view.application;

/**
 * Created by JavierArroyo on 21/3/15.
 */

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.service.AlarmIface;
import com.eventerzgz.presenter.service.AlarmReciver;
import com.eventerzgz.view.R;
import com.eventerzgz.view.activities.DetailEventActivity;
import com.eventerzgz.view.activities.ListEventsActivity;
import com.eventerzgz.view.activities.SplashScreenActivity;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by JavierArroyo on 21/3/15.
 */
public class EventerZgzApplication  extends Application implements AlarmIface {

    //Data intent
    //-----------
    public static final String INTENT_EVENT_SELECTED = "posEventSelected";
    public static final String INTENT_EVENT_FILTERED = "eventFiltered";

    //Data
    //----
    public static List<Event> allEventsList;
    public static List<Event> filterEventsList;
    public static List<Category> categoryList;

    //Preferences
    //-----
    public static final String APP_PREFERENCES = "eventerzgz";


    public void startService(){
        // TODO - Comprobar que no este lanzado ya
        AlarmReciver.setAlarm(getApplicationContext(), this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Se comenta parte del mtodo para evitar que saque log
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)

                .memoryCache(new UsingFreqLimitedMemoryCache(2000000)).memoryCacheSize(1500000).threadPoolSize(3)
                .memoryCache(new WeakMemoryCache())/*
                                                         * .enableLogging ()
                                                         */
                .build();

        // Iniciar ImageLoader
        ImageLoader.getInstance().init(config);
        startService();
    }


    public void deliverNotification(Context context,String title,String sId,Event event)
    {

        PendingIntent contentIntent;

        if(event == null){
            Intent intent = new Intent(this,ListEventsActivity.class);
            contentIntent = PendingIntent.getActivity(this, 0,intent , 0);
        }else{
            allEventsList = new ArrayList<>();
            allEventsList.add(event);

            Intent intent = new Intent(this,DetailEventActivity.class);
            intent.putExtra(EventerZgzApplication.INTENT_EVENT_SELECTED,0);
            contentIntent = PendingIntent.getActivity(this, 0,intent , 0);

        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("EventerZgz")
                        .setContentText(title);

        mBuilder.setContentIntent(contentIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        int id = 5;
        try{
            Integer.parseInt(sId);
        }catch (Exception ex){
            ex.printStackTrace();
            id = new Random().nextInt();
        }


        mNotificationManager.notify(id, mBuilder.build());


    }
}
