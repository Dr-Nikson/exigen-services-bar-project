package com.springapp.mvc.DAO;

import com.springapp.mvc.model.Order;
import com.springapp.mvc.model.RestaurantTable;
import com.springapp.mvc.model.User;
import com.springapp.mvc.repository.OrderRepository;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


@Service
@Transactional
public class OrderDAO {
    @Autowired
    private OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Order> getOrders(Date byDay) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(byDay);

        /*
         *   Устанавливаем дату на byDay и время 00:00
         *   Это - начальная дата
         */
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 1);
        Date loDate = new Date(calendar.getTime().getTime());

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        Date hiDate = new Date(calendar.getTime().getTime());

        /*
         *   Делаем выборку с lowDate < startTime < hiDate
         */
        Criteria criteria = this.getCriteria();
        criteria.add(Restrictions.between("startTime", loDate, hiDate));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); // Я НЕ ЗНАЮ ПОЧЕМУ БЕЗ ЭТОГО НЕ РАБОТАЕТ, НО ЕСЛИ ЗАКОМЕНТИТЬ - НА ВЫХОДЕ БУДУТ ДУПЛИКАТЫ ОРДЕРОВ
        return criteria.list();
    }

    public List<Order> getOrders(Date startDate, Date endDate) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, startDate.getHours());
        calendar.set(Calendar.MINUTE, startDate.getMinutes());
        Date loDate = new Date(calendar.getTime().getTime());

        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, endDate.getHours());
        calendar.set(Calendar.MINUTE, endDate.getMinutes());
        Date hiDate = new Date(calendar.getTime().getTime());
        Criteria criteria = this.getCriteria();
        criteria.add(Restrictions.between("startTime", loDate, hiDate));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public Long getReservedSpace(Date day) {
        Long reservedSpace = (long) 0;

        for (Order order : this.getOrders(day)) {
            for (RestaurantTable table : order.getTables()) {
                reservedSpace += table.getPersonsNum();
            }
        }

        return reservedSpace;
    }

    public Order save(Order order) {
        order = orderRepository.save(order);
        //getSession().getTransaction().commit();
        //entityManager.flush();
        return order;
    }

    protected Session getSession() {
        return (Session) entityManager.getDelegate();
    }

    protected Criteria getCriteria() {
        Session session = getSession();
        // без транзакшенал будет ошибка мол сессия закрыта. Так можно исправить:
        //session = session.getSessionFactory().openSession();
        return session.createCriteria(Order.class);
    }

    protected CriteriaQuery<Order> createCriteriaQuery() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        return criteriaBuilder.createQuery(Order.class);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrders(User user) {
        return entityManager.createQuery(
                "SELECT ord FROM Order ord WHERE ord.user.id = :inUser")
                .setParameter("inUser", user.getId())
                .getResultList();
    }
}
