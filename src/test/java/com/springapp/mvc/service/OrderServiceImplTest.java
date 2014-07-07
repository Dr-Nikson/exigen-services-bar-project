package com.springapp.mvc.service;

import com.springapp.mvc.DAO.UserDAO;
import com.springapp.mvc.model.Order;
import com.springapp.mvc.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
//@Transactional
public class OrderServiceImplTest
{
    @Autowired
    private OrderService orderService;


    @Autowired
    private UserDAO userDAO;


    @Test
    public void testAddOrder() throws Exception
    {
        User userBarney = new User();
        userBarney.setFirstName("Malako");
        userBarney.setLastName("Fakako");
        userBarney.setEmail("mail@g.com");
        userBarney.setPassword("Qwerty");
        userBarney.setPhone("89114452043");

        // TODO: удалить сохранение пользователя
        userDAO.save(userBarney);

        Order order1Barney = new Order();
        order1Barney.setAllRestaurant(true);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = formatter.parse("2014-07-12 18:00:44.0");
        order1Barney.setStartTime(date);
        order1Barney.setOwnAlcohol(false);
        order1Barney.setNote("Oh, please...");
        order1Barney.setStatus("New");
        order1Barney.setUser(userBarney);
        order1Barney.setPersonsNum(18);

        Order order = orderService.addOrder(order1Barney);
    }
}