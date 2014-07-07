package com.springapp.mvc.DAO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
@Transactional
public class RestaurantTableDAOTest
{
    @Autowired
    private RestaurantTableDAO restaurantTableDAO;

    @Test
    public void testGetAvailableSpace() throws Exception
    {
        Long availableSpace = restaurantTableDAO.getAvailableSpace();
        assertEquals((long) availableSpace, (long) 18);
    }
}