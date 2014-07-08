package com.springapp.mvc.controllers;

import com.springapp.mvc.json_protocol.JSONResponse;
import com.springapp.mvc.repository.UserRepository;
import com.springapp.mvc.service.ResponseService;
import com.springapp.mvc.service.ResponseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Vlad on 08.07.2014.
 */
public class UsersController {

    @Autowired
    private UserRepository userRepository;

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
}
