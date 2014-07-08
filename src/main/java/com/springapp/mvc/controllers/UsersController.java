package com.springapp.mvc.controllers;

import com.springapp.mvc.exceptions.UserException;
import com.springapp.mvc.json_protocol.JSONResponse;
import com.springapp.mvc.model.User;
import com.springapp.mvc.repository.UserRepository;
import com.springapp.mvc.service.ResponseService;
import com.springapp.mvc.service.ResponseServiceImpl;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Vlad on 08.07.2014.
 */
public class UsersController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/users/get", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONResponse getUsersList()
    {
        ResponseService responseService = new ResponseServiceImpl();
        //Long id = new Long(1);
        //return userRepository.findAll();
        return responseService.successResponse(userRepository.findAll());
    }


    @RequestMapping(value = "/api/users/authorize", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONResponse authorizationUserJson(@RequestBody User user){
        User loginedUser =null;
        ResponseService responseService = new ResponseServiceImpl();
        try
        {
            loginedUser = userService.loginUser(user.getEmail(), user.getPassword());
        }catch(UserException ex)
        {
            responseService.errorResponse("authtorization.failed","error");
        }
        return responseService.successResponse(loginedUser);
    }
}
