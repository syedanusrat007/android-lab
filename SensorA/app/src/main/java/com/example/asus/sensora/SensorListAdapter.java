package com.example.asus.sensora;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ASUS on 2017-09-12.
 */

public class SensorListAdapter extends ArrayAdapter {

    private  List<Sensor> sensors;
    private  Activity activity;


    public SensorListAdapter(Activity activity, List<Sensor> sensors) {
        super(activity, R.layout.eachrow , sensors);
        this.sensors = sensors;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.eachrow, null, true);
        TextView textView = (TextView) listViewItem.findViewById(R.id.textView);

        Sensor ss = sensors.get(position);
        int a = ss.getVersion();
        float b = ss.getPower();
        String c = ss.getName();

        textView.setText(a + " " + b + " "+ c);
        return listViewItem;

    }


}
