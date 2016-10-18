package com.untitledapps.meetasweedt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.NetworkShared.RequestCreateUser;
import com.untitledapps.Client.RequestBuilder;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    private Person person;
    private int countryIndex;

    private EditText etName;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etAge;
    private EditText etCountry;
    private Button buttonRegister;
    public static Spinner spCountry;
    private RatingBar sweLvl;
    private TextView valueLvl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = (EditText) findViewById(R.id.etName);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etAge = (EditText) findViewById(R.id.etAge);

        final TextView titleSignUp = (TextView) findViewById(R.id.textSingUp);
        final TextView titleMeet = (TextView) findViewById(R.id.textTitle);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButtonPressed();
            }
        });

        spCountry = (Spinner)findViewById(R.id.spCountry);

        //currently only the countries with most immigrants
        String[] countries = new String[]{"Afghanistan", "Irak", "Somalia", "Syrien", "Turkiet", "Sverige", "Ursprungsland"};

        final int countrylistsize = countries.length - 1;

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, countries){
            @Override
            public int getCount(){
                return(countrylistsize);
            }
        };

        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCountry.setAdapter(countryAdapter);
        spCountry.setSelection(countrylistsize);

        addListenerOnRatingBar();

    }

    ////hur visa upp på profile activity????
    public void addListenerOnRatingBar(){
        sweLvl = (RatingBar) findViewById(R.id.ratingBar);
        //valueLvl = (TextView) findViewById(R.id.txtRatingvalue);

        sweLvl.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(valueLvl != null)
                    valueLvl.setText(String.valueOf(rating));
            }
        });
    }


    public void registerButtonPressed() {
        //person = new Person(true /*not yet implemented*/, Integer.parseInt(etAge.getText().toString()),
        //        etName.getText().toString(), "Sweden" /*should maybe be swedish true/false?*/,
        //        20, 20 /*get longitude/latitude somehow*/, 20 /*example*/, new ArrayList<String>());

        //// TODO: 2016-10-05 check user entered valid data (not left empty)

        final RequestCreateUser req =
                new RequestCreateUser(
                        false,
                        etAge.getText().toString().equals("")? 0: Integer.parseInt(etAge.getText().toString()),
                        etName.getText().toString(),
                        spCountry.getSelectedItem().toString(),
                        -1,
                        -1,
                        new ArrayList<String>(),
                        etUsername.getText().toString(),
                        etPassword.getText().toString()
                );

        RequestBuilder builder = new RequestBuilder(this, new RequestBuilder.Action() {
            @Override
            public void PostExecute() {
                System.out.println(req.was_successfull()?"successfully created account":"could not create");
            }
        });

        builder.addRequest(req);
//        builder.execute();

        //after the registration go to interest list
        Intent intent = new Intent(this, SignUp2Activity.class);
        intent.putExtra("req",req);
        startActivity(intent);
    }


}
