package com.example.bruker.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Context;
import android.content.*;
import java.net.*;
import java.io.*;
import org.json.*;
import android.os.*;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name, password;
    String Name, Password;
    Context ctx=this;
    String NAME=null, PASSWORD=null, UID=null;
    String CID=null, TIME=null, PRIORITY=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.main_name);
        password = (EditText) findViewById(R.id.main_password);
    }

    public void main_login(View v){
        Name = name.getText().toString();
        Password = password.getText().toString();
        BackGround b = new BackGround();

        if(!Name.isEmpty() && !Password.isEmpty()) {
            b.execute(Name, Password);
        }
        else {
            Toast.makeText(getApplicationContext(), "Skriv inn brukernavn og passord!", Toast.LENGTH_LONG).show();
        }
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String password = params[1];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://10.0.2.2/login.php");
                String urlParams = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                urlParams += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err=null;
            try {           //når vi får problemer med return-value ikke er JSONObject, bruk throw? se C++ lecture slides
                JSONObject root = new JSONObject(s);
                //JSONArray root = new JSONArray(s);
                JSONObject user_data = root.getJSONObject("user_data");
                boolean error = user_data.getBoolean("error");

                if(!error) {
                    //JSONObject event_data = root.getJSONObject("event_data");
                    //JSONObject user_data = event_data.getJSONObject("user_info");

                    //JSONObject user_data = event_data.getJSONObject();
                    //UID = user_data.getString("uid");
                    NAME = user_data.getString("userName");
                    PASSWORD = user_data.getString("userPass");

                    //CID = user_data.getString("cid");
                    //TIME = user_data.getString("timestamp");
                    //PRIORITY = user_data.getString("priority");


                    Intent i = new Intent(ctx, Home.class);
                    //i.putExtra("uid", UID);
                    i.putExtra("userName", NAME);
                    i.putExtra("userPass", PASSWORD);
                    i.putExtra("err", err);

                    //i.putExtra("userName", CID);
                    //i.putExtra("userPass", TIME);
                    //i.putExtra("err", err);

                    startActivity(i);
                } else {
                    String errorMsg = user_data.getString("error_msg");
                    Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                String errorMsg = "onPostExecution Exception: " + e.getMessage();
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
            }catch (NullPointerException e){
                e.printStackTrace();
                err = "Nullpointerexception: " + e.getMessage();
            }

            /*Intent i = new Intent(ctx, Home.class);
            //i.putExtra("userName", NAME);
            //i.putExtra("userPass", PASSWORD);
            i.putExtra("err", err);

            i.putExtra("userName", CID);
            i.putExtra("userPass", TIME);
            //i.putExtra("err", err);

            startActivity(i);*/

        }
    }
}