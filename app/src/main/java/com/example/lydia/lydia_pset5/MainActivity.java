package com.example.lydia.lydia_pset5;

/*
Main activity which controls cityname input from Json query via CityAsyncTask and HTTPRequestHelper
Found results of WeatherData are put in ListView via control of WeatherAdapter
Next cityname can be searched for weather information
 */

import android.content.Context;
import android.net.wifi.WifiEnterpriseConfig;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity {
    ArrayList<WeatherData> weather = new ArrayList<>();
    WeatherAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAdapter = new WeatherAdapter(this, weather);
        final Button searchBT = (Button) findViewById(R.id.searchButton);
        searchBT.setEnabled(false);
        EditText myEditText = (EditText) findViewById(R.id.newRequestET);
        myEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    searchBT.setEnabled(false);
                } else {
                    searchBT.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EditText tagInputET = (EditText) findViewById(R.id.newRequestET);
        final ListView weatherLV = (ListView) findViewById(R.id.weatherDataLV);
        weatherLV.setAdapter(myAdapter);

        weatherLV.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // todo insert extra function
            }
        });

        weatherLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast to let user know item was deleted
                Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                WeatherData oldWeather = (WeatherData) weatherLV.getItemAtPosition(position);

                // Delete old weather info
                weather.remove(oldWeather);

                // Update ListView
                myAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    /*
    Get user data and send to asyncTask collect data when "search" button is clicked
     */
    public void getData(View view){
        // get user input
        EditText tagInputET = (EditText) findViewById(R.id.newRequestET);
        String userInput = tagInputET.getText().toString();
        new CityAsyncTask(MainActivity.this).execute(userInput);

    }

    /*
    Get data from asyncTask and set in ListView
     */
    public void setData(WeatherData newWeatherData){
        weather.add(newWeatherData);
        EditText tagInputET = (EditText)  findViewById(R.id.newRequestET);
        WeatherAdapter myAdapter  = new WeatherAdapter(this, weather);
        tagInputET.setText("");
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            weather = (ArrayList<WeatherData>) savedInstanceState.getSerializable("d");
            WeatherAdapter adapter = new WeatherAdapter(this, weather);
            ListView listView = (ListView) findViewById(R.id.weatherDataLV);
            listView.setAdapter(adapter);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("d", weather);
        super.onSaveInstanceState(outState);
    }
}
