package com.springapp.mvc;

import com.springapp.mvc.DAO.UserDAO;
import com.springapp.mvc.model.Order;
import com.springapp.mvc.model.RestaurantTable;
import com.springapp.mvc.model.User;
import com.springapp.mvc.repository.OrderRepository;
import com.springapp.mvc.repository.RestaurantTableRepository;
import com.springapp.mvc.repository.UserRepository;
import com.springapp.mvc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class AppTests
{
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantTableRepository tableRepository;


    @Autowired
    private UserDAO userDAO;


    @Autowired
    private UserService userService;


    /*@Before
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
    }*/

    @Test
    public void fillDB() throws Exception
    {
        clearDB();

        User userBarney = new User();
        userBarney.setFirstName("Barney");
        userBarney.setLastName("Stinson");
        userBarney.setEmail("stinson@g.com");
        userBarney.setPassword("Qwerty");
        userBarney.setPhone("89110051010");

        User userJesse = new User();
        userJesse.setFirstName("Jesse");
        userJesse.setLastName("Pinkman");
        userJesse.setEmail("jpinkman@j.com");
        userJesse.setPassword("1111");
        userJesse.setPhone("89062340132");

        User userEve = new User();
        userEve.setFirstName("Eve");
        userEve.setLastName("Hereve");
        userEve.setEmail("e.hereve@gmail.com");
        userEve.setPassword("132");
        userEve.setPhone("88001201920");

        userService.registerUser(userBarney);
        userService.registerUser(userJesse);
        userService.registerUser(userEve);
        //userRepository.save(userBarney);
        //userRepository.save(userJesse);
        //userRepository.save(userEve);

        RestaurantTable table1 = new RestaurantTable();
        table1.setPersonsNum(2);
        tableRepository.save(table1);

        RestaurantTable table2 = new RestaurantTable();
        table2.setPersonsNum(2);
        tableRepository.save(table2);

        RestaurantTable table3 = new RestaurantTable();
        table3.setPersonsNum(2);
        tableRepository.save(table3);

        RestaurantTable table4 = new RestaurantTable();
        table4.setPersonsNum(4);
        tableRepository.save(table4);

        RestaurantTable table5 = new RestaurantTable();
        table5.setPersonsNum(4);
        tableRepository.save(table5);

        RestaurantTable table6 = new RestaurantTable();
        table6.setPersonsNum(4);
        tableRepository.save(table6);

        Order order1Barney = new Order();
        order1Barney.setAllRestaurant(true);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = formatter.parse("2014-07-08 18:00:44.0");
        order1Barney.setStartTime(date);
        order1Barney.setOwnAlcohol(false);
        order1Barney.setNote("Oh, please...");
        order1Barney.setStatus("New");
        order1Barney.setUser(userBarney);
        order1Barney.setPersonsNum(18);

        for (RestaurantTable table : tableRepository.findAll())
        {
            order1Barney.getTables().add(table);
        }

        /*order1Barney.getTables().add(table1);
        order1Barney.getTables().add(table2);
        order1Barney.getTables().add(table3);*/

        Order order2Barney = new Order();
        order2Barney.setAllRestaurant(false);
        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date2 = formatter.parse("2014-07-09 15:00:44.0");
        order2Barney.setStartTime(date2);
        order2Barney.setOwnAlcohol(false);
        order2Barney.setNote("Oh, please...");
        order2Barney.setStatus("New");
        order2Barney.setPersonsNum(8);
        order2Barney.setUser(userBarney);
        order2Barney.getTables().add(table4);
        order2Barney.getTables().add(table5);


        Order orderJesse = new Order();
        orderJesse.setAllRestaurant(false);
        DateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date3 = formatter.parse("2014-07-09 13:00:44.0");
        orderJesse.setStartTime(date3);
        orderJesse.setOwnAlcohol(false);
        orderJesse.setNote("Fine!");
        orderJesse.setStatus("New");
        orderJesse.setPersonsNum(3);
        orderJesse.setUser(userJesse);
        orderJesse.getTables().add(table1);
        orderJesse.getTables().add(table2);

        orderRepository.save(order1Barney);
        orderRepository.save(order2Barney);
        orderRepository.save(orderJesse);

        System.out.println("ok");
    }

    protected void clearDB()
    {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        tableRepository.deleteAll();
    }


}
