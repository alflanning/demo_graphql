package graph_http.graph_http;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateLocaionActivity extends AppCompatActivity {

    EditText businessNameUpdate, businessPhoneUpdate, stateUpdate, cityUpdate, addressUpdate;
    Button updateBtn;

    String businessNameString, businessPhoneString, stateString, cityString, addressString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_location_activity);

        businessNameUpdate = (EditText) findViewById(R.id.idEditUpdateNameUser);
        businessPhoneUpdate = (EditText) findViewById(R.id.idEditUpdatePhoneCompany);
        stateUpdate = (EditText) findViewById(R.id.idEditUpdateState);
        cityUpdate = (EditText) findViewById(R.id.idEditUpdateCity);
        addressUpdate = (EditText) findViewById(R.id.idEditUpdateAdress);

        updateBtn = (Button) findViewById(R.id.idUpdateLocationClick);
    }

    public void onUpdateLocationClick(View view) {
        businessNameString = businessNameUpdate.getText().toString();
        businessPhoneString = businessPhoneUpdate.getText().toString();
        stateString = stateUpdate.getText().toString();
        cityString = cityUpdate.getText().toString();
        addressString = addressUpdate.getText().toString();


        new doUpdateLocationParseTask().execute();
    }


    private class doUpdateLocationParseTask extends AsyncTask<Void, Void, String> {

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
                conn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYmFtYmlub19hZG1pbiIsInVzZXJfaWQiOjUzLCJpYXQiOjE1MDI0NTg0MDIsImV4cCI6MTUwMjU0NDgwMiwiYXVkIjoicG9zdGdyYXBocWwiLCJpc3MiOiJwb3N0Z3JhcGhxbCJ9.j7K8Sn1lq9JY--UgtYHDnpVR3LQaaI5UjlhNHarNieE");

                conn.setReadTimeout(5000);

                conn.setDoOutput(true);

                OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

                w.write("{\"query\":\"\\nmutation UpdateLocationMutation {\\n  updateLocation(input:{\\n    id:\\\"63\\\",\\n    name:\\\"jora216\\\",\\n    businessPhone:\\\"12341234\\\",\\n    state:\\\"AL\\\",\\n    city:\\\"TEST CITY FOR UPDATE\\\",\\n    address:\\\"jora address\\\",\\n    isDefault:false\\n  }) {\\n    clientMutationId\\n  }\\n}\",\"variables\":null,\"operationName\":\"UpdateLocationMutation\"}");
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


            Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(response), Toast.LENGTH_SHORT);
            toast.show();




            /*try {
                JSONObject Json = new JSONObject(response);
                JSONObject data = Json.getJSONObject("data");
                JSONObject registerMerchant = data.getJSONObject("registerMerchant");
                JSONObject merchant = registerMerchant.getJSONObject("merchant");

                Toast toast = Toast.makeText(getApplicationContext(), "Пользватель под именем " + merchant.getString("businessName") + " успешно зарегистрирован", Toast.LENGTH_SHORT);
                toast.show();


            } catch (JSONException e) {
                e.printStackTrace();

                Toast toast = Toast.makeText(getApplicationContext(), "Ошибка регистрации", Toast.LENGTH_SHORT);
                toast.show();
            }*/





        }

    }

}
