package com.frestau.restaurants.model;

public class User {
    private String userid;
    private String useremail;
    private String userfirstname;
    private String userlastname;
    private String userphone;
    private String userpassword;
    private String userlatitude;
    private String userlongitude;
    private String userlogintime;
    private String userphotoid;
    private String userresnumber;
    private String userresnumber_a;
    private String userresnumber_b;
    private String userresnumber_c;

    public User(String userid, String useremail, String userfirstname, String userlastname, String userphone, String userpassword,String userlatitude,String userlongitude,String userlogintime,String userphotoid,String userresnumber,String userresnumber_a,String userresnumber_b,String userresnumber_c) {
        this.userid = userid;
        this.useremail = useremail;
        this.userfirstname = userfirstname;
        this.userlastname = userlastname;
        this.userphone = userphone;
        this.userpassword = userpassword;
        this.userlatitude = userlatitude;
        this.userlongitude = userlongitude;
        this.userlogintime = userlogintime;
        this.userphotoid = userphotoid;
        this.userresnumber = userresnumber;
        this.userresnumber_a = userresnumber_a;
        this.userresnumber_b = userresnumber_b;
        this.userresnumber_c = userresnumber_c;
    }




    public User() {

    }
    public String getuserid() {
        return userid;
    }

    public void setuserid(String userid) {
        this.userid = userid;
    }

    public String getuseremail() {
        return useremail;
    }

    public void setuseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getuserfirstname() {
        return userfirstname;
    }

    public void setuserfirstname(String userfirstname) {
        this.userfirstname = userfirstname;
    }

    public String getuserlastname() {
        return userlastname;
    }

    public void setuserlastname(String userlastname) {
        this.userlastname = userlastname;
    }

    public String getuserphone() {
        return userphone;
    }

    public void setuserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getuserpassword() {
        return userpassword;
    }

    public void setuserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getuserlatitude() {
        return userlatitude;
    }

    public void setuserlatitude(String userlatitude) {
        this.userlatitude = userlatitude;
    }

    public String getuserlongitude() {
        return userlongitude;
    }

    public void setuserlongitude(String userlongitude) {
        this.userlongitude = userlongitude;
    }

    public String getuserlogintime() {
        return userlogintime;
    }

    public void setuserlogintime(String userlogintime) {
        this.userlogintime = userlogintime;
    }

    public String getuserphotoid() {
        return userphotoid;
    }

    public void setuserphotoid(String userphotoid) {
        this.userphotoid = userphotoid;
    }

    public String getuserresnumber() {
        return userresnumber;
    }

    public void setuserresnumber(String userresnumber) {
        this.userresnumber = userresnumber;
    }

    public String getuserresnumber_a() {
        return userresnumber_a;
    }

    public void setuserresnumber_a(String userresnumber_a) {
        this.userresnumber_a = userresnumber_a;
    }

    public String getuserresnumber_b() {
        return userresnumber_b;
    }

    public void setuserresnumber_b(String userresnumber_b) {
        this.userresnumber_b = userresnumber_b;
    }

    public String getuserresnumber_c() {
        return userresnumber_c;
    }

    public void setuserresnumber_c(String userresnumber_c) {
        this.userresnumber_c = userresnumber_c;
    }




}
