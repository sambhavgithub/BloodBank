package com.example.sam.bloodbank;

import android.widget.Spinner;

/**
 * Created by sam on 14/3/18.
 */

public class UserInfoBloodBank {
    String Name;
    String Type;
    String ContactPerson;
    String ContactNumber;
    String Email;
    String Address;
    String State;
    String Locality;

    public UserInfoBloodBank(String name, String type, String contactPerson, String contactNumber, String email, String address, String state, String locality) {
        Name = name;
        Type = type;
        ContactPerson = contactPerson;
        ContactNumber = contactNumber;
        Email = email;
        Address = address;
        State = state;
        Locality = locality;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }
}
