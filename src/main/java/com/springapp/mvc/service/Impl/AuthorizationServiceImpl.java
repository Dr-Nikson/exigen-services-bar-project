package com.springapp.mvc.service.Impl;

import com.springapp.mvc.DAO.UserDAO;
import com.springapp.mvc.exceptions.AuthorizationException;
import com.springapp.mvc.model.User;
import com.springapp.mvc.service.AuthorizationService;
import com.springapp.mvc.service.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TofixXx on 07.07.2014.
 */
@Service
@Transactional
public class AuthorizationServiceImpl implements AuthorizationService
{

    @Autowired
    private UserDAO userDao;
    /**
     * Метод авторизует пользователя:
     * 1) Сохраняет ключ авторизации в http-сессии
     * 2) Сохраняет ключ авторизации в cookies, с флагом http-only-cookies
     *
     * @param user Пользователь для авторизации.
     * @return возвращает авторизованного пользователя
     * @throws AuthorizationException в случае неудачи
     */

    HttpSession session;
    HttpServletResponse response;


    @Override
    public User authorizeUser(User user, HttpSession session, HttpServletResponse response) throws AuthorizationException
    {
        User authorizedUser = userDao.get(user.getEmail(), user.getPassword());
        this.session = session;
        this.response = response;
        if(authorizedUser != null)
        {
            session.setAttribute("user", user);
            session.setAttribute("role", UserRoles.USER);
            response.addCookie(new Cookie("id", user.getId().toString()));
        }
        else
        {
            throw new AuthorizationException();
        }
        return authorizedUser;
    }

    /**
     * Метод проверяет права авторизованного пользователя
     *
     * @param role права
     * @return true, если права у пользователя есть
     * @throws AuthorizationException если пользователь не авторизован
     */
    @Override
    public User checkAccess(UserRoles role, HttpSession session) throws AuthorizationException
    {
        UserRoles UserRole = (UserRoles) session.getAttribute("role");

        if (UserRole == role)
            {
                return (User) session.getAttribute("user");
            }
            else
            {
                throw new AuthorizationException();
            }

        //return null;
    }


    /**
     * Метод проверяет права авторизованного пользователя
     *
     * @param roles   массив прав
     * @param session
     * @return user, если права у пользователя есть
     * @throws com.springapp.mvc.exceptions.AuthorizationException если пользователь не авторизован
     */
    @Override
    public User checkAccess(List<UserRoles> roles, HttpSession session) throws AuthorizationException
    {
        //boolean isAuhtorized = false;
        User user = null;

        for (UserRoles role : roles)
        {

            try
            {
                user = checkAccess(role, session);
                if (user != null)
                    break;
            }
            catch (AuthorizationException e)
            {

            }
        }

        if (user == null)
            throw new AuthorizationException("У пользователя недостаточно прав");

        return user;
    }

    @Override
    public User getActiveUser(HttpSession session) throws AuthorizationException
    {
        User user = (User) session.getAttribute("user");
        if (user == null)
            throw new AuthorizationException("Пользователь не авторизован");
        return user;
    }

    @Override
    public List<UserRoles> getRolesListForAddOrder()
    {
        ArrayList<UserRoles> userRoles = new ArrayList<UserRoles>();
        userRoles.add(UserRoles.USER);
        userRoles.add(UserRoles.ADMIN);
        return userRoles;
    }
}
