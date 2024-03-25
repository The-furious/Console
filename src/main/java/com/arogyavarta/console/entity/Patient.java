package com.arogyavarta.console.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PrimaryKeyJoinColumn(name = "patient_id")
public class Patient extends User {
    private Date dateOfBirth;
    private double weight;
    private double height;
    private String bloodGroup;
    private int emergencyContact;
    @Override
    public UserType getUserType() {
        return UserType.PATIENT;
    }
}