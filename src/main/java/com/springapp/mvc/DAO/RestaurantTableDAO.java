package com.springapp.mvc.DAO;

import com.springapp.mvc.model.RestaurantTable;
import com.springapp.mvc.repository.OrderRepository;
import javafx.util.Pair;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RestaurantTableDAO
{
    @Autowired
    private OrderRepository tableRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public Pair<Integer, List<RestaurantTable>> getTablesForPersons(List<RestaurantTable> freeTables, Integer personsNum)
    {
        List<RestaurantTable> result = new ArrayList<RestaurantTable>();
        Integer reservedForPersons = 0;

        for (RestaurantTable table : freeTables)
        {
            result.add(table);
            reservedForPersons += table.getPersonsNum();

            if (reservedForPersons >= personsNum)
                break;
        }

        return new Pair<Integer, List<RestaurantTable>>(reservedForPersons, result);
    }

    public List<RestaurantTable> getTablesExcludeList(List<RestaurantTable> excluded)
    {
        Criteria criteria = getCriteria();

        for (RestaurantTable table : excluded)
        {
            criteria.add(Restrictions.ne("id", table.getId()));
        }

        return criteria.list();
    }

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
