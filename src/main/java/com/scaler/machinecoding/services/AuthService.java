package com.scaler.machinecoding.services;

import com.scaler.machinecoding.exceptions.UserAlreadyExistedException;

public interface AuthService {

    boolean signUp(String email, String password) throws UserAlreadyExistedException;
    String login(String email, String password) throws UserAlreadyExistedException;
    boolean validateJwtToken(String token);
}
