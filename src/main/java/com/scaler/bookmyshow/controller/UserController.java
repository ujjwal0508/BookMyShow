package com.scaler.bookmyshow.controller;

import com.scaler.bookmyshow.dtos.ResponseStatus;
import com.scaler.bookmyshow.dtos.SignUpRequestDTO;
import com.scaler.bookmyshow.dtos.SignUpResponseDTO;
import com.scaler.bookmyshow.models.User;
import com.scaler.bookmyshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public SignUpResponseDTO signUp(SignUpRequestDTO request){
        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO();
        User user;

        try{
            user = userService.signUp(request.getEmail(), request.getPassword());
            signUpResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            signUpResponseDTO.setUserId(user.getId());
        } catch (Exception ex){
            signUpResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }

        return signUpResponseDTO;
    }

    // break - 08:13 am
}
