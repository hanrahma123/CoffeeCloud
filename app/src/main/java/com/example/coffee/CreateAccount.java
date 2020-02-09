        package com.example.coffee;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

        import com.amazonaws.ClientConfiguration;
        import com.amazonaws.amplify.generated.graphql.CreateTodoMutation;
        import com.amazonaws.auth.AnonymousAWSCredentials;
        import com.amazonaws.mobile.client.AWSMobileClient;
        import com.amazonaws.mobile.client.UserStateDetails;
        import com.amazonaws.mobile.client.UserStateListener;
        import com.amazonaws.mobile.config.AWSConfiguration;
        import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
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
        import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
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

public class CreateAccount extends AppCompatActivity {

    private  AWSAppSyncClient mAWSAppSyncClient;
    private  String poolId = "eu-west-1_Sywtfz3aj";
    private String clientId = "4jhmpul0tm8f9j9ik1eolv5f6n";
    private String clientSecret = "1u0ttt41as3ql2ibn0l4269d7mdqn2eq1a100sk3d2b60g9kfq6n";
    private CognitoUserPool userPool;
    private CognitoUser user;
    private String userId, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

//        AWSConfiguration awsConfig = new AWSConfiguration(getApplicationContext());
//        CognitoUserPool cognitoUserPool = new CognitoUserPool(getApplicationContext(), awsConfig);
//        BasicCognitoUserPoolsAuthProvider basicCognitoUserPoolsAuthProvider = new BasicCognitoUserPoolsAuthProvider(cognitoUserPool);
//        mAWSAppSyncClient = AWSAppSyncClient.builder()
//                .context(getApplicationContext())
//                .region(Regions.EU_WEST_1)
//                .cognitoUserPoolsAuthProvider(basicCognitoUserPoolsAuthProvider)
//                .awsConfiguration(awsConfig)
//                .build();

        SetupBack();
        Ok();
    }

    private void SetupBack(){
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void Ok(){
        Button back = findViewById(R.id.createAccount);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                CreateAccountRequest();



            }
        });
    }
    private void CreateAccountRequest() {
         userId = ((EditText) findViewById(R.id.username_create)).getText().toString();
         password = ((EditText) findViewById(R.id.password_create)).getText().toString();
        String email = ((EditText) findViewById(R.id.email_create)).getText().toString();
        Log.e(poolId, password);
        ClientConfiguration clientConfiguration = new ClientConfiguration();


        AmazonCognitoIdentityProviderClient identityProviderClient = new AmazonCognitoIdentityProviderClient(new AnonymousAWSCredentials(), clientConfiguration);
        identityProviderClient.setRegion(Region.getRegion(Regions.EU_WEST_1));

        // pool = new CognitoUserPool(context, USER_POOL_ID, APP_CLIENT_ID, APP_CLIENT_SECRET, identityProviderClient);

        userPool = new CognitoUserPool(getApplicationContext(), poolId, clientId, clientSecret, identityProviderClient);

        CognitoUserAttributes userAttributes = new CognitoUserAttributes();

        userAttributes.addAttribute("email", email);

        userPool.signUpInBackground(userId, password, userAttributes, null, signupCallback);
    }

        private SignUpHandler signupCallback = new SignUpHandler() {

            @Override
            public void onSuccess(CognitoUser cognitoUser, boolean userConfirmed, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                // Sign-up was successful
                user = cognitoUser;
                Log.e("SUCCESSSSS", "SUCCCCEEEEEESSSSS" );
                user.getSessionInBackground(authenticationHandler);
                // Check if this user (cognitoUser) has to be confirmed
                if(!userConfirmed) {

                    //startActivity(new Intent(CreateAccount.this, validate.class));

                    findViewById(R.id.table).setVisibility(View.GONE);
                    findViewById(R.id.code).setVisibility(View.VISIBLE);
                    findViewById(R.id.createAccount).setVisibility(View.INVISIBLE);
                    findViewById(R.id.ValidateButton).setVisibility(View.VISIBLE);
                    SetupValidateButton();
                    // This user has to be confirmed and a confirmation code was sent to the user
                    // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
                    // Get the confirmation code from user
                }
                else {
                    // The user has already been confirmed
                }
            }

            @Override
            public void onFailure(Exception exception) {
                // Sign-up failed, check exception for the cause
                String msg = exception.getMessage();

                Log.e("FAILURE", msg );
            }
        };

    private void CreateCodeButton(){

        Button submitCodeBut = findViewById(R.id.validateAcc);
        submitCodeBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               Log.e("hello", "hello");


            }
        });

    }

    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {

        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            Log.e("Success!!", "you signed in " );
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
           Log.e("Getting Auth", "getting auth details " );
//          // The API needs user sign-in credentials to continue
           AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, password, null);
//
//            // Pass the user sign-in credentials to the continuation
           authenticationContinuation.setAuthenticationDetails(authenticationDetails);
//
//            // Allow the sign-in to continue
//            authenticationContinuation.continueTask();
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

    // Call back handler for confirmSignUp API
    GenericHandler confirmationCallback = new GenericHandler() {

        @Override
        public void onSuccess() {
            // User was successfully confirmed
            startActivity(new Intent(CreateAccount.this, CreatedAcc.class));
        }

        @Override
        public void onFailure(Exception exception) {
            // User confirmation failed. Check exception for the cause.
            String msg = exception.getMessage();
            Log.e("msg", msg);
        }
    };

    private void SetupValidateButton(){
        Button submitCodeBut = findViewById(R.id.ValidateButton);
        submitCodeBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String confirmationCode = ((EditText) findViewById(R.id.validationCode)).getText().toString();
                boolean forcedAliasCreation = false;
                user.confirmSignUpInBackground(confirmationCode, forcedAliasCreation, confirmationCallback);
                Log.e("err",confirmationCode);

            }
        });

    }
    public CognitoUserPool returnUserPool(){
        return userPool;
    }
//    public void runMutation(){
//        CreateTodoInput createTodoInput = CreateTodoInput.builder().
//                name("Use AppSync").
//                description("Realtime and Offline").
//                build();
//
//        mAWSAppSyncClient.mutate(CreateTodoMutation.builder().input(createTodoInput).build())
//                .enqueue(mutationCallback);
//    }
//
//    private GraphQLCall.Callback<CreateTodoMutation.Data> mutationCallback = new GraphQLCall.Callback<CreateTodoMutation.Data>() {
//        @Override
//        public void onResponse(@Nonnull Response<CreateTodoMutation.Data> response) {
//            Log.i("Results", "Added Todo");
//        }
//
//        @Override
//        public void onFailure(@Nonnull ApolloException e) {
//            Log.e("Error", e.toString());
//        }
//    };

}



