package com.example.sam.bloodbank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BloodBankRegistration extends AppCompatActivity {

    EditText etname, etpassword, locality, etemail, etconfirmpassword, etcity, etaddress, contactnumber, contactperson;
    Spinner type, state;
    Button btnsignup;
    TextView tvlogin;
    CheckBox terms;
    String typeselected, stateselected;
    String email, name, contperson, contnumber, address, city, local, password, confirmpass;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank_registration);

        etname = findViewById(R.id.name);
        etpassword = findViewById(R.id.password);
        etconfirmpassword = findViewById(R.id.confirmpassword);
        etemail = findViewById(R.id.email);
        etcity = findViewById(R.id.city);
        locality = findViewById(R.id.locality);
        contactperson = findViewById(R.id.contactperson);
        contactnumber = findViewById(R.id.contactnumber);
        etaddress = findViewById(R.id.address);
        btnsignup = findViewById(R.id.btnsignup);
        tvlogin = findViewById(R.id.tvalreadymember);
        terms = findViewById(R.id.terms);
        type = findViewById(R.id.type);
        state = findViewById(R.id.state);

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeselected = type.getItemAtPosition(i).toString();
                Log.d("interest", typeselected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stateselected = state.getItemAtPosition(i).toString();
                Log.d("interest", stateselected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etname.getText().toString();
                email = etemail.getText().toString();
                contperson = contactperson.getText().toString();
                contnumber = contactnumber.getText().toString();
                local = locality.getText().toString();
                city = etcity.getText().toString();
                address = etaddress.getText().toString();
                password = etpassword.getText().toString();
                confirmpass = etconfirmpassword.getText().toString();

                final UserInfoBloodBank userInfoBloodBank = new UserInfoBloodBank(name, typeselected, contperson, contnumber, email, address, stateselected, local);

                if (name.equals("")){
                    etname.setError("Empty field");
                    return;
                }
                if (email.equals("")){
                    etemail.setError("Empty field");
                    return;
                }
                if (password.equals("")){
                    etpassword.setError("Empty field");
                    return;
                }
                if (password.length() < 6){
                    etpassword.setError("Minimum password length should be 6");
                    return;
                }
                if (confirmpass.equals("")){
                    etpassword.setError("Empty field");
                    return;
                }
                if (!password.equals(confirmpass)) {
                    etconfirmpassword.setError("Password does not match");
                    return;
                }
                if (typeselected.equals("Type")){
                    Snackbar.make(view, "Enter your Blood Bank Type", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (stateselected.equals("State")){
                    Snackbar.make(view, "Enter your State", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (contperson.equals("")){
                    contactperson.setError("Empty field");
                    return;
                }
                if (contnumber.equals("")){
                    contactnumber.setError("Empty field");
                    return;
                }
                if(contnumber.length() != 10){
                    contactnumber.setError("Invalid Phone number");
                    return;
                }
                if (city.equals("")){
                    etcity.setError("Empty field");
                    return;
                }
                if (address.equals("")) {
                    etaddress.setError("Empty field");
                    return;
                }
                if (local.equals("")) {
                    locality.setError("Empty field");
                    return;
                }
                if (!terms.isChecked()){
                    Snackbar.make(view, "Please accept Terms and Conditions", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(BloodBankRegistration.this, "Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(BloodBankRegistration.this, LoginActivityBloodBank.class));
                                }
                                else {
                                    Toast.makeText(BloodBankRegistration.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                                user = mAuth.getCurrentUser();
                                UserID = user.getUid();
                                user.sendEmailVerification();
                                databaseReference.child("Users").child(UserID).setValue(userInfoBloodBank);
                                mAuth.signOut();
                            }
                        });
                Snackbar.make(view, "A verification message has been send to your email ID. Check you Email and click verify to continue.", Snackbar.LENGTH_LONG).show();
            }
        });
        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BloodBankRegistration.this, LoginActivityBloodBank.class));
            }
        });
    }
}
