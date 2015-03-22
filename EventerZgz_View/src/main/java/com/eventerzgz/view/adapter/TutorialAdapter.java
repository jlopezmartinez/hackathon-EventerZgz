package com.eventerzgz.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.commons.Population;
import com.eventerzgz.presenter.BasePresenter;
import com.eventerzgz.presenter.tutorial.TutorialIface;
import com.eventerzgz.presenter.tutorial.TutorialPresenter;
import com.eventerzgz.view.R;
import com.eventerzgz.view.activities.ListEventsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.taptwo.android.widget.TitleProvider;

import java.util.ArrayList;
import java.util.List;

public class TutorialAdapter extends BaseAdapter implements TitleProvider, TutorialIface {

    // Mapa
    // ----
    public static MapView mapView;
    private GoogleMap map;
    private Context context;
    private Marker marker = null;
    private View viewPosition;
    private View viewCategories;
    private View viewCategoriesPush;
    private CheckBox[] listCheckboxCat;
    private CheckBox[] listCheckboxPob;
    ArrayList<String> arrayIdsCategories = new ArrayList<String>();
    ArrayList<String> arrayIdsCategoriesPob = new ArrayList<String>();

    private static final int VIEW1 = 0;
    private static final int VIEW2 = 1;
    private static final int VIEW3 = 2;
    private static final int VIEW4 = 3;
    private static final int VIEW_MAX_COUNT = VIEW4 + 1;
    private final String[] names = {"Bienvenido", "Tus intereses", "Tu Zona", "Notificaciones"};

    private LayoutInflater mInflater;
    private TutorialPresenter presenter = new TutorialPresenter(this);

    public TutorialAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        presenter.getCategories();
        presenter.getPopulation();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_MAX_COUNT;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int view = getItemViewType(position);
        if (convertView == null) {
            switch (view) {
                case VIEW1:
                    convertView = mInflater.inflate(R.layout.tuto_step1, null);
                    configViewStart(convertView);
                    break;
                case VIEW2:
                    convertView = mInflater.inflate(R.layout.tuto_step2, null);
                    viewCategories = convertView;
                    configViewStart(convertView);
                    break;
                case VIEW3:
                    convertView = mInflater.inflate(R.layout.tuto_step3, null);
                    viewPosition = convertView;
                    configViewPosition(convertView);
                    break;
                case VIEW4:
                    convertView = mInflater.inflate(R.layout.tuto_step4, null);
                    viewCategoriesPush = convertView;
                    configViewFinish(convertView);
                    Log.e("TAG", "2");
                    break;
            }
        }

        return convertView;
    }

    private void openListEvents() {
        Intent intent = new Intent(context, ListEventsActivity.class);
        context.startActivity(intent);
    }

    private void configViewFinish(View convertView) {
        Button buttonClose = (Button) convertView.findViewById(R.id.buttonFinish);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // GUARDAR DATOS //

                // CATEGORIAS //
                for (int i = 0; i < listCheckboxCat.length; i++) {
                    final int j = i;
                    if (listCheckboxCat[i].isChecked()) {
                        arrayIdsCategories.add("" + listCheckboxCat[j].getId());
                    }
                }

                // POBLACION //
                for (int i = 0; i < listCheckboxPob.length; i++) {
                    final int j = i;
                    if (listCheckboxPob[i].isChecked()) {
                        arrayIdsCategoriesPob.add("" + listCheckboxPob[j].getId());
                    }
                }

                BasePresenter.saveCategoriesSelectedInPreferences(arrayIdsCategories, context);
                BasePresenter.savePoblationSelectedInPreferences(arrayIdsCategoriesPob, context);

                ((Activity) context).finish();
                openListEvents();
            }
        });
    }

    private void configViewStart(View convertView) {
        Button buttonClose = (Button) convertView.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
                openListEvents();
            }
        });
    }


    private void configViewPosition(View convertView) {
        try {
            mapView = (MapView) convertView.findViewById(R.id.mapview);
            mapView.onCreate(null);
            mapView.setClickable(true);
            mapView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            viewPosition.getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            viewPosition.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                    return mapView.onTouchEvent(motionEvent);
                }
            });
            configMap(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button buttonClose = (Button) convertView.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
                openListEvents();
            }
        });

    }
    // -----------------------------------------------------------------------------------------------------
    // CONFIG MAP
    // -----------------------------------------------------------------------------------------------------

    private void configMap(final Context context) {
        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        LatLng myLocation;
        try {
            MapsInitializer.initialize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (map.getMyLocation() != null) {
            myLocation = new LatLng(map.getMyLocation().getLatitude(),
                    map.getMyLocation().getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                    13));

        }else {
            myLocation = new LatLng(41.654935, -0.875475);
        }
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                    13));

        mapView.invalidate();


        // CLICK MAP
        // ---------
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                if (marker != null) {
                    removeMarkerFromMap(marker);
                }
                marker = addMarkerToMap(point.latitude, point.longitude);
                BasePresenter.saveLocationPushInPreferences(point.latitude, point.longitude, context);
            }
        });

    }

    // -------------------------------------------------------------------------
    // ADD MARKER TO MAP
    // -------------------------------------------------------------------------
    private Marker addMarkerToMap(double latitude, double longitude) {

        Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(
                latitude, longitude)));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                latitude, longitude), 18));
        mapView.invalidate();
        return marker;

    }

    // -------------------------------------------------------------------------
    // REMOVE MARKER FROM MAP
    // -------------------------------------------------------------------------
    private void removeMarkerFromMap(Marker marker) {
        marker.remove();
    }

    public String getTitle(int position) {
        return names[position];
    }

    @Override
    public void fechedCategories(List<Category> categoryList) {
        Log.e("TAG", "3: " + categoryList.size());
        LinearLayout layoutCategories = (LinearLayout) viewCategories.findViewById(R.id.layoutCategories);
        LinearLayout layoutCategoriesPush = (LinearLayout) viewCategoriesPush.findViewById(R.id.layoutCategoriesPush);

        listCheckboxCat = new CheckBox[categoryList.size()];

        for (int i = 0; i < categoryList.size(); i++) {

            listCheckboxCat[i] = new CheckBox(context);
            listCheckboxCat[i].setId(Integer.parseInt(categoryList.get(i).getId()));

            listCheckboxCat[i].setText(categoryList.get(i).getsTitle());

            layoutCategories.addView(listCheckboxCat[i]);
        }
    }

    @Override
    public void fechedPopulation(List<Population> populationList) {

        LinearLayout layoutCategoriesPush = (LinearLayout) viewCategoriesPush.findViewById(R.id.layoutCategoriesPush);

        listCheckboxPob = new CheckBox[populationList.size()];

        for (int i = 0; i < populationList.size(); i++) {

            listCheckboxPob[i] = new CheckBox(context);

            listCheckboxPob[i].setId(Integer.parseInt(populationList.get(i).getId()));

            listCheckboxPob[i].setText(populationList.get(i).getsTitle());

            layoutCategoriesPush.addView(listCheckboxPob[i]);
        }
    }

    @Override
    public void error(String sMessage) {

    }
}
