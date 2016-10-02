package com.untitledapps.meetasweedt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.NetworkShared.RequestCreateUser;
import com.example.NetworkShared.RequestVerifyPassword;
import com.untitledapps.Client.RequestBuilder;

import java.util.concurrent.ExecutionException;

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
                goToProfileActivity();
            }
        });}

    private void goToProfileActivity() {


        RequestBuilder requestBuilder = new RequestBuilder();
        RequestVerifyPassword req = new RequestVerifyPassword(etUsername.getText().toString(),etPassword.getText().toString());

        requestBuilder.addRequest(req);
        try
        {
            requestBuilder.execute().get();
            if(req.was_successfull())
            {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            }
            else
            {
                System.out.println("got:'"+etUsername.getText().toString() +"' '"+ etPassword.getText().toString()+"'");
                System.out.println("invalid password or username!");
            }
        }
        catch (InterruptedException|ExecutionException ex)
        {
            ex.printStackTrace();
        }
    }

}
