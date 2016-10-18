package com.untitledapps.meetasweedt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.NetworkShared.RequestGetPerson;
import com.example.NetworkShared.RequestVerifyPassword;
import com.example.NetworkShared.ResponseGetPerson;
import com.untitledapps.Client.RequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class SignInActivity extends AppCompatActivity {

    EditText etUsername,etPassword;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.context = this;


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

                    System.out.println("postexe 1");

                    final Person person = new Person();

                    final RequestGetPerson requestGetPerson = new RequestGetPerson(etUsername.getText().toString());

                    RequestBuilder requestBuilderGetPerson = new RequestBuilder(context, new RequestBuilder.Action() {
                        @Override
                        public void PostExecute() {
                            if (requestGetPerson.was_successfull()) {

                                System.out.println("postexe2");

                                ResponseGetPerson r = requestGetPerson.getResponse();

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


                                System.out.println("setting person in sign in");


                                if (person.getUsername() != null) {
                                    ((MeetASweedt) getApplicationContext()).setLoggedInPerson(person);
                                } else {
                                    //handle
                                }

                                if (((MeetASweedt) getApplicationContext()).getLoggedInPerson() != null) {
                                    Intent intent = new Intent(SignInActivity.this, ProfileActivity.class);
                                    startActivity(intent);
                                } else {
                                    System.out.println("Userdata for user: " + etUsername.getText().toString() + " not retrieved from database");
                                }

                            } else {
                                System.out.println("get person from database not successful");
                            }
                        }
                    });

                    requestBuilderGetPerson.addRequest(requestGetPerson);
                    requestBuilderGetPerson.execute();

                    //((MeetASweedt) getApplicationContext()).setLoggedInPerson(getPersonFromDatabase(etUsername.getText().toString()));
                } else {
                    Toast.makeText(SignInActivity.this, "fel användarnamn eller lösenord", Toast.LENGTH_LONG).show();

                    System.out.println("got:'" + etUsername.getText().toString() + "' '" + etPassword.getText().toString() + "'");
                    System.out.println("val:'" + req.username + "' '" + req.password + "'");
                    System.out.println("invalid password or username!");
                }
            }
        });

        requestBuilder.addRequest(req);
        requestBuilder.execute();

    }
}
