package com.cryptodev.cryptopedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private static final String TAG = "tes";
    private static final int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    private SignInButton btnSignInWithGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMoveActivity =findViewById(R.id.button_login);
        btnMoveActivity.setOnClickListener(this);

        btnSignInWithGoogle = findViewById(R.id.sign_in_button);
        btnSignInWithGoogle.setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestProfile()
                .requestEmail()
                .build();
        Log.w(TAG, "onCreate");
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            Log.w(TAG, "onActivityResult");

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());


        }
    }

    private void updateUI(GoogleSignInAccount account) {
        Log.w(TAG, "update ui");

        if(account!=null){
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);

            intent.putExtra("username",account);

            startActivity(intent);
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "onStart");
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.button_login:
                EditText editTextUsername = (EditText) findViewById(R.id.UsernameLogin);
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                String username = String.valueOf(editTextUsername.getText());

                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }
    private void signIn() {
        Log.w(TAG, "onSignIn");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);

    }

}
