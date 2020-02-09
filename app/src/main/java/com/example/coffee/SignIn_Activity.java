package com.example.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentityprovider.AmazonCognitoIdentityProviderClient;

public class SignIn_Activity extends AppCompatActivity {

    private  String poolId = "eu-west-1_Sywtfz3aj";
    private String clientId = "4jhmpul0tm8f9j9ik1eolv5f6n";
    private String clientSecret = "1u0ttt41as3ql2ibn0l4269d7mdqn2eq1a100sk3d2b60g9kfq6n";
    private CognitoUserPool userPool;
    private CognitoUser user;
    private String userId123, password123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_);

        CreateBack();
        CreateSigninBtn();
    }


    private void CreateBack() {
        Button back = findViewById(R.id.back1);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });
    }

    private void CreateSigninBtn() {
        Button back = findViewById(R.id.signinAttempt);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CreateAccountRequest();

            }
        });
    }

    private void CreateAccountRequest() {
        userId123 = ((EditText) findViewById(R.id.signin_user)).getText().toString();
        password123 = ((EditText) findViewById(R.id.signin_pass)).getText().toString();
        //String email = ((EditText) findViewById(R.id.email_create)).getText().toString();
       // Log.e(poolId, password);
        ClientConfiguration clientConfiguration = new ClientConfiguration();


        AmazonCognitoIdentityProviderClient identityProviderClient = new AmazonCognitoIdentityProviderClient(new AnonymousAWSCredentials(), clientConfiguration);
        identityProviderClient.setRegion(Region.getRegion(Regions.EU_WEST_1));

        // pool = new CognitoUserPool(context, USER_POOL_ID, APP_CLIENT_ID, APP_CLIENT_SECRET, identityProviderClient);

        userPool = new CognitoUserPool(getApplicationContext(), poolId, clientId, clientSecret, identityProviderClient);

        CognitoUserAttributes userAttributes = new CognitoUserAttributes();

        //userAttributes.addAttribute("email", "hanrahma@tcd.ie");
        //userPool.signUpInBackground(userId123, password123, userAttributes, null, signupCallback);

        CognitoUser thisUser = userPool.getUser(userId123);
        thisUser.getSessionInBackground(authenticationHandler);


    }


    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {

        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            Log.e("Success!!", "you signed in " );
            startActivity(new Intent(SignIn_Activity.this, Home.class));
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
           Log.e("Getting Auth", "getting auth details " );
//            // The API needs user sign-in credentials to continue
            AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, password123, null);
//
//            // Pass the user sign-in credentials to the continuation
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);
//
//            // Allow the sign-in to continue
            authenticationContinuation.continueTask();
        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {
            // Multi-factor authentication is required, get the verification code from user
            //multiFactorAuthenticationContinuation.setMfaCode(mfaVerificationCode);
            // Allow the sign-in process to continue
            // multiFactorAuthenticationContinuation.continueTask();
        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {

        }

        @Override
        public void onFailure(Exception exception) {
//            Log.e("Failure", "failure " );
//            String msg  =exception.toString();
//            Log.e("exception", msg );
//            // Sign-in failed, check exception for the cause
        }
    };


    // Callback handler for the sign-in process


// Sign-in the user


}
