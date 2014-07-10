package com.springapp.mvc.service;

import com.springapp.mvc.exceptions.BanquetOrderException;
import com.springapp.mvc.exceptions.DuplicateOrderException;
import com.springapp.mvc.exceptions.OrderException;
import com.springapp.mvc.model.Order;
import com.springapp.mvc.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Nik on 06.07.2014.
 */
public interface OrderService
{
    /**
     * Проверяет, возможен ли заказ на указанную дату, время, количество человек
     *
     * @param order только поля startTime,personsNum,allRestaurant
     * @return true - если заказ можно разместить
     */
    public boolean checkOrderAvailability(Order order) throws OrderException, DuplicateOrderException;


    /**
     * Добавляет новый заказ
     * Если в поле user есть id - значит добавить заказ на пользователя с этим id
     * Иначе добавить нового пользователя
     *
     * @param order данные заказа
     * @return новый заказ
     * @throws OrderException если заказ не удлось разместить
     * @throws com.springapp.mvc.exceptions.DuplicateOrderException если пользователь уже назначал заказ на текущую дату
     */
    public Order addOrder(Order order) throws OrderException, DuplicateOrderException, BanquetOrderException;


    /**
     * Получить количество свободных мест в данное время (date)
     *
     * @param date дата
     * @return количество свободных мест
     */
    public Integer getFreeSpace(Date date);


    /**
     * Получить список всех заказов
     *
     * @return список заказов
     */
    public List<Order> getOrders();
            //throws OrderException;


    /**
     * Получить список заказов для пользователя
     *
     * @param user пользователь
     * @return список заказов
     */
    public List<Order> getOrders(User user);


    /**
     * Получить список заказов в конкретный день
     *
     * @param day день
     * @return список заказов
     */
    public List<Order> getOrders(Date day);

    public List<Order> getOrders(User user,Date date);

    /**
     * Получить список заказов в промежуток дат
     *
     * @param startDate начало промежутка
     * @param endDate   конец промежутка
     * @return список заказов
     */
    public List<Order> getOrders(Date startDate, Date endDate);
}
