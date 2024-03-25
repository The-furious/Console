package com.arogyavarta.console.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Radiologist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PrimaryKeyJoinColumn(name = "radiologist_id")
public class Radiologist extends User{
    private String specialization;
    private String licenseNumber;
    @Override
    public UserType getUserType() {
        return UserType.RADIOLOGIST;
    }
}