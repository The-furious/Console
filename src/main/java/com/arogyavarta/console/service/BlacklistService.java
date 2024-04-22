package com.arogyavarta.console.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arogyavarta.console.entity.BlacklistToken;
import com.arogyavarta.console.repo.BlacklistTokenRepository;

@Service
public class BlacklistService {
    @Autowired
    private BlacklistTokenRepository blacklistTokenRepository;

    public void addToBlacklist(String token) {
        BlacklistToken blacklistToken = new BlacklistToken();
        blacklistToken.setToken(token);
        blacklistToken.setBlacklistedAt(LocalDateTime.now());
        blacklistTokenRepository.save(blacklistToken);
    }

    public boolean isBlacklisted(String token) {
        BlacklistToken blacklistToken = blacklistTokenRepository.findByToken(token);
        return blacklistToken != null;
    }
}