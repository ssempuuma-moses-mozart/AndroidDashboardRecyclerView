package com.example.ulcerpatientapp.ModelClass;

public class UserType {

    private  String Uid;
    private String UserName;
    private String PhoneNumber;
    private  String Email;
    private  String Password;
    private  int Usertype;


    public UserType(){

    }

    public UserType(String uid, String userName, String phoneNumber, String email, String password, int usertype) {
        Uid = uid;
        UserName = userName;
        PhoneNumber = phoneNumber;
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

    public int getUsertype() {
        return Usertype;
    }

    public void setUsertype(int usertype) {
        Usertype = usertype;
    }
}
