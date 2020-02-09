package com.example.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;

public class validate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);

        SetupValAccount();  //wire button
       // CognitoUserPool userpool = CreateAccount.returnUserPool();

    }

    private void SetupValAccount() {
        Button button = findViewById(R.id.validateAcc);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

            }
        });

    }
}
