package com.epam.rd.november2017.vlasenko.entity;

public class User {
    private String email;
    private String nickName;
    private UserRole role;
    private String firstName;
    private String lastName;
    private String contact;

    public User(String email, String nickName, UserRole role) {
        this.email = email;
        this.nickName = nickName;
        this.role = role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public UserRole getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContact() {
        return contact;
    }
}
