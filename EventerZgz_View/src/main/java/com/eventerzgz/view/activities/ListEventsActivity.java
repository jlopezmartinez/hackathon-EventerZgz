package com.eventerzgz.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eventerzgz.model.commons.Category;
import com.eventerzgz.model.commons.Population;
import com.eventerzgz.model.event.Event;
import com.eventerzgz.presenter.listevents.ListEventsIface;
import com.eventerzgz.presenter.listevents.ListEventsPresenter;
import com.eventerzgz.view.R;
import com.eventerzgz.view.adapter.ExpandableListAdapter;
import com.eventerzgz.view.adapter.MenuLateralItemsAdapter;
import com.eventerzgz.view.application.EventerZgzApplication;
import com.eventerzgz.view.share.SocialShare;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListEventsActivity extends ActionBarActivity implements ListEventsIface {

    //View
    //----
    private ListView listViewEvents;
    private AdapterListEvents adapterListEvents;
    private ProgressBar progressBarLoading;
    private View emptyView;
    private TextView textViewError;
    //Presenter
    //---------
    private final ListEventsPresenter listEventsPresenter = new ListEventsPresenter(this);

    //Data
    //----
    private boolean flagLoading = false;
    private boolean filterSearch = false;
    private boolean categorySearch = false;
    private List<Event> listEventsToShow;

    // Menu lateral
    // -------------
    private DrawerLayout menuLateral;
    private ListView listMenuLateral;
    private String[] opciones_menu;
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);

        //ActionBar
        //---------
        getSupportActionBar().setHomeButtonEnabled(true);

        //View
        //----
        listViewEvents = (ListView) findViewById(R.id.listViewEvents);
        progressBarLoading = (ProgressBar) findViewById(R.id.progressBarLoading);
        emptyView = findViewById(R.id.emptyView);
        textViewError = (TextView) findViewById(R.id.textViewError);

        configPaginacionListView();

        // Menu lateral
        // ------------
        //configureMenuLateral();

        //Presenter
        //---------
        showLoading();
        listEventsPresenter.getAllEvents();
        listEventsPresenter.getPopulation();

        // CLick Event
        // -----------
        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                intentDetailEvent(position);
            }
        });

    }

    private void setLateralMenu(){
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
/*                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
/*                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
/*                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/
                if(EventerZgzApplication.categoryList.get(childPosition)!=null) {
                    listEventsPresenter.getEventsByCategories(EventerZgzApplication.categoryList.get(childPosition).getId());
                }
                return false;
            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("¿Cuándo?");
        listDataHeader.add("¿Quién?");
        listDataHeader.add("¿Qué?");

        // Adding child data
        List<String> cuando = new ArrayList<String>();
        cuando.add("Hoy");
        cuando.add("Mañana");
        cuando.add("Esta semana");

        List<String> que = new ArrayList<String>();
        if(EventerZgzApplication.categoryList != null){
            for(int i=0; i<EventerZgzApplication.categoryList.size();i++){
                que.add(EventerZgzApplication.categoryList.get(i).getsTitle());
            }
        }

        listDataChild.put(listDataHeader.get(0), cuando);
        listDataChild.put(listDataHeader.get(2), que);

    }

    // ------------------------------------------------------------------------------------
    // CONFIGURE MENU LATERAL
    // ------------------------------------------------------------------------------------
    private void configureMenuLateral() {
        opciones_menu = getResources().getStringArray(R.array.opciones_menu);
        menuLateral = (DrawerLayout) findViewById(R.id.menu_lateral);
        listMenuLateral = (ListView) findViewById(R.id.menu_lateral_list);

        // Set the adapter for the list view
        listMenuLateral.setAdapter(new MenuLateralItemsAdapter(
                ListEventsActivity.this));
        // Set the list's click listener
        listMenuLateral.setOnItemClickListener(new DrawerItemClickListener());
    }

    //-------------------------------------------------------------------------
    //MENU
    //-------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_events, menu);
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAction = (SearchView) MenuItemCompat
                .getActionView(searchMenuItem);
        if (searchViewAction != null) {
            searchViewAction.setIconifiedByDefault(true);

            // BUSQUEDA
            // --------
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText == null || newText.equals("")) {
                        filterSearch = false;
                        refreshListEvents(EventerZgzApplication.allEventsList);
                    } else {
                    }

                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    filterSearch = true;
                    listEventsPresenter.getEventsByTitle(query);
                    return true;
                }

            };
            searchViewAction.setOnQueryTextListener(queryTextListener);
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent mainIntent = new Intent().setClass(
                    ListEventsActivity.this, TutorialActivity.class);
            startActivity(mainIntent);
            return true;
        }
        if(android.R.id.home == id){
            openCloseMenuLateral();
        }

        return super.onOptionsItemSelected(item);
    }

    //------------------------------------------------------------------------------------------
    //Open CLOSE MENU LATERAL
    //------------------------------------------------------------------------------------------
    private void openCloseMenuLateral(){
        if(menuLateral.isDrawerOpen(Gravity.LEFT)){
            menuLateral.closeDrawer(Gravity.LEFT);
        }else{
            menuLateral.openDrawer(Gravity.LEFT);
        }



    }
    //------------------------------------------------------------------------------------------
    //Métodos del presenter
    //------------------------------------------------------------------------------------------
    @Override
    public void fetchedEvents(List<Event> listEvents) {
        hideLoading();
        if(filterSearch){
            EventerZgzApplication.filterEventsList = listEvents;
        }else{
            EventerZgzApplication.allEventsList = listEvents;
        }

        if(listEvents.size() > 0){
            refreshListEvents(listEvents);
        }else{
            refreshListEvents(EventerZgzApplication.allEventsList);
        }


    }

    @Override

    public void fetchedCategories(List<Category> listCategory) {
        emptyView.setVisibility(View.GONE);
        EventerZgzApplication.categoryList = listCategory;
        configureMenuLateral();
        // preparing list data
        prepareListData();
        // EXPANDABLE MENU LATERAL //
        setLateralMenu();
    }

    @Override
    public void fetchedPopulation(List<Population> populationList) {
        if(populationList!=null){
            List<String> quien = new ArrayList<String>();
            for(int i=0; i<populationList.size();i++){
                quien.add(EventerZgzApplication.categoryList.get(i).getsTitle());
            }
            listDataChild.put(listDataHeader.get(1), quien);
        }
    }

    @Override
    public void error(String sMessage) {

        hideLoading();
        refreshListEvents(EventerZgzApplication.allEventsList);
        emptyView.setVisibility(View.VISIBLE);
        textViewError.setText(sMessage);
    }


    //-------------------------------------------------------------------------
    // REFRESH LIST ADAPTER
    //-------------------------------------------------------------------------
    private void refreshListEvents(List<Event> listEvents) {
        listEventsToShow = listEvents;
        if (adapterListEvents == null) {
            adapterListEvents = new AdapterListEvents();
            listViewEvents.setAdapter(adapterListEvents);
        } else {
            adapterListEvents.notifyDataSetChanged();
            ;
        }
    }

    //-------------------------------------------------------------------------
    // ADAPTER LIST EVENTS
    //-------------------------------------------------------------------------
    private class AdapterListEvents extends BaseAdapter {

        @Override
        public int getCount() {
            if (listEventsToShow != null) {
                return listEventsToShow.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup viewGroup) {
            ViewHolder viewholder;
            View vi = contentView;

            if (contentView == null) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.item_list_events, viewGroup, false);

                viewholder = new ViewHolder();
                viewholder.tvTitle = (TextView) vi.findViewById(R.id.tvTitle);
                viewholder.tvLugar = (TextView) vi.findViewById(R.id.tvLugar);
                viewholder.textViewFecha = (TextView) vi
                        .findViewById(R.id.textViewFecha);
                viewholder.tvVerMas = (TextView) vi.findViewById(R.id.tvVerMas);
                viewholder.tvCompartir = (TextView) vi
                        .findViewById(R.id.tvCompartir);
                viewholder.imageView = (ImageView) vi.findViewById(R.id.imageView);

                vi.setTag(viewholder);
            }
            viewholder = (ViewHolder) vi.getTag();

            Event event = listEventsToShow.get(position);
            viewholder.tvTitle.setText(event.getsTitle());
            try {
                viewholder.tvLugar.setText(event.getSubEvent().getWhere().getsTitle());
            }catch (Exception e){

            }
            if (event.getdEndDate() != null) {
                viewholder.textViewFecha.setText(event.getdEndDate().toString());
            } else {
                viewholder.textViewFecha.setVisibility(View.GONE);
            }
            //Imagen
            //------
            if (event.getsImage() != null && !event.getsImage().equals("")) {
                viewholder.imageView.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage((event.getFieldWithUri(event.getsImage())), viewholder.imageView);
            } else {
                viewholder.imageView.setVisibility(View.GONE);
            }


            //CLICK COMPARTIR
            viewholder.tvCompartir.setTag(position);
            viewholder.tvCompartir
                    .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                int position = (Integer) v.getTag();
                                                String url = "www.marca.com";
                                                SocialShare.share(ListEventsActivity.this, url);
                                            }
                                        }
                    );

            return vi;
        }
    }

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvLugar;
        TextView textViewFecha;
        TextView tvVerMas;
        TextView tvCompartir;
        ImageView imageView;
        LinearLayout linearLayoutClip;
    }

    // --------------------------------------------------------------------------------------
    // DRAWER ITEM CLICK LISTENER
    // --------------------------------------------------------------------------------------
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position,
                                long id) {
            switch (position) {
                case 0:
                    // Ocultar menu

                    menuLateral.closeDrawers();
                    break;

                default:
                    break;
            }
        }

    }

    // --------------------------------------------------------------------------------------
    // CONFIG PAGINACION
    // --------------------------------------------------------------------------------------
    private void configPaginacionListView() {
        listViewEvents.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount == totalItemCount
                        && totalItemCount != 0) {
                    if (flagLoading == false) {
                        flagLoading = true;
                        // TODO - ANADIR ITEMS AL LISTADO PARA CARGARLOS //
                        //adapter.notifyDataSetChanged();
                        // listView.invalidateViews();
                        //swipeLayout.setRefreshing(false);
                    }
                }
                /*int topRowVerticalPosition = (listView == null || listView.getChildCount() == 0) ? 0
                        : listView.getChildAt(0).getTop();
                swipeLayout.setEnabled(topRowVerticalPosition >= 0);*/

                //Realizar peticion de otros
                //--------------------------
                /*if(loading){
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }


                boolean loadMore =  firstVisibleItem + visibleItemCount >= totalItemCount-1;
                if(!searchingList && !loading && loadMore && urlPeticionWebService != null && !isEventos){
                    new RESTfulAsyncTask().execute(urlPeticionWebService);
                    posItemActual = firstVisibleItem;
                    loading = true;
                }*/

            }
        });
    }

    private void showLoading() {
        progressBarLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBarLoading.setVisibility(View.GONE);
    }

    // --------------------------------------------------------------------------------------
    // INTENT EVENT
    // --------------------------------------------------------------------------------------
    private void intentDetailEvent(int position) {
        Intent intentEvent = new Intent(ListEventsActivity.this, DetailEventActivity.class);
        intentEvent.putExtra(EventerZgzApplication.INTENT_EVENT_SELECTED, position);
        intentEvent.putExtra(EventerZgzApplication.INTENT_EVENT_FILTERED, filterSearch);
        startActivity(intentEvent);
    }

    // --------------------------------------------------------------------------------------
    // INTENT EVENT
    // --------------------------------------------------------------------------------------
    public void searchCategory(String id) {
        menuLateral.closeDrawer(Gravity.LEFT);
        categorySearch = true;
       listEventsPresenter.getEventsByCategories(id);
    }
}
