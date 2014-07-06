package com.springapp.mvc.service;

import com.springapp.mvc.exceptions.AuthorizationException;
import com.springapp.mvc.model.User;

/**
 * Created by Nik on 06.07.2014.
 */
public interface AuthorizationService
{
    /**
     * Метод авторизует пользователя:
     * 1) Сохраняет ключ авторизации в http-сессии
     * 2) Сохраняет ключ авторизации в cookies, с флагом http-only-cookies
     *
     * @param user Пользователь для авторизации.
     * @return возвращает авторизованного пользователя
     * @throws AuthorizationException в случае неудачи
     */
    public User authorizeUser(User user) throws AuthorizationException;

    /**
     * Метод проверяет права авторизованного пользователя
     *
     * @param role права
     * @return true, если права у пользователя есть
     * @throws AuthorizationException если пользователь не авторизован
     */
    public boolean checkAccess(UserRoles role) throws AuthorizationException;
}
