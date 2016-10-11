package com.untitledapps.meetasweedt;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class SignUp2Activity extends AppCompatActivity {

    public static MultiSpinner multisp;
    public Button buttonSIGNUPNOW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        
        //MULTISPINNER
        multisp = (MultiSpinner) findViewById(R.id.spInterests);
        final String[] interests = new String[]{"Interest 1", "Interest 2", "Interest 3"};
        final int interestsize = interests.length;

        ArrayAdapter<String> interestAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, interests){
            @Override
            public int getCount(){
                return(interestsize);
            }
        };

        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        multisp.setAdapter(interestAdapter);
        multisp.setSelection(interestsize);

        ///BUTTON
        Button buttonSINGUPNOW = (Button) findViewById(R.id.buttonSINGUPNOW);

        buttonSINGUPNOW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfileActivity();
                String s = multisp.getSelectedItemStr();
            }
        });

    }



    private void goToProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }


}
