package com.springapp.mvc.service.Impl;

import com.springapp.mvc.DAO.UserDAO;
import com.springapp.mvc.exceptions.UserException;
import com.springapp.mvc.model.User;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Nik on 06.07.2014.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDAO userDao;


    private String Hash(String password)
    {
        MessageDigest md;
        byte[] passwordBytes;
        String hashedPassword = new String();
        try
        {
            md = MessageDigest.getInstance("MD5");
            passwordBytes = password.getBytes("UTF-8");
            hashedPassword = md.digest().toString();
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }
    /**
     * Метод регистрирует нового пользователя
     * (необходимо формировать хэш пароля)
     *
     * @param user содержит данные о пользователе (без id)
     * @return Новый пользователь
     */
    @Override
    public User registerUser(User user)
    {
        String password = user.getPassword();
        user.setPassword(Hash(password));

        User savedUser = userDao.save(user);
        return savedUser;

    }

    /**
     * Получает пользователя по логину и паролю
     * (необходимо формировать хэш пароля)
     *
     * @param login    логин(емэил) пользователя
     * @param password пароль пользователя(не хэш)
     * @return пользователь с заданными логином\паролем
     * @throws UserException если не удалось залогинить
     */
    @Override
    public User loginUser(String login, String password) throws UserException
    {
        String hashedPassword = Hash(password);
        User user = (new UserDAO()).get(login, hashedPassword);
        if(user == null)
        {
            throw new UserException();
        }
        return user;
    }



    /**
     * Получить список всех пользователей
     *
     * @return список пользователей
     */
    @Override
    public List<User> getUsers()
    {
        return userDao.get();
    }
}