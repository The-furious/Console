package com.arogyavarta.console.controller;

import com.arogyavarta.console.entity.Role;
import com.arogyavarta.console.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping("/createNewRole")
    public ResponseEntity<Role>  createNewRole(@RequestBody Role role){
        Role newRole=roleService.createNewRole(role);
        return new ResponseEntity<>(newRole, HttpStatus.OK);
    }
    @GetMapping("/getAllRoles")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(roleService.getALLRoles(),HttpStatus.OK);
    }
}
