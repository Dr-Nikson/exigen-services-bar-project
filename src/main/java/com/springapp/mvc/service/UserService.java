package com.springapp.mvc.service;

import com.springapp.mvc.exceptions.UserException;
import com.springapp.mvc.model.User;

import java.util.List;

/**
 * Created by Nik on 06.07.2014.
 */
public interface UserService
{
    /**
     * Метод регистрирует нового пользователя
     * (необходимо формировать хэш пароля)
     *
     * @param user содержит данные о пользователе (без id)
     * @return Новый пользователь
     * @throws com.springapp.mvc.exceptions.UserException если пользователь с таким email существует
     */
    public User registerUser(User user) throws UserException;

    /**
     * Получает пользователя по логину и паролю
     * (необходимо формировать хэш пароля)
     *
     * @param login    логин(емэил) пользователя
     * @param password пароль пользователя(не хэш)
     * @return пользователь с заданными логином\паролем
     * @throws UserException если не удалось залогинить
     */
    public User loginUser(String login, String password) throws UserException;


    /**
     * Получить список всех пользователей
     *
     * @return список пользователей
     */
    public List<User> getUsers();
}

