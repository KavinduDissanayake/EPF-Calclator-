package com.example.epfetfbalancecheckersbewl.Screen.Model;

public class Profile_model {

    public Profile_model(){}


    private String currentUID;
    private String emo_id;
    private String firstname;
    private String lastname;
    private String nic_id;
    private String phone_number;
    private String basic_salary;

    public Profile_model(String currentUID, String emo_id, String firstname, String lastname, String nic_id, String phone_number) {
        this.currentUID = currentUID;
        this.emo_id = emo_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nic_id = nic_id;
        this.phone_number = phone_number;
    }

    public Profile_model(String currentUID, String emo_id, String firstname, String lastname, String nic_id, String phone_number, String basic_salary) {
        this.currentUID = currentUID;
        this.emo_id = emo_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nic_id = nic_id;
        this.phone_number = phone_number;
        this.basic_salary = basic_salary;
    }

    public String getCurrentUID() {
        return currentUID;
    }

    public String getEmo_id() {
        return emo_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getNic_id() {
        return nic_id;
    }

    public String getPhone_number() {
        return phone_number;
    }


    public String getBasic_salary() {
        return basic_salary;
    }
}
