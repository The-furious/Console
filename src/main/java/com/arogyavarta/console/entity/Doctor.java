package com.arogyavarta.console.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Doctor")
@Getter
@Setter
@AllArgsConstructor
@Builder
@PrimaryKeyJoinColumn(name = "doctor_id")
public class Doctor extends User{
    private String specialty;
    private String registrationNumber;
    public Doctor() {
        this.setUserType(UserType.DOCTOR);
    }
}