package graph_http.graph_http;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String DOMEN = "http://ovondo.com:5000/graphql";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onToAuthenticateActivityClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AuthenticateActivity.class);
        startActivity(intent);
    }


    public void onToRegistrationActivityClick(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }

    public void onToOvondoQueryActivityClick(View view) {
        Intent intent = new Intent(getApplicationContext(), OvondoQueryActivity.class);
        startActivity(intent);
    }

    public void onToUpdateLocationActivityClick(View view) {
        Intent intent = new Intent(getApplicationContext(), UpdateLocaionActivity.class);
        startActivity(intent);
    }
}
