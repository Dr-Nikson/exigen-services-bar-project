package com.springapp.mvc.controllers;

import com.springapp.mvc.exceptions.AuthorizationException;
import com.springapp.mvc.exceptions.UserException;
import com.springapp.mvc.json_protocol.JSONResponse;
import com.springapp.mvc.model.User;
import com.springapp.mvc.service.*;
import com.springapp.mvc.service.Impl.AuthorizationServiceImpl;
import com.springapp.mvc.service.Impl.OrderServiceImpl;
import com.springapp.mvc.service.Impl.ResponseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Vlad on 08.07.2014.
 */
public class UsersController {

    @Autowired
    private UserService userService;

    private ResponseService responseService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private OrderService orderService;



    @RequestMapping(value = "/api/users/get", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONResponse getUsersList()
    {
        ResponseService responseService = new ResponseServiceImpl();
        try{
            authorizationService.checkAccess(UserRoles.ADMIN);
        }catch(AuthorizationException ex){
            responseService.errorResponse("authtorization.access_denied","error");
        }
        return responseService.successResponse(userService.getUsers());
    }


    @RequestMapping(value = "/api/users/authorize", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONResponse authorizationUserJson(@RequestBody User user){
        User loginedUser =null;
        responseService = new ResponseServiceImpl();
        try
        {
            loginedUser = userService.loginUser(user.getEmail(), user.getPassword());
        }catch(UserException ex)
        {
            responseService.errorResponse("authtorization.failed","error");
        }
        return responseService.successResponse(loginedUser);
    }

    @RequestMapping(value = "/api/orders/get", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONResponse getOrdersJson(HttpServletRequest request, HttpServletResponse response)
    {
        authorizationService = new AuthorizationServiceImpl(request.getSession(), response);
        responseService = new ResponseServiceImpl();
        orderService = new OrderServiceImpl();
        try
        {
            authorizationService.checkAccess(UserRoles.USER);
        }
        catch(AuthorizationException ex)
        {
            return responseService.errorResponse("autherization.access_denied", new String());
        }
        return responseService.successResponse(orderService.getOrders());

    }
}
