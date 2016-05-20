package com.example.lydia.lydia_pset5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText tagInputET = (EditText) findViewById(R.id.newRequestET);
        ListView weatherLV = (ListView) findViewById(R.id.weatherDataLV);
    }

    public void getData(View view){
        // get user input
        EditText tagInputET = (EditText) findViewById(R.id.newRequestET);
        String userInput = tagInputET.getText().toString();
    }

    public void setData(ArrayList<WeatherData> weatherData){

    }
}
