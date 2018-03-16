package com.example.sam.bloodbank;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MyAccountDonor extends AppCompatActivity {

    EditText etname, etemail, etweight, etdob, etphone, etcity, etaddress;
    Spinner bloodgroup, gender;
    Button btnupdate;
    TextView tvcancel;
    String genderselected, groupselected;
    String name, email, dob, weight, phone, city, address;
    Calendar myCalendar;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String UserID;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_donor);

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

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        user = mAuth.getCurrentUser();
        if (user != null) {
            UserID = user.getUid();
            Log.d("ifelse", "user is "+user);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("name", "datasnapshot is" + dataSnapshot);
                    name = dataSnapshot.child("Users").child("Donor").child(UserID).child("name").getValue().toString();
                    Log.d("name", "name is " + name);
                    email = dataSnapshot.child("Users").child(UserID).child("email").getValue().toString();
                    city = dataSnapshot.child("Users").child(UserID).child("city").getValue().toString();
                    weight = dataSnapshot.child("Users").child(UserID).child("weight").getValue().toString();
                    dob = dataSnapshot.child("Users").child(UserID).child("dob").getValue().toString();
                    phone = dataSnapshot.child("Users").child(UserID).child("phone").getValue().toString();
                    address = dataSnapshot.child("Users").child(UserID).child("address").getValue().toString();
                    groupselected = dataSnapshot.child("Users").child(UserID).child("bloodgroup").getValue().toString();
                    genderselected = dataSnapshot.child("Users").child(UserID).child("gender").getValue().toString();

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
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
            Log.d("ifelse", "else");

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

                final UserInfoDonor userInfoDonor = new UserInfoDonor(name, email, groupselected, genderselected, dob, weight, phone, city, address);

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
                databaseReference.child("Users").child("Donor").child(UserID).setValue(userInfoDonor);
                Toast.makeText(MyAccountDonor.this, "Update Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MyAccountDonor.this, DonorPage.class));
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
                new DatePickerDialog(MyAccountDonor.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAccountDonor.this, DonorPage.class));
            }
        });
    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etdob.setText(sdf.format(myCalendar.getTime()));
    }
}
