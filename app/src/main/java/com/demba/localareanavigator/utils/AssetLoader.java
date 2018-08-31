package com.demba.localareanavigator.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class AssetLoader {

    private static final String DELIMITER_NEW_LINE = "\n";

    public static String loadGeoJson(@NonNull Context context, String filename) {
        try {
            InputStream inputStream = context.getAssets().open(filename);
            return new BufferedReader(
                    new InputStreamReader(inputStream))
                    .lines()
                    .parallel()
                    .collect(Collectors.joining(DELIMITER_NEW_LINE));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
