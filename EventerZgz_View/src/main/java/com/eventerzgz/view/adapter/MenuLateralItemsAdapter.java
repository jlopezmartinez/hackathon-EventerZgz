/* Autor : Javier Arroyo (Jarroyo@hiberus.com)
 * 
 * Fecha: 14/07/2014,14:09:20
 * 
 * Name: MenuLateralItemsAdapter.java
 * 
 * Comentarios:
 * 
 * M�todos: 
 */
package com.eventerzgz.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eventerzgz.view.R;
import com.eventerzgz.view.activities.ListEventsActivity;
import com.eventerzgz.view.application.EventerZgzApplication;

/**
 * @author jarroyo
 * 
 */
public class MenuLateralItemsAdapter extends BaseAdapter {

	private Context context;

	public MenuLateralItemsAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		if(EventerZgzApplication.categoryList!=null){
            return EventerZgzApplication.categoryList.size();
        }
        return 0;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_menu_lateral, null);
		}

		TextView textViewCategoria = (TextView) convertView.findViewById(R.id.textViewCategoria);

		switch (position) {
		case 0:

			break;

		default:
			break;
		}

		textViewCategoria.setText(EventerZgzApplication.categoryList.get(position).getsTitle().trim());
        textViewCategoria.setTag(position);
        textViewCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int)view.getTag();
                ((ListEventsActivity)context).searchCategory(EventerZgzApplication.categoryList.get(position).getId());
            }
        });

		return convertView;
	}

}
