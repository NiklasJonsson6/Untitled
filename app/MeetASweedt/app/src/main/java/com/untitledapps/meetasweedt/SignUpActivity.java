package com.untitledapps.meetasweedt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etCountry = (EditText) findViewById(R.id.etCountry);

        final TextView titleSignUp = (TextView) findViewById(R.id.textSingUp);
        final TextView titleMeet = (TextView) findViewById(R.id.textTitle);


        final Button buttonRegister = (Button) findViewById(R.id.buttonRegister);



    }
}
