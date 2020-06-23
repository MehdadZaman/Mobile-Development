package com.example.weatherapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String[] strings) {
        //String... means multiple address can be send. It acts as array
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int byteRead = inputStreamReader.read();
            String content = "";
            char readCharacter;

            while (byteRead != -1) {
                readCharacter = (char)byteRead;
                content = content + readCharacter;
                byteRead = inputStreamReader.read();
            }

            return content;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}