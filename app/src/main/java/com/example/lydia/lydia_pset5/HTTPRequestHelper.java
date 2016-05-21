package com.example.lydia.lydia_pset5;

import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lydia on 18-5-2016.
 */
public class HTTPRequestHelper {
    // make String for url
    private static final String base_url =  "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String key = "&APPID=fea160f621de96f732bac7f38500f1ff";

    // method to download form server
    protected static synchronized String serverDownload (String ... values) {
        // declare return String result
        String result = "";

        // get chosen city response
        String chosenCity = values[0];

        // complete String for url
        String weatherURL = base_url + chosenCity + key;

        // convert String to url
        URL url = null;
        try {
            url = new URL(weatherURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // make connection
        HttpURLConnection connection = null;
        if (url != null){
            try {
                connection =  (HttpURLConnection) url.openConnection();

                // open connection ; set request method
                connection.setRequestMethod("GET");

                // get response code
                Integer responseCode = connection.getResponseCode();

                // if 200-299 read inpustream
                if (200 <= responseCode && responseCode <= 299){
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String currentLine;
                    while ((currentLine = bufferedReader.readLine()) != null){
                        result = result + currentLine;
                    }
                }
                // else read error stream
                else{
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    // ToDo read error stream and communicate error
                    return null;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
