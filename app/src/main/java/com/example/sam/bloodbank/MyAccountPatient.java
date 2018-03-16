package com.example.sam.bloodbank;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MyAccountPatient extends AppCompatActivity {

    EditText etname, etemail, etweight, etdob, etphone, etcity, etaddress, etcity2, hospitalname, hospitaladdress, alternatephone, requireddate;
    Spinner bloodgroup, gender;
    Button btnupdate;
    TextView tvcancel;
    String genderselected, groupselected;
    String name, email, dob, weight, hosname, hosaddress, city2, phone, city, address, reqdate, alternate;
    Calendar myCalendar;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String UserID;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_patient);

        etname = findViewById(R.id.etname);
        etemail = findViewById(R.id.etemail);
        etweight = findViewById(R.id.etweight);
        etdob = findViewById(R.id.etdob);
        etphone = findViewById(R.id.etnumber);
        etcity = findViewById(R.id.etcity);
        etaddress = findViewById(R.id.etaddress);
        btnupdate = findViewById(R.id.btnupdate);
        bloodgroup= findViewById(R.id.bloodgroup);
        gender = findViewById(R.id.gender);
        tvcancel = findViewById(R.id.tvcancel);
        hospitalname = findViewById(R.id.HospitalName);
        hospitaladdress = findViewById(R.id.HospitalAddress);
        etcity2 = findViewById(R.id.etcity2);
        alternatephone = findViewById(R.id.alternatephone);
        requireddate = findViewById(R.id.requireddate);

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("Users").child(UserID).child("name").getValue().toString();
                email = dataSnapshot.child("Users").child(UserID).child("email").getValue().toString();
                city = dataSnapshot.child("Users").child(UserID).child("city").getValue().toString();
                weight = dataSnapshot.child("Users").child(UserID).child("weight").getValue().toString();
                dob = dataSnapshot.child("Users").child(UserID).child("dob").getValue().toString();
                phone = dataSnapshot.child("Users").child(UserID).child("phone").getValue().toString();
                address = dataSnapshot.child("Users").child(UserID).child("address").getValue().toString();
                groupselected = dataSnapshot.child("Users").child(UserID).child("bloodgroup").getValue().toString();
                genderselected = dataSnapshot.child("Users").child(UserID).child("gender").getValue().toString();
                hosname = dataSnapshot.child("Users").child(UserID).child("hosname").getValue().toString();
                hosaddress = dataSnapshot.child("Users").child(UserID).child("hosaddress").getValue().toString();
                city2 = dataSnapshot.child("Users").child(UserID).child("city2").getValue().toString();
                reqdate = dataSnapshot.child("Users").child(UserID).child("reqdate").getValue().toString();
                alternate = dataSnapshot.child("Users").child(UserID).child("alternatephone").getValue().toString();

                ArrayAdapter myAdap = (ArrayAdapter) gender.getAdapter();
                int genderPosition = myAdap.getPosition(genderselected);
                gender.setSelection(genderPosition);

                ArrayAdapter myAdap2 = (ArrayAdapter) bloodgroup.getAdapter();
                int groupPosition = myAdap2.getPosition(groupselected);
                bloodgroup.setSelection(groupPosition);

                etemail.setText(email);
                etname.setText(name);
                etcity.setText(city);
                etweight.setText(weight);
                etdob.setText(dob);
                etphone.setText(phone);
                etaddress.setText(address);
                hospitaladdress.setText(hosaddress);
                hospitalname.setText(hosname);
                etcity2.setText(city2);
                requireddate.setText(reqdate);
                alternatephone.setText(alternate);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etname.getText().toString();
                email = etemail.getText().toString();
                weight = etweight.getText().toString();
                dob = etdob.getText().toString();
                phone = etphone.getText().toString();
                city = etcity.getText().toString();
                address = etaddress.getText().toString();
                hosname = hospitalname.getText().toString();
                hosaddress = hospitaladdress.getText().toString();
                city2 = etcity2.getText().toString();
                reqdate = requireddate.getText().toString();

                final UserInfoPatient userInfoPatient = new UserInfoPatient(name, email, dob, weight, groupselected, genderselected, hosname, hosaddress, city2, phone, alternate, city, reqdate, address);

                if (name.equals("")){
                    etname.setError("Empty field");
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
                if (phone.equals("")){
                    etphone.setError("Empty field");
                    return;
                }
                if(phone.length() != 10){
                    etphone.setError("Invalid Phone number");
                    return;
                }
                if (alternate.equals("")){
                    alternatephone.setError("Empty field");
                    return;
                }
                if(alternate.length() != 10){
                    alternatephone.setError("Invalid Phone number");
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
                user = mAuth.getCurrentUser();
                UserID = user.getUid();
                databaseReference.child("Users").child(UserID).setValue(userInfoPatient);
                Toast.makeText(MyAccountPatient.this, "Update Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MyAccountPatient.this, seekerPage.class));
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
                new DatePickerDialog(MyAccountPatient.this, date, myCalendar
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
