package com.springapp.mvc;

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
        userRepository.save(user);

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

}
