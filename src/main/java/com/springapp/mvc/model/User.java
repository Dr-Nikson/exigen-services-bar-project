package com.springapp.mvc.model;

import com.springapp.mvc.service.UserRoles;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nik on 03.07.2014.
 */
@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private String firstName;

    @Basic
    private String lastName;

    @Basic
    private String email;

    @Basic
    private String phone;

    @Basic
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    //@OneToMany(fetch = FetchType.EAGER,mappedBy = "user") //- без этой фигни lazy load сваливается
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new HashSet<Order>();

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String name)
    {
        this.firstName = name;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Set<Order> getOrders()
    {
        return orders;
    }

    public UserRoles getRole()
    {
        return role;
    }

    public void setRole(UserRoles role)
    {
        this.role = role;
    }
}
