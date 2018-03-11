package com.example.sam.bloodbank;

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

public class BloodBankRegistration extends AppCompatActivity {

    EditText etname, etpassword, locality, etemail, etconfirmpassword, etcity, etaddress, contactnumber, contactperson;
    Spinner type, state;
    Button btnsignup;
    TextView tvlogin;
    CheckBox terms;
    String typeselected, stateselected;
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
                String name = etname.getText().toString();
                String email = etemail.getText().toString();
                String contperson = contactperson.getText().toString();
                String contnumber = contactnumber.getText().toString();
                String local = locality.getText().toString();
                String city = etcity.getText().toString();
                String address = etaddress.getText().toString();
                String password = etpassword.getText().toString();
                String confirmpass = etconfirmpassword.getText().toString();

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
            }
        });
    }
}
