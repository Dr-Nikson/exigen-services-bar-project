package com.springapp.mvc.DAO;

import com.springapp.mvc.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
@Transactional
public class OrderDAOTest
{
    @Autowired
    private OrderDAO orderDAO;

    @Test
    public void testGetOrders() throws Exception
    {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = formatter.parse("2014-07-08 18:00:44.0");
        List<Order> orders = orderDAO.getOrders(date);
        assertEquals(orders.size(), 3);
    }

    @Test
    public void testGetReservedSpace() throws Exception
    {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = formatter.parse("2014-07-08 18:00:44.0");
        Long reservedSpace = orderDAO.getReservedSpace(date);
        assertEquals((long) reservedSpace, (long) 3);
    }
}