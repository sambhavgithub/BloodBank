package com.example.sam.bloodbank;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonorPage extends AppCompatActivity {

    Toolbar toolbar;
    EditText etdate, etlastdate;
    Button submit;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    String UserID;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.donormenu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_page);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hello Donor");

        etdate = findViewById(R.id.date);
        etlastdate = findViewById(R.id.lastdate);
        submit = findViewById(R.id.requestblood);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();
        UserID = user.getUid();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String date = etdate.getText().toString();
                int lastdate = Integer.parseInt(etlastdate.getText().toString());
                if(date.equals("")){
                    etdate.setError("Enter date");
                    return;
                }
                if (lastdate < 120){
                    Toast.makeText(DonorPage.this, "You are not eligible to donate", Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(DonorPage.this);
                builder.setTitle("Are you sure?");
                builder.setMessage("NOTE: After clicking the button, your request for donation will be sent to the nearest Blood Bank and the nearest blood seekers.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etdate.setText("");
                        etlastdate.setText("");
                        databaseReference.child("Users").child(UserID).child("donatedate").setValue(date);
                        Toast.makeText(DonorPage.this, "Request has been sent", Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.myaccount:
                startActivity(new Intent(DonorPage.this, MyAccountDonor.class));
                Log.d("item", "myaccount");
                return true;
            case R.id.logout:
                mAuth.signOut();
                Toast.makeText(DonorPage.this, "Sign out successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DonorPage.this, LoginActivityDonor.class));
                Log.d("item", "logout");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
