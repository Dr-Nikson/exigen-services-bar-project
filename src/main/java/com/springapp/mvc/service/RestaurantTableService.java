package com.springapp.mvc.service;

import com.springapp.mvc.model.RestaurantTable;

import java.util.List;

/**
 * Created by Nik on 06.07.2014.
 */
public interface RestaurantTableService
{
    /**
     * Получить список всех столов
     *
     * @return список столов
     */
    public List<RestaurantTable> getTables();
}
