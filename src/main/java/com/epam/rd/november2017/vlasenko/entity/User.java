package com.epam.rd.november2017.vlasenko.entity;

public class User {
    private String email;
    private String nickName;
    private String password;
    private String role;
    private boolean isBlocked;
    private String firstName;
    private String lastName;
    private String contact;

    public User(String email, String nickName, String password, UserRole role, boolean isBlocked, String firstName, String lastName, String contact) {
        this(email, nickName, password, role);
        this.isBlocked = isBlocked;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
    }

    public User(String email, String nickName, String password, UserRole role) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.role = role.name();
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

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
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
}
