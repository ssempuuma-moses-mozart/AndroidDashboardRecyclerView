package com.example.ulcerpatientapp.ModelClass;

public class DoctorModel {

    private  String Uid;
    private String FirstName;
    private String LastName;
    private String Specialization;
    private String UserName;
    private String PhoneNumber;

    private String TimeSlot;
    private  String Email;
    private  String Password;
    private  String Usertype;


    public DoctorModel(){

    }

    public DoctorModel(String uid, String firstName, String lastName, String specialization, String userName, String phoneNumber, String timeSlot, String email, String password, String usertype) {
        Uid = uid;
        FirstName = firstName;
        LastName = lastName;
        Specialization = specialization;
        UserName = userName;
        PhoneNumber = phoneNumber;
        TimeSlot = timeSlot;
        Email = email;
        Password = password;
        Usertype = usertype;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getTimeSlot() {
        return TimeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        TimeSlot = timeSlot;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUsertype() {
        return Usertype;
    }

    public void setUsertype(String usertype) {
        Usertype = usertype;
    }
}
