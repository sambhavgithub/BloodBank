package com.example.sam.bloodbank;

import android.app.DatePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SeekerRegistration extends AppCompatActivity {

    EditText etname, etpassword, etemail, etconfirmpassword, etweight, etdob, etphone, etcity, etaddress, hospitalname, hospitaladdress, alternatephone, requireddate;
    Spinner bloodgroup, gender;
    Button btnsignup;
    TextView tvlogin;
    Calendar calendardob, calendardate;
    String genderselected, groupselected;
    CheckBox terms;
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
                String name = etname.getText().toString();
                String email = etemail.getText().toString();
                String weight = etweight.getText().toString();
                String dob = etdob.getText().toString();
                String phone = etphone.getText().toString();
                String city = etcity.getText().toString();
                String address = etaddress.getText().toString();
                String password = etpassword.getText().toString();
                String confirmpass = etconfirmpassword.getText().toString();
                String alternate = alternatephone.getText().toString();
                String hosname = hospitalname.getText().toString();
                String hosaddress = hospitaladdress.getText().toString();
                String reqdate = requireddate.getText().toString();


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
                if (address.equals("")) {
                    etaddress.setError("Empty field");
                    return;
                }
                if (reqdate.equals("")){
                    requireddate.setError("Empty field");
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
