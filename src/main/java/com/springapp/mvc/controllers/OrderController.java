package com.springapp.mvc.controllers;

import com.springapp.mvc.exceptions.AuthorizationException;
import com.springapp.mvc.exceptions.OrderException;
import com.springapp.mvc.json_protocol.JSONResponse;
import com.springapp.mvc.model.Order;
import com.springapp.mvc.model.User;
import com.springapp.mvc.service.AuthorizationService;
import com.springapp.mvc.service.OrderService;
import com.springapp.mvc.service.ResponseService;
import com.springapp.mvc.service.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vlad on 08.07.2014.
 */
@Controller
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
    @RequestMapping(value = "/api/orders/get", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONResponse getOrdersJson(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        //authorizationService = new AuthorizationServiceImpl();
        //responseService = new ResponseServiceImpl();
        //orderService = new OrderServiceImpl();
        List<Order> orders = new ArrayList<Order>();

        try
        {
            authorizationService.checkAccess(UserRoles.USER, session);
            orders = orderService.getOrders();
        }
        catch(AuthorizationException ex)
        {
            return responseService.errorResponse("authorization.access_denied", new String());
        }

        return responseService.successResponse(orders);

    }



    @RequestMapping(value = "/api/orders/add", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONResponse addOrdersJson(Date startTime, int personsNum, boolean allRestaurant, String note, boolean ownAlcohol, User user, HttpServletResponse response)
    {
        //authorizationService = new AuthorizationServiceImpl();
        //responseService = new ResponseServiceImpl();
        //orderService = new OrderServiceImpl();

        Order order = new Order();
        order.setStartTime(startTime);
        order.setPersonsNum(personsNum);
        order.setAllRestaurant(allRestaurant);
        order.setNote(note);
        order.setOwnAlcohol(ownAlcohol);
        order.setUser(user);
        try {
            orderService.addOrder(order);
        } catch (OrderException e) {
            return responseService.errorResponse("order.not_enough_space", new String(orderService.getFreeSpace(startTime) + ""));
        }
        return responseService.successResponse(order);


    }

}
