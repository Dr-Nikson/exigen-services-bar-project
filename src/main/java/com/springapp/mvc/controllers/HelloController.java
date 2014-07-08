package com.springapp.mvc.controllers;

import com.springapp.mvc.json_protocol.JSONResponse;
import com.springapp.mvc.model.Order;
import com.springapp.mvc.model.User;
import com.springapp.mvc.repository.OrderRepository;
import com.springapp.mvc.repository.UserRepository;
import com.springapp.mvc.service.ResponseService;
import com.springapp.mvc.service.Impl.ResponseServiceImpl;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HelloController
{
    @Autowired
    private UserRepository userRepository;

    //@Qualifier("orderRepository")
    @Autowired
    private OrderRepository orderRepository;


    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public
    @ResponseBody
    List<User> listUsersJson(ModelMap model)
    {
        //Long id = new Long(1);
        return userRepository.findAll();
    }

    @RequestMapping(value = "/api/test/single", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONResponse testResponseJson()
    {
        ResponseService responseService = new ResponseServiceImpl();
        //Long id = new Long(1);
        //return userRepository.findAll();
        return responseService.successResponse(userRepository.findOne((long) 1));
    }

    @RequestMapping(value = "/api/test/array", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONResponse testArrayResponseJson()
    {
        ResponseService responseService = new ResponseServiceImpl();
        //Long id = new Long(1);
        //return userRepository.findAll();
        return responseService.successResponse(userRepository.findAll());
    }

    @RequestMapping(value = "/api/orders", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Order> listOrdersJson(ModelMap model)
    {
        //Long id = new Long(1);
        List<Order> orders = orderRepository.findAll();
        Hibernate.initialize(orders);
        return orders;
    }

    @RequestMapping(value = "/api/users/add", method = RequestMethod.POST)
    public
    @ResponseBody
    User addUserJson(@RequestBody User user)
    {
        //Long id = new Long(1);
        //List<Order> orders = orderRepository.findAll();
        //Hibernate.initialize(orders);
        System.out.println(user);
        return user;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello world! Test push");
		return "hello";
	}


}