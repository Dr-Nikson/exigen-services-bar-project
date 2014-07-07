package com.springapp.mvc;

import com.springapp.mvc.DAO.UserDAO;
import com.springapp.mvc.model.Order;
import com.springapp.mvc.model.RestaurantTable;
import com.springapp.mvc.model.User;
import com.springapp.mvc.repository.OrderRepository;
import com.springapp.mvc.repository.RestaurantTableRepository;
import com.springapp.mvc.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class AppTests
{
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantTableRepository tableRepository;

    @Autowired
    private UserDAO userDAO;


    @Before
    public void setup()
    {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void simple() throws Exception
    {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("hello"));
    }

    @Test
    public void testDAO() throws Exception
    {
        //userDAO.testMePLZ();
        System.out.println("Hello, bitch");
    }

    @Test
    public void testAddUser() throws Exception
    {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user = userRepository.save(user);
    }

    @Test
    public void testOrders() throws Exception
    {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user = userRepository.save(user);

        Order order = new Order();
        order.setNote("blah blah blah");
        order.setUser(user);

        RestaurantTable table = new RestaurantTable();
        table.setPersonsNum(2);
        //tableRepository.save(table);
        order.getTables().add(table);

        table = new RestaurantTable();
        table.setPersonsNum(4);
        //tableRepository.save(table);
        order.getTables().add(table);

        table = new RestaurantTable();
        table.setPersonsNum(6);
        //tableRepository.save(table);
        order.getTables().add(table);

        orderRepository.save(order);

        order = new Order();
        order.setNote("test 2");
        order.setUser(user);
        orderRepository.save(order);

        System.out.println("dasdas");
    }

    @Test
    public void testDB() throws Exception
    {
        User userBarney = new User();
        userBarney.setFirstName("Barney");
        userBarney.setLastName("Stinson");
        userBarney.setEmail("stinson@g.com");
        userBarney.setPhone("1234567890");

        User userJesse = new User();
        userJesse.setFirstName("Jesse");
        userJesse.setLastName("Pinkman");
        userJesse.setEmail("jpinkman@j.com");
        userJesse.setPhone("4321");

        userRepository.save(userBarney);
        userRepository.save(userJesse);

        Order order1Barney = new Order();
        order1Barney.setAllRestaurant(true);
        order1Barney.setStartTime(new java.util.Date(2014 - 1900, 6, 7, 12, 0, 0));
        order1Barney.setOwnAlcohol(false);
        order1Barney.setNote("Oh, please...");
        order1Barney.setStatus("Ok!");
        order1Barney.setUser(userBarney);

        Order order2Barney = new Order();
        order2Barney.setAllRestaurant(false);
        order2Barney.setStartTime(new java.util.Date(2014 - 1900, 6, 9, 14, 0, 0));
        order2Barney.setOwnAlcohol(true);
        order2Barney.setNote("Oh, please...");
        order2Barney.setStatus("Ok!");
        order2Barney.setPersonsNum(9);
        order2Barney.setUser(userBarney);

        Order orderJesse = new Order();
        orderJesse.setAllRestaurant(false);
        orderJesse.setStartTime(new java.util.Date(2014 - 1900, 6, 8, 17, 30, 0));
        orderJesse.setOwnAlcohol(true);
        orderJesse.setNote("Fine!");
        orderJesse.setStatus("ok");
        orderJesse.setPersonsNum(3);
        orderJesse.setUser(userJesse);

        RestaurantTable table1 = new RestaurantTable();
        table1.setPersonsNum(order1Barney.getPersonsNum());
        order1Barney.getTables().add(table1);
        orderRepository.save(order1Barney);

        RestaurantTable table2 = new RestaurantTable();
        table2.setPersonsNum(order2Barney.getPersonsNum());
        order1Barney.getTables().add(table2);
        orderRepository.save(order2Barney);

        RestaurantTable table3 = new RestaurantTable();
        table3.setPersonsNum(orderJesse.getPersonsNum());
        orderJesse.getTables().add(table3);
        orderRepository.save(orderJesse);

        System.out.println("ok");
    }


}
