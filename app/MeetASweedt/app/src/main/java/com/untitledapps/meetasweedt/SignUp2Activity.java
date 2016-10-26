package com.untitledapps.meetasweedt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.NetworkShared.RequestCreateUser;
import com.example.NetworkShared.RequestGetPerson;
import com.example.NetworkShared.ResponseGetPerson;
import com.untitledapps.Client.RequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;


public class SignUp2Activity extends AppCompatActivity {

    public MultiSpinner multisp;
    public Button buttonSIGNUPNOW;

    Context context;

    RequestCreateUser requestCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        context = this;

        Intent intent = getIntent();
        requestCreateUser =(RequestCreateUser)intent.getSerializableExtra("req");
        
        //MULTISPINNER
        multisp = (MultiSpinner) findViewById(R.id.spInterests);
        final String[] interests = new String[]{"Fotboll", "Hockey", "Handboll", "Kampsport", "Träning", "Laga Mat", "Fika", "Språk", "Spel", "Musik", "Film", "Konst", "Resor", "Natur", "Politik"};
        final int interestsize = interests.length;

        ArrayAdapter<String> interestAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, interests){
            @Override
            public int getCount(){
                return(interestsize);
            }
        };

        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //multisp.setAdapter(interestAdapter); //adapters are not supported for multi spinner, (it throws)
        multisp.setSelection(interestsize);
        multisp.setItem(interests);

        ///BUTTON
        Button buttonSINGUPNOW = (Button) findViewById(R.id.buttonSINGUPNOW);

        buttonSINGUPNOW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (multisp.getSelectedStrings().size() != 0) {
                    //this is why we have public variables or getters and setters lol
                    final RequestCreateUser req =
                            new RequestCreateUser(requestCreateUser.isLearner(),
                                    requestCreateUser.getAge(),
                                    requestCreateUser.getName(),
                                    requestCreateUser.getOrginCountry(),
                                    requestCreateUser.getLongitude(),
                                    requestCreateUser.getLatitude(),
                                    multisp.getSelectedStrings(),
                                    requestCreateUser.getUsername(),
                                    requestCreateUser.getPassword());

                    RequestBuilder builder = new RequestBuilder(SignUp2Activity.this, new RequestBuilder.Action() {
                        @Override
                        public void PostExecute() {


                            if (req.was_successfull()) {




                                final RequestGetPerson requestGetPerson = new RequestGetPerson(requestCreateUser.getUsername());

                                RequestBuilder requestBuilderGetPerson = new RequestBuilder(context, new RequestBuilder.Action() {
                                    @Override
                                    public void PostExecute() {
                                        if (requestGetPerson.was_successfull()) {

                                            ResponseGetPerson responseGetPerson = requestGetPerson.getResponse();

                                            System.out.println("setting person in sign in");

                                            Person person = new Person(
                                                    responseGetPerson.getIsLearner(),
                                                    responseGetPerson.getAge(),
                                                    responseGetPerson.getName(),
                                                    responseGetPerson.getOriginCountry(),
                                                    responseGetPerson.getLongitude(),
                                                    responseGetPerson.getLatitude(),
                                                    multisp.getSelectedStrings(),
                                                    responseGetPerson.getUsername(),
                                                    responseGetPerson.getId()
                                            );


                                            if (person.getUsername() != null) {
                                                ((MeetASweedt) getApplicationContext()).setLoggedInPerson(person);
                                            } else {
                                                //handle
                                            }

                                            if (((MeetASweedt) getApplicationContext()).getLoggedInPerson() != null) {
                                                Intent intent = new Intent(SignUp2Activity.this, ProfileActivity.class);
                                                startActivity(intent);
                                            } else {

                                            }

                                        } else {
                                            System.out.println("get person from database not successful");
                                        }
                                    }
                                });

                                requestBuilderGetPerson.addRequest(requestGetPerson);
                                requestBuilderGetPerson.execute();

                                //goToSignInActivity();

                            } else {
                                System.out.println("stuff:" +

                                        "\n" + requestCreateUser.getAge() +
                                        "\n" + requestCreateUser.getName() +
                                        "\n" + requestCreateUser.getOrginCountry() +
                                        "\n" + requestCreateUser.getLongitude() +
                                        "\n" + requestCreateUser.getLatitude() +
                                        "\n" + multisp.getSelectedStrings() +
                                        "\n" + requestCreateUser.getUsername() +
                                        "\n" + requestCreateUser.getPassword());


                                //should never happend
                                System.out.println("req failed");
                                Toast.makeText(SignUp2Activity.this, "skapandet misslyckades, prova ett annat användarnamn", Toast.LENGTH_LONG).show();

                                gotoSignup1();
                            }
                        }
                    });

                    builder.addRequest(req);
                    builder.execute();
                } else {
                    Toast.makeText(SignUp2Activity.this, "välj minst ett intresse för att fortsätta", Toast.LENGTH_LONG).show();
                }
            }
        });




    }

    private void goToSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    private void gotoSignup1() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
