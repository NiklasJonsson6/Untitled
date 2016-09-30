package com.untitledapps.meetasweedt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.untitledapps.Client.RequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class SignUpActivity extends AppCompatActivity {
    private Person person;

    private EditText etName;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etAge;
    private EditText etCountry;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = (EditText) findViewById(R.id.etName);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etAge = (EditText) findViewById(R.id.etAge);

        //should be swedish true/false?
        etCountry = (EditText) findViewById(R.id.etCountry);

        final TextView titleSignUp = (TextView) findViewById(R.id.textSingUp);
        final TextView titleMeet = (TextView) findViewById(R.id.textTitle);


        buttonRegister = (Button) findViewById(R.id.buttonRegister);
    }

    public void registerButtonPressed(View v) {
        person = new Person(true /*not yet implemented*/, Integer.parseInt(etAge.getText().toString()),
                etName.getText().toString(), "Sweden" /*should maybe be swedish true/false?*/,
                20, 20 /*get longitude/latitude somehow*/, 20 /*example*/, new ArrayList<String>());

        person.databaseCreateUser();
    }
}
