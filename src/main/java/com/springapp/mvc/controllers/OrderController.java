package com.springapp.mvc.controllers;

import com.springapp.mvc.exceptions.OrderException;
import com.springapp.mvc.exceptions.UserException;
import com.springapp.mvc.json_protocol.JSONResponse;
import com.springapp.mvc.model.User;
import com.springapp.mvc.model.Order;
import com.springapp.mvc.service.OrderService;
import com.springapp.mvc.service.ResponseService;
import com.springapp.mvc.service.ResponseServiceImpl;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Vlad on 08.07.2014.
 */
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/api/orders/get/", method = RequestMethod.GET)
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
    }


}
