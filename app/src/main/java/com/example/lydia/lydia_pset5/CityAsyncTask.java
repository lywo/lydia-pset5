package com.example.lydia.lydia_pset5;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lydia on 18-5-2016.
 */
public abstract class CityAsyncTask  extends AsyncTask{

    Context context;
    Activity activity;

    // constructor
    public CityAsyncTask(MainActivity activity){
        this.activity = activity;
        this.context = this.activity.getApplicationContext();
    }

    @Override
    protected void onPreExecute(){
        Toast.makeText(context, "Weather is loading from server", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Object doInBackground(String... params) {
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
        // parse JSON
        else{
            ArrayList<WeatherData> weatherData = new ArrayList<>();


            /// todo
        }
    }
}
