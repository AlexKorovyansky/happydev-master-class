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
public final class Forecast implements Serializable {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ((Object)this).getClass() != o.getClass()) return false;

        Forecast forecast = (Forecast) o;

        if (Double.compare(forecast.dayTemp, dayTemp) != 0) return false;
        if (Double.compare(forecast.nightTemp, nightTemp) != 0) return false;
        if (date != null ? !date.equals(forecast.date) : forecast.date != null) return false;
        if (description != null ? !description.equals(forecast.description) : forecast.description != null)
            return false;
        if (iconUrl != null ? !iconUrl.equals(forecast.iconUrl) : forecast.iconUrl != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = date != null ? date.hashCode() : 0;
        temp = Double.doubleToLongBits(dayTemp);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(nightTemp);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (iconUrl != null ? iconUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "date=" + date +
                ", dayTemp=" + dayTemp +
                ", nightTemp=" + nightTemp +
                ", description='" + description + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
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
        return Utils.capitalize(description);
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getDayName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        return Utils.capitalize(dateFormat.format(date));
    }

    public String getDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format); //"EEEE, dd MMM");
        return Utils.capitalize(dateFormat.format(date));
    }

}
