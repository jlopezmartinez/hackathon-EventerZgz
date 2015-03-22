package com.eventerzgz.view.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.eventerzgz.view.R;
import com.eventerzgz.view.adapter.TutorialAdapter;

import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

/**
 * Created by jesus_000 on 21/03/2015.
 */
public class TutorialActivity extends Activity {

    private ViewFlow viewFlow;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_layout);

        viewFlow = (ViewFlow) findViewById(R.id.viewflow);
        TutorialAdapter adapter = new TutorialAdapter(this);
        viewFlow.setAdapter(adapter);
        TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
        indicator.setTitleProvider(adapter);
        viewFlow.setFlowIndicator(indicator);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(TutorialAdapter.mapView != null){
            TutorialAdapter.mapView.onLowMemory();
        }
    }

    @Override
    public void onResume() {
        if(TutorialAdapter.mapView != null) {
            TutorialAdapter.mapView.onResume();
        }
        super.onResume();
    }
}
