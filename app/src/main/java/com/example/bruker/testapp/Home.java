package com.example.bruker.testapp;

/**
 * Created by Bruker on 03.03.2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Home extends Activity {

    String name, password, email, Err, uid;
    TextView nameTV, emailTV, passwordTV, err, uidTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        nameTV = (TextView) findViewById(R.id.home_name);
        //emailTV = (TextView) findViewById(R.id.home_email);
        passwordTV = (TextView) findViewById(R.id.home_password);
        //uidTV = (TextView) findViewById(R.id.home_password);
        err = (TextView) findViewById(R.id.err);

        name = getIntent().getStringExtra("userName");
        //uid = getIntent().getStringExtra("uid");
        password = getIntent().getStringExtra("userPass");
        //email = getIntent().getStringExtra("email");
        Err = getIntent().getStringExtra("err");

        nameTV.setText("Welcome "+name);
        //passwordTV.setText("Your ID is "+uid);
        passwordTV.setText("Your password is "+password);
        //emailTV.setText("Your email is "+email);
        err.setText(Err);
    }
}
