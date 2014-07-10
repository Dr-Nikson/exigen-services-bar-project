package com.springapp.mvc.controllers;

import com.springapp.mvc.exceptions.AuthorizationException;
import com.springapp.mvc.exceptions.DuplicateOrderException;
import com.springapp.mvc.exceptions.OrderException;
import com.springapp.mvc.exceptions.UserException;
import com.springapp.mvc.json_protocol.JSONResponse;
import com.springapp.mvc.model.Order;
import com.springapp.mvc.model.User;
import com.springapp.mvc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Vlad on 08.07.2014.
 */
@Controller
@Transactional
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/orders/get", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONResponse getOrdersJson(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        List<Order> orders;

        try
        {
            authorizationService.checkAccess(UserRoles.ADMIN, session);
            orders = orderService.getOrders();
        }
        catch(AuthorizationException ex)
        {
            return responseService.errorResponse("authorization.access_denied", "");
        }

        return responseService.successResponse(orders);
    }



    @RequestMapping(value = "/api/orders/add", method = RequestMethod.POST)
    public
    @ResponseBody
    JSONResponse addOrdersJson(@RequestBody Order order, HttpServletResponse response, HttpSession session)
    {
        User authorizedUser;

        try
        {
            // Проверяем залогинен-ли пользователь
            authorizedUser = authorizationService.checkAccess(authorizationService.getRolesListForAddOrder(), session);
            order.setUser(authorizedUser);
        }
        catch (AuthorizationException e)
        {
            // Значит пользователь - новый и надо его добавить
            try
            {
                authorizedUser = userService.registerUser(order.getUser());
                authorizedUser = authorizationService.authorizeUser(authorizedUser, session, response);
                order.setUser(authorizedUser);
            }
            catch (AuthorizationException e1)
            {
                // Непонятная ошибка, так быть не должно
                return responseService.errorResponse("order.very_bad_error", "shit happens :(((");
            }
            catch (UserException e1)
            {
                return responseService.errorResponse("user.user_exist", "");
            }
        }

        try
        {
            orderService.addOrder(order);
        }
        catch (OrderException e)
        {
            return responseService.errorResponse("order.not_enough_space", orderService.getFreeSpace(order.getStartTime()));
        }
        catch (DuplicateOrderException e)
        {
            return responseService.errorResponse("order.duplicate", "");
        }


        return responseService.successResponse(order);
    }

}
