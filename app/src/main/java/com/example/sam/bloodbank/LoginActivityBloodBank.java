package com.example.sam.bloodbank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class LoginActivityBloodBank extends AppCompatActivity {

    TextView createaccount, forgotpassword, signasreceptor, signasdonor;
    EditText etemail, etpassword;
    Button btnlogin;
    CallbackManager callbackManager;
    LoginButton loginButton;
    private FirebaseAuth mAuth;
    String email, password;
    private static final String EMAIL = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_blood_bank);

        createaccount = findViewById(R.id.createaccount);
        forgotpassword = findViewById(R.id.forgotpassword);
        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        btnlogin = findViewById(R.id.btnlogin);
        signasreceptor = findViewById(R.id.SignasReceptor);
        signasdonor = findViewById(R.id.SignasDonor);

        mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etemail.getText().toString();
                password = etpassword.getText().toString();
                if (email.equals("")){
                    etemail.setError("Field Empty");
                    return;
                }
                if (!email.contains("@")){
                    etemail.setError("Invalid Email");
                    return;
                }
                if (!email.contains(".com")){
                    etemail.setError("Invalid Email");
                    return;
                }
                if (password.equals("")){
                    etpassword.setError("Field Empty");
                    return;
                }
                if (password.length() < 6){
                    etpassword.setError("Minimum length must be 6");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Log.d("temp", "success");
                                    Toast.makeText(LoginActivityBloodBank.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(LoginActivityBloodBank.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etemail.getText().toString();

                if (email.equals("")){
                    etemail.setError("Field Empty");
                    return;
                }
                if (!email.contains("@")){
                    etemail.setError("Invalid Email");
                    return;
                }
                if (!email.contains(".com")) {
                    etemail.setError("Invalid Email");
                    return;
                }
                mAuth.sendPasswordResetEmail(email);
                Snackbar.make(v, "A password reset email has been sent to your email account", Snackbar.LENGTH_LONG).show();
                Log.d("login", "forgot success");
            }
        });
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityBloodBank.this, BloodBankRegistration.class));
            }
        });

        callbackManager = CallbackManager.Factory.create();

        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("facebook", "onsuccess "+loginResult);
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("facebook", "oncancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("facebook", "onerror "+exception);
            }
        });
        signasdonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityBloodBank.this, LoginActivityDonor.class));
            }
        });
        signasreceptor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityBloodBank.this, LoginActivityPatient.class));
            }
        });
    }
}
