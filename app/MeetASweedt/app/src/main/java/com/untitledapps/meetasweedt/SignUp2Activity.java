package com.untitledapps.meetasweedt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.NetworkShared.RequestCreateUser;
import com.untitledapps.Client.RequestBuilder;


public class SignUp2Activity extends AppCompatActivity {

    public static MultiSpinner multisp;
    public Button buttonSIGNUPNOW;


    RequestCreateUser requestCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        
        //MULTISPINNER
        multisp = (MultiSpinner) findViewById(R.id.spInterests);
        final String[] interests = new String[]{"Fotboll", "Äta Mat", "Fika", "Speaka", "Lära mig svenska"};
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
                        if(req.was_successfull())
                        {
                            goToSignInActivity();
                        }
                        else
                        {
                            gotoSignup1();
                        }
                    }
                });

                builder.addRequest(req);
                builder.execute();
            }
        });

        Intent intent = getIntent();
        requestCreateUser =(RequestCreateUser)intent.getSerializableExtra("req");


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
