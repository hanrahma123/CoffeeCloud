package com.example.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



         //signin
        SetupSignin();
        SetupCreateAccount();


//      Button back = findViewById(R.id.back);
////        back.setOnClickListener(new View.OnClickListener() {
////
////            public void onClick(View v) {
////                //setContentView(R.layout.activity_main);
////            }
////        });
    }
    private void SetupSignin() {
        Button button = findViewById(R.id.signin);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignIn_Activity.class));
            }
        });
        }

    private void SetupCreateAccount() {
        Button button = findViewById(R.id.createAccount);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateAccount.class));
            }
        });
    }
    }


