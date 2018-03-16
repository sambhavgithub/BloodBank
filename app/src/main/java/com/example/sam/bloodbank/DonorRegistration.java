package com.example.sam.bloodbank;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DonorRegistration extends AppCompatActivity {

    EditText etname, etpassword, etemail, etconfirmpassword, etweight, etdob, etphone, etcity, etaddress;
    Spinner bloodgroup, gender;
    Button  btnsignup;
    TextView tvlogin;
    Calendar myCalendar;
    String genderselected, groupselected;
    String name, email, password, confirmpass, dob, weight, phone, city, address;
    CheckBox terms, eligibility;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String UserID;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_registration);

        etname = findViewById(R.id.etname);
        etpassword = findViewById(R.id.etpassword);
        etconfirmpassword = findViewById(R.id.etconfirmpassword);
        etemail = findViewById(R.id.etemail);
        etweight = findViewById(R.id.etweight);
        etdob = findViewById(R.id.etdob);
        etphone = findViewById(R.id.etnumber);
        etcity = findViewById(R.id.etcity);
        etaddress = findViewById(R.id.etaddress);
        btnsignup = findViewById(R.id.btnsignup);
        bloodgroup= findViewById(R.id.bloodgroup);
        gender = findViewById(R.id.gender);
        tvlogin = findViewById(R.id.tvalreadymember);
        terms = findViewById(R.id.terms);
        eligibility = findViewById(R.id.eligibility);


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genderselected = gender.getItemAtPosition(i).toString();
                Log.d("interest", genderselected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bloodgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                groupselected = bloodgroup.getItemAtPosition(i).toString();
                Log.d("interest", groupselected);
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
                weight = etweight.getText().toString();
                dob = etdob.getText().toString();
                 phone = etphone.getText().toString();
                 city = etcity.getText().toString();
                 address = etaddress.getText().toString();
                 password = etpassword.getText().toString();
                 confirmpass = etconfirmpassword.getText().toString();

                final UserInfoDonor userInfoDonor = new UserInfoDonor(name, email, groupselected, genderselected, dob, weight, phone, city, address);

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
                if (!password.equals(confirmpass)) {
                    etconfirmpassword.setError("Password does not match");
                    return;
                }
                if (weight.equals("")){
                    etweight.setError("Empty field");
                    return;
                }
                if (dob.equals("")){
                    etdob.setError("Empty field");
                    return;
                }
                if (genderselected.equals("Gender")){
                    Snackbar.make(view, "Enter your gender", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (groupselected.equals("Blood Group")){
                    Snackbar.make(view, "Enter your Blood Group", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (phone.equals("")){
                    etphone.setError("Empty field");
                    return;
                }
                if(phone.length() != 10){
                    etphone.setError("Invalid Phone number");
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
                if (!eligibility.isChecked()){
                    Snackbar.make(view, "Please check if you are eligible", Snackbar.LENGTH_SHORT).show();
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
                                    Toast.makeText(DonorRegistration.this, "Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(DonorRegistration.this, LoginActivityDonor.class));
                                }
                                else {
                                    Toast.makeText(DonorRegistration.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                                user = mAuth.getCurrentUser();
                                UserID = user.getUid();
                                databaseReference.child("Users").child(UserID).setValue(userInfoDonor);
                                user.sendEmailVerification();
                                mAuth.signOut();

                            }
                        });
                Snackbar.make(view, "A verification message has been send to your email ID.", Snackbar.LENGTH_LONG).show();
            }
        });

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DonorRegistration.this, LoginActivityDonor.class));
            }
        });

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etdob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(DonorRegistration.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etdob.setText(sdf.format(myCalendar.getTime()));
    }
}
