package com.example.sam.bloodbank;

/**
 * Created by sam on 14/3/18.
 */

public class UserInfoPatient {
    String name, email, dob, weight, bloodgroup, gender, hosname, hosaddess, city2, phone, alternatephone, city, reqdate, address;

    public UserInfoPatient(String name, String email, String dob, String weight, String bloodgroup, String gender, String hosname, String hosaddess, String city2, String phone, String alternatephone, String city, String reqdate, String address) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.weight = weight;
        this.bloodgroup = bloodgroup;
        this.gender = gender;
        this.hosname = hosname;
        this.hosaddess = hosaddess;
        this.city2 = city2;
        this.phone = phone;
        this.alternatephone = alternatephone;
        this.city = city;
        this.reqdate = reqdate;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHosname() {
        return hosname;
    }

    public void setHosname(String hosname) {
        this.hosname = hosname;
    }

    public String getHosaddess() {
        return hosaddess;
    }

    public void setHosaddess(String hosaddess) {
        this.hosaddess = hosaddess;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlternatephone() {
        return alternatephone;
    }

    public void setAlternatephone(String alternatephone) {
        this.alternatephone = alternatephone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getReqdate() {
        return reqdate;
    }

    public void setReqdate(String reqdate) {
        this.reqdate = reqdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
