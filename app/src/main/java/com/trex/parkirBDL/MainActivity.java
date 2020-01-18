package com.trex.parkirBDL;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.trex.parkirbandarlampung.R;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;


    static final int GOOGLE_SIGN = 1001;
    private static final String TAG = "test";

    private FirebaseAuth mAuth;

    private EditText email_Masuk, kataSandi_Masuk;
    private Button masuk_btn ;
    private ImageView back_btn;


    private ImageView loginButton, btnSignIn_google;
    CallbackManager callbackManager;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //facebook login--------------------------------------------------------------
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        //----------------------------------------------------------------------------

        mAuth = FirebaseAuth.getInstance();


        //Google Sign In------------------------------------------------
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        //--------------------------------------------------------------

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        email_Masuk = (EditText) findViewById(R.id.email_masuk_txt);
        kataSandi_Masuk = (EditText) findViewById(R.id.kataSandi_masuk_txt);
        masuk_btn = (Button) findViewById(R.id.btn_masuk);
        back_btn = findViewById(R.id.back);
        btnSignIn_google = findViewById(R.id.btn_login_google);



        btnSignIn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                signIn();

            }
        });

        masuk_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String $email = email_Masuk.getText().toString();
                String $kataSandi = kataSandi_Masuk.getText().toString();

                validasiMasukForm($email, $kataSandi);

            }
        });

//        masuk_mitra_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intentBack = new Intent(MainActivity.this, MainActivityMitra.class);
//                startActivity(intentBack);
//            }
//        });


        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setEnabled(false);
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "error : Login Di batalkan" , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(MainActivity.this, "error : " + error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser != null && firebaseUser.isEmailVerified()){
            updateUI(firebaseUser);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                e.printStackTrace();
                // Google Sign In failed, update UI appropriately

            }
        }


    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN);

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Berhasil Masuk", Toast.LENGTH_LONG).show();

                            btnSignIn_google.setEnabled(true);
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            updateUI(currentUser);


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });



    }

    private void updateUI(FirebaseUser user){

       if (user != null){

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }


    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information


                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            loginButton.setEnabled(true);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                            updateUI(null);

                            loginButton.setEnabled(true);
                        }

                        // ...
                    }
                });
    }






    public void MasukUser(String email, String kataSandi){
        mAuth.signInWithEmailAndPassword(email, kataSandi)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            if (mAuth.getCurrentUser().isEmailVerified())
                            {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                                startActivity(intent);

                            }else {
                                Toast.makeText(MainActivity.this, "Akun Belum Terverifikasi", Toast.LENGTH_SHORT).show();

                            }

                        }else {

                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                });

    }

    public void validasiMasukForm(String email, String kataSandi)
    {
        if (email.isEmpty())
        {
            email_Masuk.setError("Email Harus Diisi");
            email_Masuk.requestFocus();

        }else if(kataSandi.isEmpty())
        {
            kataSandi_Masuk.setError("Password Harus Diisi");
            kataSandi_Masuk.requestFocus();
        }
        else if(!ValidasiEmail(email)){

            email_Masuk.setError("Format Email Salah");
            email_Masuk.requestFocus();
        }

        else
        {
            MasukUser(email, kataSandi);

        }

    }

    public static boolean ValidasiEmail(String email){
        boolean validasi;
        String emailPatern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

        if (email.matches(emailPatern) || email.matches(emailPattern2) && email.length() > 0)
        {
            validasi = true;
        }else{
            validasi = false;
        }

        return validasi;
    }

    public void Daftar(View view) {
        Intent intenDaftar = new Intent(MainActivity.this, DaftarActivity.class);
        startActivity(intenDaftar);
    }

    public void bantuan_masuk(View view) {

        Intent intenBantuan = new Intent(MainActivity.this, LupaKataSandi.class);
        startActivity(intenBantuan);


    }



}
