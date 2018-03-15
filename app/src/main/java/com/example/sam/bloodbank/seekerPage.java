package com.example.sam.bloodbank;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class seekerPage extends AppCompatActivity {

    EditText etdate, etunit;
    Button requestblood;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_page);

        etdate = findViewById(R.id.date);
        etunit = findViewById(R.id.units);
        requestblood = findViewById(R.id.requestblood);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();
        UserID = user.getUid();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hello Patient");

        requestblood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String date = etdate.getText().toString();
                final String unit = etunit.getText().toString();

                if(unit.equals("")){
                    etunit.setError("Enter the amount of blood");
                    return;
                }
                if(date.equals("")){
                    etdate.setError("Enter date");
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(seekerPage.this);
                builder.setTitle("Are you sure?");
                builder.setMessage("NOTE: After clicking the button, your request for blood will be sent to the nearest Blood Bank.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child("Users").child("Patient").child(UserID).setValue(date);
                        databaseReference.child("Users").child("Patient").child(UserID).setValue(unit);
                        Log.d("builder", "positive");
                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.seekermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.myaccount:
                startActivity(new Intent(seekerPage.this, MyAccountPatient.class));
                Log.d("item", "myaccount");
                return true;
            case R.id.logout:
                Log.d("item", "logout");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
