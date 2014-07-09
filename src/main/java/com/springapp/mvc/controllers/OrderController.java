package com.springapp.mvc.controllers;

import com.springapp.mvc.exceptions.AuthorizationException;
import com.springapp.mvc.json_protocol.JSONResponse;
import com.springapp.mvc.service.AuthorizationService;
import com.springapp.mvc.service.Impl.AuthorizationServiceImpl;
import com.springapp.mvc.service.Impl.OrderServiceImpl;
import com.springapp.mvc.service.Impl.ResponseServiceImpl;
import com.springapp.mvc.service.OrderService;
import com.springapp.mvc.service.ResponseService;
import com.springapp.mvc.service.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Vlad on 08.07.2014.
 */
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private AuthorizationService authorizationService;

    /*@RequestMapping(value = "/api/orders/get/", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONResponse showOrdersJson(@RequestBody Order order) throws OrderException {
        List<Order> orderList = null;
        ResponseService responseService = new ResponseServiceImpl();
        try
        {
            orderList = orderService.getOrders();
        }catch(OrderException ex)
        {
            responseService.errorResponse("authtorization.access_denied","error");
        }
        return responseService.successResponse(orderService.getOrders());
    }*/
    @RequestMapping(value = "/api/orders/get", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONResponse getOrdersJson(HttpServletRequest request)
    {
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
