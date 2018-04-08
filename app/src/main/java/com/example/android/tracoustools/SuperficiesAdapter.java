package com.example.android.tracoustools;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SuperficiesAdapter extends ArrayAdapter<Superficie> {

    public SuperficiesAdapter(Activity context, ArrayList<Superficie> sups) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, sups);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.sup_item, parent, false);
        }
        // Get the {@link Superficie} object located at this position in the list
        Superficie currentSup = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID material
        TextView materialTextView = (TextView) listItemView.findViewById(R.id.sup_material);
        // Get the material from the current Superficie object and
        // set this text on the material TextView
        materialTextView.setText(currentSup.getMaterial());

        // Find the TextView in the list_item.xml layout with the ID area
        TextView areaTextView = (TextView) listItemView.findViewById(R.id.sup_area);
        // Get the area from the current Superficie object and
        // set this text on the area TextView
        double area = currentSup.getArea();
        area = Math.round(area*100.00)/100.00;
        areaTextView.setText(String.valueOf(area));

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;

    }
}
