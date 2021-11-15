package com.example.drawertest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<SensorData> arrayList;

    public MyAdapter(Context context, ArrayList<SensorData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public  View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        }
        TextView created_at_lv, air_temperature_lv, humidity_lv, wind_speed_lv, pressure_lv;
        created_at_lv = (TextView) convertView.findViewById(R.id.created_at_lv);
        air_temperature_lv = (TextView) convertView.findViewById(R.id.air_temperature_lv);
        humidity_lv = (TextView) convertView.findViewById(R.id.humidity_lv);
        wind_speed_lv = (TextView) convertView.findViewById(R.id.wind_speed_lv);
        pressure_lv = (TextView) convertView.findViewById(R.id.pressure_lv);

        created_at_lv.setText("On\n"+arrayList.get(position).getCreatedAt());
        air_temperature_lv.setText(arrayList.get(position).getAirTemperature()+"°C");
        humidity_lv.setText(arrayList.get(position).getHumidity()+"%");
        wind_speed_lv.setText(arrayList.get(position).getWindSpeed()+ "m/s");
        pressure_lv.setText(arrayList.get(position).getBarometricPressure()+ "hPa");
        return convertView;
    }
}
