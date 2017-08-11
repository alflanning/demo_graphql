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


public class AuthenticateActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    Button authenticateBtn;

    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authenticate_activity);

        editEmail = (EditText) findViewById(R.id.idEditEmailAdress);
        editPassword = (EditText) findViewById(R.id.idEditPassword);
        authenticateBtn = (Button) findViewById(R.id.idAuthenticateClick);

    }

    public void onAuthnticateClick(View view) {
        email = editEmail.getText().toString();
        password = editPassword.getText().toString();

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

                Toast toast = Toast.makeText(getApplicationContext(), "ваш токен  " + authenticate.getString("jwtToken"), Toast.LENGTH_SHORT);
                toast.show();


                editEmail.setText(authenticate.getString("jwtToken"));


            } catch (JSONException e) {
                e.printStackTrace();

                Toast toast = Toast.makeText(getApplicationContext(), "Ошибка авторизации", Toast.LENGTH_SHORT);
                toast.show();
            }




        }

    }
}
