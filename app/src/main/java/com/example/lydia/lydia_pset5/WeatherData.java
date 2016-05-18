package com.example.lydia.lydia_pset5;

/**
 * Created by Lydia on 18-5-2016.
 */
public class WeatherData {
    private String weatherDescription;
    private String tempCurrent; // in Kelvin - 273,15
    private String tempMin; // in Kelvin
    private String tempMax; //in Kelvin
    private String windSpeed; //in mph

    // constructor
    public WeatherData (String weatherDescription, String tempCurrent, String tempMax, String tempMin, String windSpeed){
        super();
        this.tempCurrent =tempCurrent;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.windSpeed = windSpeed;
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherDescription(){return weatherDescription;}
    public void setWeatherDescription(String weatherDescription){this.weatherDescription = weatherDescription;}

    public String getTempCurrent(){return tempCurrent;}
    public void setTempCurrent(String tempCurrent){this.tempCurrent = tempCurrent;}

    public String getTempMin(){return tempMin;}
    public void setTempMin(String tempMin){this.tempMin = tempMin;}

    public String getTempMax(){return tempMax;}
    public void setTempMax(String tempMax){this.tempMax = tempMax;}

    public String getWindSpeed(){return windSpeed;}
    public void setWindSpeed(String windSpeed){this.windSpeed = windSpeed;}

}
