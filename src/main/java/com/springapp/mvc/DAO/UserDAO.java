package com.springapp.mvc.DAO;

import com.springapp.mvc.model.User;
import com.springapp.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDAO{
    @Autowired
    private UserRepository userRepository;

    public UserDAO() {
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getByNameAndPass(String name, String pass){
        List<User> userList;

    }

    public User getByID(int id){
        return userRepository.findOne(new Long(id));
    }

    public int save(User user) {
        userRepository.save(user);
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int delete(User user) {
        userRepository.delete(user);
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
