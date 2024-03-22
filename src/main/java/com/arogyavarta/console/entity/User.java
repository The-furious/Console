package com.arogyavarta.console.entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Data
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(unique = true)
    private String userName;

    @JsonIgnore
    private String password; // Encoded password

    private String userFirstName;
    private String userLastName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "role_name",
    joinColumns = {
            @JoinColumn(name = "USER_ID")
    },
            inverseJoinColumns ={
            @JoinColumn(name="ROLE_ID")
    })
    private Set<Role> role;


}
