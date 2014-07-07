package com.springapp.mvc.DAO;

import com.springapp.mvc.model.RestaurantTable;
import com.springapp.mvc.repository.OrderRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@Service
@Transactional
public class RestaurantTableDAO
{
    @Autowired
    private OrderRepository tableRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Long getAvailableSpace()
    {
        Long aviailableSpace = (long) 0;
        Query query = entityManager.createQuery("SELECT SUM(t.personsNum) FROM RestaurantTable t");
        aviailableSpace = (Long) query.getSingleResult();

        return aviailableSpace;
    }

    protected Criteria getCriteria()
    {
        Session session = (Session) entityManager.getDelegate();
        // без транзакшенал будет ошибка мол сессия закрыта. Так можно исправить:
        //session = session.getSessionFactory().openSession();
        return session.createCriteria(RestaurantTable.class);
    }

    protected CriteriaQuery<RestaurantTable> createCriteriaQuery()
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        return criteriaBuilder.createQuery(RestaurantTable.class);
    }
}
