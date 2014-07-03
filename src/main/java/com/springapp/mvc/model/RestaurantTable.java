package com.springapp.mvc.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nik on 04.07.2014.
 */
@Entity
@Table(name = "restaurant_tables")
public class RestaurantTable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private Integer personsNum;

    @ManyToMany(mappedBy = "tables")
    private Set<Order> orders = new HashSet<Order>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPersonsNum() {
        return personsNum;
    }

    public void setPersonsNum(Integer personsNum) {
        this.personsNum = personsNum;
    }
}
