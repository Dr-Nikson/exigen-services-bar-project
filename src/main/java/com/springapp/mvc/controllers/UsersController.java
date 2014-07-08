package com.springapp.mvc.controllers;

import com.springapp.mvc.exceptions.AuthorizationException;
import com.springapp.mvc.exceptions.OrderException;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        //ResponseService responseService = new ResponseServiceImpl();
        //Long id = new Long(1);
        //return userRepository.findAll();
        return responseService.successResponse(userService.getUsers());
    }


    @RequestMapping(value = "/api/users/authorize", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONResponse authorizationUserJson(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
        User loginedUser =null;
        User authorizedUser =null;
        responseService = new ResponseServiceImpl();
        authorizationService = new AuthorizationServiceImpl();

        try
        {
            loginedUser = userService.loginUser(user.getEmail(), user.getPassword());
            authorizedUser = authorizationService.authorizeUser(loginedUser, request.getSession(), response);
        }catch(UserException ex)
        {
            responseService.errorResponse("authtorization.failed","error");
        }
        catch(AuthorizationException ex)
        {
            responseService.errorResponse("authtorization.failed","error");
        }

        return responseService.successResponse(authorizedUser);
    }

    @RequestMapping(value = "/api/orders/get", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONResponse getOrdersJson(HttpServletRequest request) throws OrderException {
        HttpSession session = request.getSession();
        authorizationService = new AuthorizationServiceImpl();
        responseService = new ResponseServiceImpl();
        orderService = new OrderServiceImpl();

        try
        {
            authorizationService.checkAccess(UserRoles.USER, session);
        }
        catch(AuthorizationException ex)
        {
            return responseService.errorResponse("authorization.access_denied", new String());
        }
        return responseService.successResponse(orderService.getOrders());

    }
}
