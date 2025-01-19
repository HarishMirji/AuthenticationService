package com.scaler.machinecoding.controllers;

import com.scaler.machinecoding.dtos.*;
import com.scaler.machinecoding.exceptions.UserAlreadyExistedException;
import com.scaler.machinecoding.exceptions.UserNotFoundException;
import com.scaler.machinecoding.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign_up")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) throws UserAlreadyExistedException {
        SignUpResponseDto response = new SignUpResponseDto();

        try{
            if(authService.signUp(requestDto.getEmail(), requestDto.getPassword())) {
                response.setRequestStatus(RequestStatus.SUCCESS);
            }else {
                response.setRequestStatus(RequestStatus.FAILURE);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setRequestStatus(RequestStatus.FAILURE);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) throws UserAlreadyExistedException {
        try {
            String token = authService.login(requestDto.getEmail(), requestDto.getPassword());
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setRequestStatus(RequestStatus.SUCCESS);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("auth_token", token);

            return new ResponseEntity<>(loginResponseDto, headers, HttpStatus.OK);
        } catch (Exception e) {
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setRequestStatus(RequestStatus.FAILURE);

            return new ResponseEntity<>(loginResponseDto, null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/validate")
    public boolean validateToken(@RequestParam("token") String token) {
        return authService.validateJwtToken(token);
    }
}
