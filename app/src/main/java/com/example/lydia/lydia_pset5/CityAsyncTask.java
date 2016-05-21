package com.example.lydia.lydia_pset5;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Lydia on 18-5-2016.
 */
public class CityAsyncTask  extends AsyncTask <String, Integer, String>{
    Context context;
    MainActivity activity;

    // constructor
    public CityAsyncTask(MainActivity Mainactivity){
        this.activity = Mainactivity;
        this.context = this.activity.getApplicationContext();
    }

    @Override
    protected void onPreExecute(){
        Toast.makeText(context, "Weather is loading from server", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {
        return HTTPRequestHelper.serverDownload( params);
    }

    @Override
    protected void onProgressUpdate(Integer ... values){
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute (String result){
        super.onPostExecute(result);
        if (result.length() == 0){
            Toast.makeText(context, "No weather was found", Toast.LENGTH_SHORT).show();
        }
        else{
            WeatherData newWeatherData = null;
            // parse JSON
            try {
                JSONObject responsejObj = new JSONObject(result);
                JSONArray weatherjArr = responsejObj.getJSONArray("weather");
                JSONObject weatherItemjObj = weatherjArr.getJSONObject(0);
                String weatherDes = weatherItemjObj.getString("description");
                String weatherMain = weatherItemjObj.getString("main");
                String weatherDescription = weatherMain + " - " + weatherDes;
                JSONObject mainjObj = responsejObj.getJSONObject("main");
                String temperature = String.format("%1$,.1f", mainjObj.getDouble("temp") - 273.15) + " C";
                String maxTemperature = String.format("%1$,.1f", mainjObj.getDouble("temp_max") - 273.15) + " C";
                String minTemperature = String.format("%1$,.1f", mainjObj.getDouble("temp_min") - 273.15) + " C";
                JSONObject windjObj = responsejObj.getJSONObject("wind");
                String windSpeed = String.format("%1$,.1f", windjObj.getDouble("speed"))+ " mph";
                //JSONObject cloudjObj = responsejObj.getJSONObject("clouds");
                String name = responsejObj.getString("name");
                JSONObject sysjObj = responsejObj.getJSONObject("sys");
                String country = sysjObj.getString("country");
                String completeName = name + ", " + country;

                // adding values to dataset (weatherDescription, tempCurrent, tempMax, tempMin, windSpeed)
                newWeatherData = new WeatherData(completeName, weatherDescription, temperature, maxTemperature, minTemperature, windSpeed);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // call MainActivity to set data to ListView
            this.activity.setData(newWeatherData);
        }
    }
}
