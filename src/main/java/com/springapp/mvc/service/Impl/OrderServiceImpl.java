package com.springapp.mvc.service.Impl;

import com.springapp.mvc.DAO.OrderDAO;
import com.springapp.mvc.DAO.RestaurantTableDAO;
import com.springapp.mvc.exceptions.OrderException;
import com.springapp.mvc.model.Order;
import com.springapp.mvc.model.RestaurantTable;
import com.springapp.mvc.model.User;
import com.springapp.mvc.service.OrderService;
import com.springapp.mvc.service.UserService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
//@Qualifier(value = "orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private RestaurantTableDAO tableDAO;

    @Autowired
    private UserService userService;


    @Override
    public boolean checkOrderAvailability(Order order) {
        Long reservedSpace = orderDAO.getReservedSpace(order.getStartTime());
        Long availableSpace = tableDAO.getAvailableSpace();
        return (availableSpace - reservedSpace) >= order.getPersonsNum();
    }

    @Override
    public Order addOrder(Order order) throws OrderException {
        // 1) Проверим на возможность добавления ордера
        if (!checkOrderAvailability(order))
            throw new OrderException("Недостаточно места - столы заняты!");
        // 2) Получить все заказы на дату из order.startTime
        List<Order> orders = orderDAO.getOrders(order.getStartTime());
        // 3) Получить все заказанные столы на дату из order.startTime
        List<RestaurantTable> reservedTables = new ArrayList<RestaurantTable>();
        for (Order tmpOrder : orders) {
            for (RestaurantTable table : tmpOrder.getTables()) {
                reservedTables.add(table);
            }
        }
        // 4) Получить все свободные столы на дату из order.startTime
        List<RestaurantTable> freeTables = tableDAO.getTablesExcludeList(reservedTables);
        // 5) Получить столы на необходимое количество человек - order.personsNum
        Pair<Integer, List<RestaurantTable>> tablesForPersons = tableDAO.getTablesForPersons(freeTables, order.getPersonsNum());
        // ???? 6) Если заказ на весь ресторан - получаем все столы
        // 7) Проверяем ошибки
        if (tablesForPersons.getKey() < order.getPersonsNum())
            throw new OrderException("Неудалось добавить заказ - неудалось получить достаточное количество столов (получено:"
                    + tablesForPersons.getKey() + "; необходимо:" + order.getPersonsNum());
        // 8) Добавляем столы к заказу
        for (RestaurantTable table : tablesForPersons.getValue()) {
            order.getTables().add(table);
        }
        // 9) Сохраним пользователя
        //UserService userService = new UserServiceImpl();

        // 10) Добавить заказ
        order = orderDAO.save(order);
        return order;
    }

    @Override
    public Integer getFreeSpace(Date date) {
        return (int) (long) (tableDAO.getAvailableSpace() - orderDAO.getReservedSpace(date));
    }

    @Override
    public List<Order> getOrders(){
        return orderDAO.getAllOrders();
    }

    @Override
    public List<Order> getOrders(User user) {
        return orderDAO.getOrders(user);
    }

    @Override
    public List<Order> getOrders(Date day) {
        return orderDAO.getOrders(day);
    }

    @Override
    public List<Order> getOrders(Date startDate, Date endDate) {
        return orderDAO.getOrders(startDate, endDate);
    }
}
