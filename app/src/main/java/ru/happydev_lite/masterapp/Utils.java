/*
 * Copyright (C) 2014 Medlert, Inc.
 */
package ru.happydev_lite.masterapp;

/**
 * Utils
 */
public class Utils {

    public static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private Utils() {
    }
}
