package graph_http.graph_http;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import graph_http.graph_http.adapters.OvondoQueryAdapter;


public class OvondoQueryActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ovondo_query_activity);



        new doOvondoQueryParseTask().execute();
    }

    private class doOvondoQueryParseTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // получаем данные с внешнего ресурса

            StringBuffer html = null;

            try {

                String url = "http://ovondo.com:5000/graphql";

                URL obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setReadTimeout(5000);

                conn.setDoOutput(true);

                OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

                w.write("{\"query\":\"query OvondoQuery\\n{\\n  allLocations {\\n    edges {\\n      node {\\n        id\\n        name\\n        merchant: merchantByMerchantId {\\n          id\\n          businessName\\n        }\\n      }\\n    }\\n  }\\n}\",\"variables\":null,\"operationName\":\"OvondoQuery\"}");
                w.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                html = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    html.append(inputLine);
                }

                in.close();
                conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return html.toString();
        }


        @Override
        protected void onProgressUpdate(Void... items) {
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            JSONObject Json = null;
            try {
                Json = new JSONObject(response);
                JSONObject data = Json.getJSONObject("data");
                JSONObject allLocations = data.getJSONObject("allLocations");
                JSONArray edges = allLocations.getJSONArray("edges");


                LinearLayoutManager llm = new LinearLayoutManager(OvondoQueryActivity.this);
                RecyclerView rv = (RecyclerView)findViewById(R.id.AllLocationsRecyclerView);
                rv.setLayoutManager(llm);

                OvondoQueryAdapter adapter = new OvondoQueryAdapter(edges);
                rv.setAdapter(adapter);






                /*Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(edges.length()), Toast.LENGTH_SHORT);
                toast.show();*/

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}
