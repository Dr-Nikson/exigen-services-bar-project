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

/**
 * Created by TofixXx on 07.07.2014.
 */
@Service
@Transactional
public class AuthorizationServiceImpl implements AuthorizationService {

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


    public AuthorizationServiceImpl(HttpSession session, HttpServletResponse response)
    {
        this.session = session;
        this.response = response;
    }

    @Override
    public User authorizeUser(User user) throws AuthorizationException
    {
        User authorizedUser = userDao.get(user.getEmail(), user.getPassword());
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
    public boolean checkAccess(UserRoles role) throws AuthorizationException
    {
            UserRoles UserRole = (UserRoles)session.getAttribute("role");
            if(UserRole == role)
            {
                return true;
            }
            else
            {
                throw new AuthorizationException();
            }
    }
}
