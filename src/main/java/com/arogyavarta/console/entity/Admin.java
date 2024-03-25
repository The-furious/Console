package com.arogyavarta.console.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Admin")
@Getter
@Setter
@AllArgsConstructor
@Builder
@PrimaryKeyJoinColumn(name = "admin_id")
public class Admin extends User {
    private Date joinDate;
    public Admin() {
        this.setUserType(UserType.ADMIN);
    }

}