package ru.happydev_lite.masterapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
 
    public static class Forecast {
 
        public static class Temp {
            @SerializedName("day")
            public final double day;
 
            @SerializedName("night")
            public final double night;
 
            public Temp(double day, double night) {
                this.day = day;
                this.night = night;
            }
        }
 
        public static class Weather {
            @SerializedName("icon")
            public final String icon;
 
            @SerializedName("description")
            public final String description;
 
            public Weather(String icon, String description) {
                this.icon = icon;
                this.description = description;
            }
        }
 
        @SerializedName("dt")
        public final long dt;
 
        @SerializedName("temp")
        public final Temp temp;
 
        @SerializedName("weather")
        public final List<Weather> weatherList;
 
        public Forecast(long dt, Temp temp, List<Weather> weatherList) {
            this.dt = dt;
            this.temp = temp;
            this.weatherList = weatherList;
        }
    }
 
    @SerializedName("cod")
    public final String code;
 
    @SerializedName("list")
    public final List<Forecast> forecastList;
 
    public Response(String code, List<Forecast> forecastList) {
        this.code = code;
        this.forecastList = forecastList;
    }
}