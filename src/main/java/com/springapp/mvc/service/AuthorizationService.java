package com.springapp.mvc.service;

import com.springapp.mvc.exceptions.AuthorizationException;
import com.springapp.mvc.model.User;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

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
    public User authorizeUser(User user, HttpSession session, HttpServletResponse response) throws AuthorizationException;

    /**
     * Метод проверяет права авторизованного пользователя
     *
     * @param role права
     * @return User, если права у пользователя есть
     * @throws AuthorizationException если пользователь не авторизован
     */
    public User checkAccess(UserRoles role, HttpSession session) throws AuthorizationException;

    /**
     * Метод проверяет права авторизованного пользователя
     *
     * @param roles массив прав
     * @return User, если права у пользователя есть
     * @throws AuthorizationException если пользователь не авторизован
     */
    public User checkAccess(List<UserRoles> roles, HttpSession session) throws AuthorizationException;


    public List<UserRoles> getRolesListForAddOrder();
}
