package com.example.sam.bloodbank;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAccountBloodBank extends AppCompatActivity {

    EditText etname, locality, etemail, etcity, etaddress, contactnumber, contactperson;
    Spinner type, state;
    Button btnupdate;
    TextView tvcancel;
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
        etemail = findViewById(R.id.email);
        etcity = findViewById(R.id.city);
        locality = findViewById(R.id.locality);
        contactperson = findViewById(R.id.contactperson);
        contactnumber = findViewById(R.id.contactnumber);
        etaddress = findViewById(R.id.address);
        btnupdate = findViewById(R.id.btnupdate);
        tvcancel = findViewById(R.id.tvcancel);
        type = findViewById(R.id.type);
        state = findViewById(R.id.state);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();
        UserID = user.getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("Users").child(UserID).child("name").getValue().toString();
                email = dataSnapshot.child("Users").child(UserID).child("email").getValue().toString();
                city = dataSnapshot.child("Users").child(UserID).child("city").getValue().toString();
                local = dataSnapshot.child("Users").child(UserID).child("locality").getValue().toString();
                contperson = dataSnapshot.child("Users").child(UserID).child("contactPerson").getValue().toString();
                contnumber = dataSnapshot.child("Users").child(UserID).child("contactNumber").getValue().toString();
                address = dataSnapshot.child("Users").child(UserID).child("address").getValue().toString();
                typeselected = dataSnapshot.child("Users").child(UserID).child("type").getValue().toString();
                stateselected = dataSnapshot.child("Users").child(UserID).child("state").getValue().toString();

                ArrayAdapter myAdap = (ArrayAdapter) type.getAdapter();
                int typePosition = myAdap.getPosition(typeselected);
                type.setSelection(typePosition);

                ArrayAdapter myAdap2 = (ArrayAdapter) state.getAdapter();
                int statePosition = myAdap2.getPosition(stateselected);
                state.setSelection(statePosition);

                etemail.setText(email);
                etname.setText(name);
                etcity.setText(city);
                locality.setText(local);
                contactperson.setText(contperson);
                contactnumber.setText(contnumber);
                etaddress.setText(address);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final UserInfoBloodBank userInfoBloodBank = new UserInfoBloodBank(name, typeselected, contperson, contnumber, email, address, stateselected, local);

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



        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etname.getText().toString();
                email = etemail.getText().toString();
                contperson = contactperson.getText().toString();
                contnumber = contactnumber.getText().toString();
                local = locality.getText().toString();
                city = etcity.getText().toString();
                address = etaddress.getText().toString();

                if (name.equals("")){
                    etname.setError("Empty field");
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
                user = mAuth.getCurrentUser();
                UserID = user.getUid();
                databaseReference.child("Users").child(UserID).setValue(userInfoBloodBank);
                Snackbar.make(view, "blood bank page will open", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
