package com.springapp.mvc.model;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nik on 04.07.2014.
 */
@Entity
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition="TEXT")
    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reserved_tables",
               joinColumns = {@JoinColumn(name = "order_id")},
               inverseJoinColumns = {@JoinColumn(name = "restaurant_table_id")})
    private Set<RestaurantTable> tables = new HashSet<RestaurantTable>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addTable(RestaurantTable table)
    {
        this.tables.add(table);
    }


    public Set<RestaurantTable> getReservedTables()
    {
        return this.tables;
    }
}
