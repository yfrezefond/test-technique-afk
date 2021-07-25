package com.afk.testtechnique.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDTO {

    @NotBlank(message = "is mandatory")
    private String username;

    @NotBlank(message = "is mandatory")
    private String firstName;

    @NotBlank(message = "is mandatory")
    private String lastName;

    @NotBlank(message = "is mandatory")
    @Email(message = "should be a valid email")
    private String email;

    private String company;

    private String gender;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
