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

public class SeekerRegistration extends AppCompatActivity {

    EditText etname, etpassword, etemail, etconfirmpassword, etweight, etdob, etphone, etcity, etcity2, etaddress, hospitalname, hospitaladdress, alternatephone, requireddate;
    Spinner bloodgroup, gender;
    Button btnsignup;
    TextView tvlogin;
    Calendar calendardob, calendardate;
    String genderselected, groupselected;
    String name, password, confirmpass, email, dob, weight, hosname, hosaddress, city2, phone, alternate, city, reqdate, address;
    CheckBox terms;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_registration);

        etname = findViewById(R.id.etname);
        etpassword = findViewById(R.id.etpassword);
        etconfirmpassword = findViewById(R.id.etconfirmpassword);
        etemail = findViewById(R.id.etemail);
        etweight = findViewById(R.id.etweight);
        etdob = findViewById(R.id.etdob);
        etphone = findViewById(R.id.etnumber);
        etcity = findViewById(R.id.etcity);
        etcity2 = findViewById(R.id.etcity2);
        etaddress = findViewById(R.id.etaddress);
        btnsignup = findViewById(R.id.btnsignup);
        bloodgroup= findViewById(R.id.bloodgroup);
        gender = findViewById(R.id.gender);
        tvlogin = findViewById(R.id.tvalreadymember);
        alternatephone = findViewById(R.id.alternatephone);
        hospitalname = findViewById(R.id.HospitalName);
        hospitaladdress = findViewById(R.id.HospitalAddress);
        requireddate = findViewById(R.id.requireddate);
        terms = findViewById(R.id.terms);

        final UserInfoPatient userInfoPatient = new UserInfoPatient(name, email, dob, weight, groupselected, genderselected, hosname, hosaddress, city2, phone, alternate, city, reqdate, address);

        mAuth = FirebaseAuth.getInstance();

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
                 city2 = etcity2.getText().toString();
                 address = etaddress.getText().toString();
                 password = etpassword.getText().toString();
                 confirmpass = etconfirmpassword.getText().toString();
                 alternate = alternatephone.getText().toString();
                 hosname = hospitalname.getText().toString();
                 hosaddress = hospitaladdress.getText().toString();
                 reqdate = requireddate.getText().toString();


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
                if(phone.length() < 10){
                    etphone.setError("Invalid Phone number");
                    return;
                }
                if (alternate.equals("")){
                    alternatephone.setError("Empty field");
                    return;
                }
                if (alternate.length() < 10){
                    alternatephone.setError("Invalid Phone Number");
                    return;
                }
                if (city.equals("")){
                    etcity.setError("Empty field");
                    return;
                }
                if (city2.equals("")){
                    etcity2.setError("Empty field");
                    return;
                }
                if (address.equals("")) {
                    etaddress.setError("Empty field");
                    return;
                }
                if (hosaddress.equals("")){
                    hospitaladdress.setError("Empty field");
                    return;
                }
                if (hosname.equals("")){
                    hospitalname.setError("Empty field");
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
                                    Toast.makeText(SeekerRegistration.this, "Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SeekerRegistration.this, LoginActivityPatient.class));
                                }
                                else {
                                    Toast.makeText(SeekerRegistration.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                mAuth = FirebaseAuth.getInstance();
                databaseReference = FirebaseDatabase.getInstance().getReference();
                user = mAuth.getCurrentUser();
                UserID = user.getUid();
                databaseReference.child("Users").child("Patient").child(UserID).setValue(userInfoPatient);
                user.sendEmailVerification();
                mAuth.signOut();
                Snackbar.make(view, "A verification message has been send to your email ID. Check you Email and click verify to continue.", Snackbar.LENGTH_LONG).show();
            }
        });
        calendardob = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendardob.set(Calendar.YEAR, year);
                calendardob.set(Calendar.MONTH, monthOfYear);
                calendardob.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabeldob();
            }

        };

        etdob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(SeekerRegistration.this, date, calendardob
                        .get(Calendar.YEAR), calendardob.get(Calendar.MONTH),
                        calendardob.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        calendardate = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendardate.set(Calendar.YEAR, year);
                calendardate.set(Calendar.MONTH, monthOfYear);
                calendardate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabeldate();
            }

        };

        requireddate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(SeekerRegistration.this, date1, calendardate
                        .get(Calendar.YEAR), calendardate.get(Calendar.MONTH),
                        calendardate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabeldob() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etdob.setText(sdf.format(calendardob.getTime()));
    }
    private void updateLabeldate() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        requireddate.setText(sdf.format(calendardate.getTime()));
    }
}
