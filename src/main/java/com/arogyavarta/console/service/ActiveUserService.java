package com.arogyavarta.console.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.dto.ActiveUserDTO;
import com.arogyavarta.console.entity.ActiveUser;
import com.arogyavarta.console.entity.User;
import com.arogyavarta.console.repo.ActiveUserRepository;
import com.arogyavarta.console.repo.UserRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUserService {

    @Autowired
    private ActiveUserRepository activeUserRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveUser(ActiveUserDTO activeUserDTO) {
        ActiveUser activeUser = activeUserRepository.findByUserUserId(activeUserDTO.getUserId());
        if (activeUser==null) {
            User user = userRepository.findById(activeUserDTO.getUserId()).orElseThrow();
            activeUserRepository.save(ActiveUser.builder().user(user).build());
        }
    }

    public void disconnect(ActiveUserDTO activeUserDTO) {
        ActiveUser activeUserToDelete = activeUserRepository.findByUserUserId(activeUserDTO.getUserId());
        activeUserRepository.delete(activeUserToDelete);
    }

    public List<Long> findConnectedUsers() {
        List<ActiveUser> activeUsers = activeUserRepository.findAll();
        return activeUsers.stream()
                         .map(activeUser -> activeUser.getUser().getUserId())
                         .collect(Collectors.toList());
    }
}
