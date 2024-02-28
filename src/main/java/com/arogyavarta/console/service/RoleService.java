package com.arogyavarta.console.service;

import com.arogyavarta.console.entity.Role;
import com.arogyavarta.console.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    public Role createNewRole(Role role){
        return roleRepo.save(role);
    }

    public List<Role> getALLRoles(){
        return roleRepo.findAll();
    }
}
