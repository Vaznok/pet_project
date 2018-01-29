package com.epam.rd.november2017.vlasenko.entity;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String email;
    private String nickName;
    private String password;
    private Role role;
    private boolean isBlocked;
    private String firstName;
    private String lastName;
    private String contact;

    public enum Role {
        REGISTERED_USER,
        LIBRARIAN,
        ADMINISTRATOR
    }


    public User(String email, String nickName, String password, Role role, boolean isBlocked, String firstName, String lastName, String contact) {
        this(email, nickName, password, role);
        this.isBlocked = isBlocked;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
    }

    public User(String email, String nickName, String password, Role role) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "nickName='" + nickName + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean isBlocked() {
        return isBlocked;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
