package com.example.sam.bloodbank;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    TextView createaccount, forgotpassword;
    EditText etemail, etpassword;
    Button btnlogin;
    CallbackManager callbackManager;
    LoginButton loginButton;
    private static final String EMAIL = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createaccount = findViewById(R.id.createaccount);
        forgotpassword = findViewById(R.id.forgotpassword);
        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        btnlogin = findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etemail.getText().toString();
                String password = etpassword.getText().toString();
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
                Log.d("login", "loginsuccess");
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("login", "forgot success");
            }
        });
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Choose Registration");
                builder.setMessage("Choose one of the option which suits you the best");
                builder.setPositiveButton("Donor", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(LoginActivity.this, DonorRegistration.class));
                    }
                });
                builder.setNegativeButton("Receptor/Patient", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(LoginActivity.this, SeekerRegistration.class));
                    }
                });
                builder.setNeutralButton("Blood Bank", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(LoginActivity.this, BloodBankRegistration.class));
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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
    }
}
