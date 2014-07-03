package com.springapp.mvc.repository;

import com.springapp.mvc.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nik on 04.07.2014.
 */
public interface OrderRepository extends JpaRepository<Order, Long>
{
}
