package com.arogyavarta.console.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Lab")
@Getter
@Setter
@AllArgsConstructor
@Builder
@PrimaryKeyJoinColumn(name = "lab_id")
public class Lab extends User {
    private String location;
    private String accreditationNumber;
    public Lab() {
        this.setUserType(UserType.LAB);
    }
}