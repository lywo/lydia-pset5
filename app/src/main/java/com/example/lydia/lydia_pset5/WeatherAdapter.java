package com.example.lydia.lydia_pset5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lydia on 18-5-2016.
 */
public class WeatherAdapter extends ArrayAdapter<WeatherData> {
    // Constructor
    public WeatherAdapter(Context context, ArrayList<WeatherData> weather) {
        super(context, 0, weather);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.row_layout, parent, false);

        // get WeatherData and content strings
        WeatherData currentWeatherData = (WeatherData) getItem(position);
        String name;
        if(currentWeatherData.getName()!= null);{
            name = currentWeatherData.getName();
        }
        String description = currentWeatherData.getWeatherDescription();
        String temperature = currentWeatherData.getTempCurrent();
        String minTemp = currentWeatherData.getTempMin();
        String maxTemp = currentWeatherData.getTempMax();
        String windSpd = currentWeatherData.getWindSpeed();

        // get textViews for content
        TextView contentNameTV = (TextView) view.findViewById(R.id.contentName);
        TextView contentDescriptionTV = (TextView) view.findViewById(R.id.contentDescription);
        TextView contentTemperatureTV = (TextView) view.findViewById(R.id.contentTemperature);
        TextView contentMinTemperatureTV = (TextView) view.findViewById(R.id.contentMinTemperature);
        TextView contentMaxTemperatureTV = (TextView) view.findViewById(R.id.contentMaxTemperature);
        TextView contentWindSpeedTV = (TextView) view.findViewById(R.id.contentWindSpeed);

        // set content in TextViews
        contentNameTV.setText(name);
        contentDescriptionTV.setText(description);
        contentMaxTemperatureTV.setText(maxTemp);
        contentMinTemperatureTV.setText(minTemp);
        contentTemperatureTV.setText(temperature);
        contentWindSpeedTV.setText(windSpd);

        return view;
    }
}
