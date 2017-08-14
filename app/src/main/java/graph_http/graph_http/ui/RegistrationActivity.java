package graph_http.graph_http.ui;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import graph_http.graph_http.R;
import graph_http.graph_http.utils.Constants;

public class RegistrationActivity extends AppCompatActivity {

    EditText businessName, businessPhone, webSiteUrl, state, city, firstName, lastName, phone, address, email, password;
    Button registrationBtn;
    TextView errorEmptyFields, errorStateName;
    ScrollView myScroll;
    FrameLayout frameRegistrationLayout;
    String businessNameString, businessPhoneString, webSiteUrString, stateString,
            cityString, firstNameString, lastNameString, phoneString, addressString, emailString, passwordString;
    private ProgressDialog mRegistrationSpinner;
    List<String> arrayFields = new ArrayList<>();

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
        errorEmptyFields = (TextView) findViewById(R.id.idErrorEmptyFieldsText);
        errorStateName = (TextView) findViewById(R.id.idErrorStateNameText);
        registrationBtn = (Button) findViewById(R.id.idRegistrationButton);
        myScroll = (ScrollView) findViewById(R.id.idRegistrationScrollView);
        frameRegistrationLayout = (FrameLayout) findViewById(R.id.idFrameRegistrationLayout);

        mRegistrationSpinner = new ProgressDialog(this);
        mRegistrationSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRegistrationSpinner.setMessage("Регистрация");
    }


    public void onRegistrationUserClick(View view) {
        mRegistrationSpinner.show();
        errorEmptyFields.setVisibility(View.GONE);
        errorStateName.setVisibility(View.GONE);
        arrayFields.clear();
        businessNameString = businessName.getText().toString();
        arrayFields.add(businessNameString);
        businessPhoneString = businessPhone.getText().toString();
        arrayFields.add(businessPhoneString);
        webSiteUrString = webSiteUrl.getText().toString();
        arrayFields.add(webSiteUrString);
        stateString = state.getText().toString();
        arrayFields.add(stateString);
        cityString = city.getText().toString();
        arrayFields.add(cityString);
        firstNameString = firstName.getText().toString();
        arrayFields.add(firstNameString);
        lastNameString = lastName.getText().toString();
        arrayFields.add(lastNameString);
        phoneString = phone.getText().toString();
        arrayFields.add(phoneString);
        addressString = address.getText().toString();
        arrayFields.add(addressString);
        emailString = email.getText().toString();
        arrayFields.add(emailString);
        passwordString = password.getText().toString();
        arrayFields.add(passwordString);

        if (isExistEmptyFields(arrayFields)) {
            mRegistrationSpinner.dismiss();
            errorEmptyFields.setVisibility(View.VISIBLE);
            myScroll.scrollTo(0, 0);
        } else if (stateString.length() != 2) {
            mRegistrationSpinner.dismiss();
            errorStateName.setVisibility(View.VISIBLE);
            myScroll.scrollTo(0, 0);
        } else
            new doRegistrationParseTask().execute();
    }

    private boolean isExistEmptyFields(List<String> arrayFields) {
        for (int i = 0; i < arrayFields.size(); i++) {
            String field = String.valueOf(arrayFields.get(i));
            if (Objects.equals(field, null) | Objects.equals(field, "")) {
                return true;
            }
        }
        return false;
    }

    public void onFrameRegistrationLayoutClick(View view) {
        Constants.setLogin(emailString);
        Constants.setPassword(passwordString);
        this.finish();
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
                emailString + "\\\",\\n\\t\\tpassword : \\\"" + passwordString +
                "\\\"\\n      \\n\\t  }\\n\\t  )\\n  {\\n    merchant\\n\\t{\\n      id\\n      businessName\\n    locationsByMerchantId {\\n      edges {\\n        node {\\n          id\\n        }\\n      }\\n    }\\n    }\\n  }\\n}\",\"variables\":null,\"operationName\":\"RegisterMerchantMutation\"}");
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
                mRegistrationSpinner.dismiss();
                JSONObject Json = new JSONObject(response);
                JSONObject data = Json.getJSONObject("data");
                JSONObject registerMerchant = data.getJSONObject("registerMerchant");
                JSONObject merchant = registerMerchant.getJSONObject("merchant");

                frameRegistrationLayout.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();

                mRegistrationSpinner.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), "Ошибка регистрации", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}


                /*JSONObject locationsByMerchantId = merchant.getJSONObject("locationsByMerchantId");
                JSONArray edges = locationsByMerchantId.getJSONArray("edges");

                JSONObject node = edges.getJSONObject(0);*/

                /*Toast toast2 = Toast.makeText(getApplicationContext(), node.getString("id"), Toast.LENGTH_SHORT);
                toast2.show();*/

