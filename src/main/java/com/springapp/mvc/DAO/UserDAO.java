package com.springapp.mvc.DAO;

import com.springapp.mvc.model.User;
import com.springapp.mvc.repository.UserRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Service
@Transactional
public class UserDAO
{
    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    //@PersistenceContext
    //private SessionFactory sessionFactory;


    /*public void testMePLZ()
    {

        User user = new User();
        user.setFirstName("FirstName");

        Criteria criteria = getCriteria();
        Object o = criteria.add(Example.create(user).excludeZeroes()).list();
    }*/


    public boolean isUserExist(String email)
    {
        Query q = entityManager.createQuery("SELECT COUNT(u.id) FROM User u WHERE u.email = :email").setParameter("email", email);
        Long foundedUsersCount = (Long) q.getSingleResult();
        return foundedUsersCount >= 1;
    }


    public List<User> get()
    {
        return userRepository.findAll();
    }

    public User get(String login, String pass)
    {
        Criteria criteria = this.getCriteria();
        User user = new User();
        user.setEmail(login);
        user.setPassword(pass);
        criteria.add(Example.create(user).excludeZeroes());
        return (User) criteria.uniqueResult();
    }

    public User get(Long id)
    {
        return userRepository.findOne(id);
    }

    public User save(User user)
    {
        return userRepository.save(user);
    }

    public void delete(User user)
    {
        userRepository.delete(user);
    }

    protected Criteria getCriteria()
    {
        Session session = (Session) entityManager.getDelegate();
        // без транзакшенал будет ошибка мол сессия закрыта. Так можно исправить:
        //session = session.getSessionFactory().openSession();
        return session.createCriteria(User.class);
    }

    protected CriteriaQuery<User> createCriteriaQuery()
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        return criteriaBuilder.createQuery(User.class);
    }

}
