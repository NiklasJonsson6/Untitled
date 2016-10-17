package com.untitledapps.meetasweedt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.NetworkShared.RequestGetPerson;
import com.example.NetworkShared.RequestVerifyPassword;
import com.example.NetworkShared.ResponsVerifyPassword;
import com.example.NetworkShared.ResponseGetPerson;
import com.example.Server.PasswordStorage;
import com.untitledapps.Client.RequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class SignInActivity extends AppCompatActivity {

    EditText etUsername,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        final Button buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SignInActivity.this, ProfileActivity.class);
                //startActivity(intent);
                goToProfileActivity();
            }
        });}

    private void goToProfileActivity() {

        final RequestVerifyPassword req = new RequestVerifyPassword(etUsername.getText().toString(), etPassword.getText().toString());

        RequestBuilder requestBuilder = new RequestBuilder(this, new RequestBuilder.Action() {
            @Override
            public void PostExecute() {
                if (req.was_successfull()) {
                    ((MeetASweedt) getApplicationContext()).setLoggedInPerson(getPersonFromDatabase(etUsername.getText().toString()));
                    if (((MeetASweedt) getApplicationContext()).getLoggedInPerson() != null) {
                        Intent intent = new Intent(SignInActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    } else {
                        System.out.println("Userdata for user: " + etUsername.getText().toString() + " not retrieved from database");
                    }
                } else {
                    System.out.println("got:'" + etUsername.getText().toString() + "' '" + etPassword.getText().toString() + "'");
                    System.out.println("val:'" + req.username + "' '" + req.password + "'");
                    System.out.println("invalid password or username!");
                }
            }
        });

        requestBuilder.addRequest(req);
        requestBuilder.execute();

    }

    private Person getPersonFromDatabase(String username) {
        final Person person = new Person();

        final RequestGetPerson req = new RequestGetPerson(username);

        RequestBuilder requestBuilder = new RequestBuilder(this, new RequestBuilder.Action() {
            @Override
            public void PostExecute() {
                if (req.was_successfull()) {
                    ResponseGetPerson r = req.getResponse();

                    ArrayList<String> interests = new ArrayList<>(Arrays.asList(r.getInterests().split(",")));
                    person.setInterests(interests);
                    person.setLearner(r.getIsLearner());
                    person.setAge(r.getAge());
                    person.setName(r.getName());
                    person.setOrginCountry(r.getOriginCountry());
                    person.setLongitude(r.getLongitude());
                    person.setLatitude(r.getLatitude());
                    person.setUsername(r.getUsername());
                    person.setUser_id(r.getId());
                } else {
                    System.out.println("get person from database not successful");
                }
            }
        });

        requestBuilder.addRequest(req);
        requestBuilder.execute();

        if (person.getUsername() != null) {
            return person;
        } else {
            return null;
        }
    }
}
