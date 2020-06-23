package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    double celsiusTemperature;
    String weatherConditions;
    String cityName;
    String sunriseTime;
    String sunsetTime;
    String iconName;

    TextView cityNameTextView;
    TextView celsiusTemperatureTextView;
    TextView weatherConditionsTextView;
    TextView sunriseTimeTextView;
    TextView sunsetTimeTextView;

    ImageView iconImageView;
    EditText cityEnterEditText;

    String userInputCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityNameTextView = findViewById(R.id.city_name_text_view);
        celsiusTemperatureTextView = findViewById(R.id.temperature_text_view);
        weatherConditionsTextView = findViewById(R.id.description_text_view);
        sunriseTimeTextView = findViewById(R.id.sunrise_time_text_view);
        sunsetTimeTextView = findViewById(R.id.sunset_time_text_view);

        iconImageView = findViewById(R.id.weather_icon_image_view);
        cityEnterEditText = findViewById(R.id.city_enter_edit_text);

        userInputCityName = "Stony Brook";

        acquireInformation();
    }

    public void acquireInformation()
    {
        String weatherData;
        WeatherTask weather = new WeatherTask();

        try {
            weatherData = weather.execute("http://api.openweathermap.org/data/2.5/weather?q=" + userInputCityName + "&APPID=cdda5dbe3af2da3036b7d7f47576a2bc").get();
            if(weather == null)
            {
                throw new Exception();
            }
            JSONObject jsonObject = new JSONObject(weatherData);

            JSONObject jsonMainObject = jsonObject.getJSONObject("main");
            JSONArray jsonWeatherArray = jsonObject.getJSONArray("weather");
            JSONObject jsonSysObject = jsonObject.getJSONObject("sys");

            weatherConditions = jsonWeatherArray.getJSONObject(0).getString("description");

            cityName = jsonObject.getString("name");

            long unix_seconds_sunrise = Long.parseLong(jsonSysObject.getString("sunrise"));
            Date date_sunrise = new Date(unix_seconds_sunrise*1000L);
            SimpleDateFormat jdf_sunrise = new SimpleDateFormat("HH:mm");
            sunriseTime = jdf_sunrise.format(date_sunrise);

            long unix_seconds_sunset = Long.parseLong(jsonSysObject.getString("sunset"));
            Date date_sunset = new Date(unix_seconds_sunset*1000L);
            SimpleDateFormat jdf_sunset = new SimpleDateFormat("HH:mm");
            sunsetTime = jdf_sunset.format(date_sunset);

            iconName = jsonWeatherArray.getJSONObject(0).getString("icon");

            celsiusTemperature = jsonMainObject.getDouble("temp") - 273.15;

            setDisplayView();
            return;

        } catch (Exception e) {
            Toast.makeText(this, "Entered an Invalid City Name", Toast.LENGTH_LONG).show();

            userInputCityName = "Stony Brook";
            acquireInformation();
        }
    }

    public void setDisplayView()
    {
        try {
            cityNameTextView.setText(cityName);
            celsiusTemperatureTextView.setText((String.format("%.2f", celsiusTemperature)) + "°C");
            weatherConditionsTextView.setText(weatherConditions);
            sunriseTimeTextView.setText("Sunrise: " + sunriseTime);
            sunsetTimeTextView.setText("Sunset: " + sunsetTime);

            Picasso.get().load("http://openweathermap.org/img/w/" + iconName + ".png").resizeDimen(R.dimen.icon_image_size, R.dimen.icon_image_size).into(iconImageView);

        } catch (Exception e) {
            Toast.makeText(this, "Could not load icon image", Toast.LENGTH_LONG).show();

            userInputCityName = "Stony Brook";
            acquireInformation();
        }
    }

    public void selectCity(View view)
    {
        if(TextUtils.isEmpty(cityEnterEditText.getText().toString()))
        {
            Toast.makeText(this, "City field is Empty", Toast.LENGTH_LONG).show();
        }

        userInputCityName = cityEnterEditText.getText().toString();
        acquireInformation();
    }
}