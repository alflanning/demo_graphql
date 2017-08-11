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

public class RegistrationActivity extends AppCompatActivity {

    EditText businessName, businessPhone, webSiteUrl, state, city, firstName, lastName, phone, address, email, password;
    Button registrationBtn;

    String businessNameString, businessPhoneString, webSiteUrString, stateString,
            cityString, firstNameString, lastNameString, phoneString, addressString, emailString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        businessName = (EditText) findViewById(R.id.idEditCompanyName);
        businessPhone = (EditText) findViewById(R.id.idEditCompanyPhone);
        webSiteUrl = (EditText) findViewById(R.id.idEditCompanyWebSite);
        state = (EditText) findViewById(R.id.idEditState);
        city = (EditText) findViewById(R.id.idEditCity);
        firstName = (EditText) findViewById(R.id.idEditUserName);
        lastName = (EditText) findViewById(R.id.idEditUserSecondName);
        phone = (EditText) findViewById(R.id.idEditUserPhone);
        address = (EditText) findViewById(R.id.idEditAdress);
        email = (EditText) findViewById(R.id.idEditEmail);
        password = (EditText) findViewById(R.id.idEditPassword);

        registrationBtn = (Button) findViewById(R.id.idRegistrationButton);
    }


    public void onRegistrationUserClick(View view) {
        businessNameString = businessName.getText().toString();
        businessPhoneString = businessPhone.getText().toString();
        webSiteUrString = webSiteUrl.getText().toString();
        stateString = state.getText().toString();
        cityString = city.getText().toString();
        firstNameString = firstName.getText().toString();
        lastNameString = lastName.getText().toString();
        phoneString = phone.getText().toString();
        addressString = address.getText().toString();
        emailString = email.getText().toString();
        passwordString = password.getText().toString();


        new doRegistrationParseTask().execute();
    }


    private class doRegistrationParseTask extends AsyncTask<Void, Void, String> {

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

                w.write("{\"query\":\"mutation RegisterMerchantMutation {\\n  registerMerchant(\\n\\t  input:\\n\\t  {\\n\\t\\tbusinessName:\\\""
                + businessNameString +
                "\\\",\\n\\t\\tbusinessPhone : \\\"" + businessPhoneString + "\\\",\\n\\t\\twebSiteUrl : \\\"" + webSiteUrString +
                "\\\",\\n\\t\\tstate : \\\"" + stateString + "\\\",\\n\\t\\tcity : \\\"" + cityString +
                "\\\",\\n\\t\\tfirstName : \\\"" + firstNameString + "\\\",\\n\\t\\tlastName : \\\"" + lastNameString +
                "\\\",\\n\\t\\tphone : \\\"" + phoneString + "\\\"\\n\\n\\t\\taddress : \\\"" + addressString + "\\\",\\n\\t\\temail : \\\"" +
                emailString + "\\\",\\n\\t\\tpassword : \\\"" + passwordString + "\\\"\\n\\t  }\\n\\t  )\\n  {\\n    merchant\\n\\t{\\n      id\\n      businessName\\n    }\\n  }\\n}\",\"variables\":null,\"operationName\":\"RegisterMerchantMutation\"}");
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

            try {
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
            }





        }

    }
}
