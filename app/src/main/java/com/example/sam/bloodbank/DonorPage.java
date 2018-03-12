package com.example.sam.bloodbank;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class DonorPage extends AppCompatActivity {

    Toolbar toolbar;
    EditText etdate;
    Button submit;
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
        submit = findViewById(R.id.requestblood);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = etdate.getText().toString();
                if(date.equals("")){
                    etdate.setError("Enter date");
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(DonorPage.this);
                builder.setTitle("Are you sure?");
                builder.setMessage("NOTE: After clicking the button, your request for donation will be sent to the nearest Blood Bank and the nearest blood seekers.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
            case R.id.history:
                Log.d("item", "history");
                return true;
            case R.id.myaccount:
                Log.d("item", "myaccount");
                return true;
            case R.id.logout:
                Log.d("item", "logout");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
