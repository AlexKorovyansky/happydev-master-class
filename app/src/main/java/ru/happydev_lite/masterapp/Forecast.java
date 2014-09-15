/*
 * Copyright (C) 2014 Medlert, Inc.
 */
package ru.happydev_lite.masterapp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Forecast
 */
public class Forecast implements Serializable {

    private static final Random RANDOM = new Random(System.currentTimeMillis());
    private final Date date;
    private final double dayTemp;
    private final double nightTemp;
    private final String description;
    private final String iconUrl;

    public static Forecast makeRandom(Date date) {
        double dayTemp = RANDOM.nextDouble() * 10.0;
        double nightTemp = RANDOM.nextDouble() * 5.0;
        return new Forecast(date, dayTemp, nightTemp, "Random", null);
    }

    public static List<Forecast> makeRandom(int daysNumber) {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        List<Forecast> result = new ArrayList<Forecast>();
        for (int i = 0; i < daysNumber; ++i) {
            if (i > 0) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            Forecast forecast = makeRandom(calendar.getTime());
            result.add(forecast);
        }
        return result;
    }

    public Forecast(Date date, double dayTemp, double nightTemp, String description, String iconUrl) {
        this.date = date;
        this.dayTemp = dayTemp;
        this.nightTemp = nightTemp;
        this.description = description;
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM");
        return capitalize(dateFormat.format(date)) + String.format(" (%.1f *C)", dayTemp);
    }

    public String dateName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM");
        return capitalize(dateFormat.format(date));
    }

    public Date getDate() {
        return date;
    }

    public double getDayTemp() {
        return dayTemp;
    }

    public double getNightTemp() {
        return nightTemp;
    }

    public String getDescription() {
        return description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    private static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

}
