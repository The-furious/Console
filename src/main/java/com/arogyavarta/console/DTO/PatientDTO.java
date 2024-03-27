package com.arogyavarta.console.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class PatientDTO {
    private String name;
    private String email;
    private String profilePhotoUrl;
    private String contactNumber;
    private String address;
    private Date dateOfBirth;
    private double weight;
    private double height;
    private String bloodGroup;
    private String emergencyContact;
    private String state;
    private String country;
    private int pincode;
    private String username;
    private String password;
}

