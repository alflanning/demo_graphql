package graph_http.graph_http.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import graph_http.graph_http.R;
import graph_http.graph_http.utils.Constants;

public class MainActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    TextView errorEnterText;
    String email, password;

    private ProgressDialog mAuthenticateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = (EditText) findViewById(R.id.idEditEmailAdress);
        editPassword = (EditText) findViewById(R.id.idEditPassword);
        errorEnterText = (TextView) findViewById(R.id.idErrorEnterText);

        mAuthenticateSpinner = new ProgressDialog(this);
        mAuthenticateSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mAuthenticateSpinner.setMessage("Авторизация");
    }

    @Override
    protected void onResume() {
        super.onResume();

        String login = Constants.getLogin();
        String password = Constants.getPassword();
        if (!login.equals("null")) {
            editEmail.setText(login);
            editPassword.setText(password);
        }
    }

    public void onAuthenticateClick(View view) {
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();
        errorEnterText.setVisibility(View.GONE);
        mAuthenticateSpinner.show();

        new doAuthenticateParseTask().execute();
    }


    private class doAuthenticateParseTask extends AsyncTask<Void, Void, String> {

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

                w.write("{\"query\":\"mutation Authenticate($email: String!, $password: String!) {\\n  authenticate(input: {email: $email, password: $password}) {\\n    jwtToken\\n  }\\n}\\n\",\"variables\":{\"email\":\"" + email + "\",\"password\":\"" + password + "\"},\"operationName\":\"Authenticate\"}");
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
                mAuthenticateSpinner.dismiss();
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
                JSONObject authenticate = data.getJSONObject("authenticate");

                String token = authenticate.getString("jwtToken");

                if (!token.equals("null")) {
                    Constants.setToken(token);
                    Intent intent = new Intent(getApplicationContext(), OvondoQueryActivity.class);
                    startActivity(intent);
                } else {
                    errorEnterText.setVisibility(View.VISIBLE);
                }
                mAuthenticateSpinner.dismiss();

                //Toast toast = Toast.makeText(getApplicationContext(), authenticate.getString("jwtToken"), Toast.LENGTH_SHORT);
                //toast.show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(), "Ошибка. Проверьте подключение к интернету.", Toast.LENGTH_SHORT);
                toast.show();
                mAuthenticateSpinner.dismiss();
            }
        }
    }

    public void onToRegistrationActivityClick(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }
}
