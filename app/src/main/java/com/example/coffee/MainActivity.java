package com.example.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.amplify.generated.graphql.CreateTodoMutation;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.UserStateListener;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentityprovider.AmazonCognitoIdentityProviderClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.amazonaws.mobileconnectors.appsync.sigv4.BasicCognitoUserPoolsAuthProvider;

import javax.annotation.Nonnull;

import type.CreateTodoInput;

import static android.provider.ContactsContract.CommonDataKinds.StructuredPostal.REGION;

public class MainActivity extends AppCompatActivity {

   private  AWSAppSyncClient mAWSAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AWSConfiguration awsConfig = new AWSConfiguration(getApplicationContext());
//        CognitoUserPool cognitoUserPool = new CognitoUserPool(getApplicationContext(), awsConfig);
//        BasicCognitoUserPoolsAuthProvider basicCognitoUserPoolsAuthProvider = new BasicCognitoUserPoolsAuthProvider(cognitoUserPool);
//        mAWSAppSyncClient = AWSAppSyncClient.builder()
//                .context(getApplicationContext())
//                .region(Regions.EU_WEST_1)
//                .cognitoUserPoolsAuthProvider(basicCognitoUserPoolsAuthProvider)
//                .awsConfiguration(awsConfig)
//                .build();
//        String poolId = "eu-west-1_Sywtfz3aj";
//        String clientId = "4jhmpul0tm8f9j9ik1eolv5f6n";
//        String clientSecret = "1u0ttt41as3ql2ibn0l4269d7mdqn2eq1a100sk3d2b60g9kfq6n";
//
//        ClientConfiguration clientConfiguration = new ClientConfiguration();
//
//        AmazonCognitoIdentityProviderClient identityProviderClient = new AmazonCognitoIdentityProviderClient(new AnonymousAWSCredentials(), clientConfiguration);
//        identityProviderClient.setRegion(Region.getRegion( Regions.EU_WEST_1 ));
//
//       // pool = new CognitoUserPool(context, USER_POOL_ID, APP_CLIENT_ID, APP_CLIENT_SECRET, identityProviderClient);
//
//        CognitoUserPool userPool = new CognitoUserPool(getApplicationContext(), poolId, clientId, clientSecret, identityProviderClient);
//
//        CognitoUserAttributes userAttributes = new CognitoUserAttributes();
//
//        userAttributes.addAttribute("email", "markhanrahan@live.com");
//
//        SignUpHandler signupCallback = new SignUpHandler() {
//
//            @Override
//            public void onSuccess(CognitoUser cognitoUser, boolean userConfirmed, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
//                // Sign-up was successful
//                Log.e("SUCCESSSSS", "SUCCCCEEEEEESSSSS" );
//                // Check if this user (cognitoUser) has to be confirmed
//                if(!userConfirmed) {
//
//                    // This user has to be confirmed and a confirmation code was sent to the user
//                    // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
//                    // Get the confirmation code from user
//                }
//                else {
//                    // The user has already been confirmed
//                }
//            }
//
//            @Override
//            public void onFailure(Exception exception) {
//                // Sign-up failed, check exception for the cause
//                String msg = exception.getMessage();
//
//                Log.e("FAILURE", msg );
//            }
//        };
//        String userId = "marky123";
//        String password = "Robot23456";
//        userPool.signUpInBackground(userId, password, userAttributes, null, signupCallback);
        //signin
        SetupSignin();
        SetupCreateAccount();
    }
//      Button back = findViewById(R.id.back);
////        back.setOnClickListener(new View.OnClickListener() {
////
////            public void onClick(View v) {
////                //setContentView(R.layout.activity_main);
////            }
////        });

    private void SetupSignin() {
        Button button = findViewById(R.id.signin);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //runMutation();
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
    public void runMutation(){
        CreateTodoInput createTodoInput = CreateTodoInput.builder().
                name("Use AppSync").
                description("Realtime and Offline").
                build();

        mAWSAppSyncClient.mutate(CreateTodoMutation.builder().input(createTodoInput).build())
                .enqueue(mutationCallback);
    }

    private GraphQLCall.Callback<CreateTodoMutation.Data> mutationCallback = new GraphQLCall.Callback<CreateTodoMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateTodoMutation.Data> response) {
            Log.i("Results", "Added Todo");
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }
    };

}


