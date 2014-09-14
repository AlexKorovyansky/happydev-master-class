/*
 * Copyright (C) 2014 Medlert, Inc.
 */
package ru.happydev_lite.masterapp;

import java.util.Date;
import java.util.Random;

/**
 * Forecast
 */
public class Forecast {

    private final Date date;
    private final double dayTemp;
    private final double nightTemp;
    private final String description;
    private final String iconUrl;

    public static Forecast makeRandom(Date date) {
        Random random = new Random(System.currentTimeMillis());
        double dayTemp = random.nextDouble() * 10.0;
        double nightTemp = random.nextDouble() * 5.0;
        return new Forecast(date, dayTemp, nightTemp, "Random", null);
    }

    public Forecast(Date date, double dayTemp, double nightTemp, String description, String iconUrl) {
        this.date = date;
        this.dayTemp = dayTemp;
        this.nightTemp = nightTemp;
        this.description = description;
        this.iconUrl = iconUrl;
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

}
