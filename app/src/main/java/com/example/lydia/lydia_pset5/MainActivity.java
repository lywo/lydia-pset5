package com.example.lydia.lydia_pset5;

/*
Main activity which controls cityname input from Json query via CityAsyncTask and HTTPRequestHelper
Found results of WeatherData are put in ListView via control of WeatherAdapter
Next cityname can be searched for weather information
 */

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity {
    ArrayList<WeatherData> weather = new ArrayList<>();
    WeatherAdapter myAdapter;
    static Boolean favouritesloaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAdapter = new WeatherAdapter(this, weather);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        EditText myEditText = (EditText) findViewById(R.id.newRequestET);
        final Button searchBT = (Button) findViewById(R.id.searchButton);
        final DBHelper myDB = new DBHelper(this);

        // Debug function only DANGER!!
        // myDB.DeleteAll();

        if (favouritesloaded == false){
            ArrayList<String> savedNames = (ArrayList) myDB.readDB();
            int size = savedNames.size();
            Toast.makeText(this, "Loading favourites", Toast.LENGTH_SHORT).show();
            for(int i = 0; i < size; i++ ){
                new CityAsyncTask(MainActivity.this).execute(savedNames.get(i));
            }
            favouritesloaded = true;
        }

        /*
        work around error when executing html request when turned to landscapte view
        To Do fix error with AsyncTask
         */
        Boolean Enabled = true;
        int Visibility = VISIBLE;
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
            Enabled = false;
            Visibility = GONE;
        }
        myEditText.setVisibility(Visibility);
        myEditText.setEnabled(Enabled);
        searchBT.setVisibility(Visibility);
        searchBT.setEnabled(Enabled);

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

        final ListView weatherLV = (ListView) findViewById(R.id.weatherDataLV);
        weatherLV.setAdapter(myAdapter);

        weatherLV.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Adding favourite", Toast.LENGTH_SHORT).show();
                WeatherData newWeatherData = (WeatherData) weatherLV.getItemAtPosition(position);
                String cityName = newWeatherData.getName();
                int IndexCountry = cityName.lastIndexOf(",");
                cityName = cityName.substring(0, IndexCountry);
                myDB.addItem(cityName);
            }
        });

        weatherLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast to let user know item was deleted
                Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                WeatherData oldWeather = (WeatherData) weatherLV.getItemAtPosition(position);

                String nameCity = oldWeather.getName();
                int IndexCountry = nameCity.lastIndexOf(",");
                nameCity = nameCity.substring(0, IndexCountry);

                // Delete old weather info
                weather.remove(oldWeather);
                myDB.deleteItem(nameCity);

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

        // hide keyboard when new city is searched
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        // clear EditText
        assert tagInputET != null;
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
            assert listView != null;
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("d", weather);
    }
}
