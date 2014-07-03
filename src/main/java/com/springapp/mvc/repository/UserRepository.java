package com.springapp.mvc.repository;

import com.springapp.mvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nik on 03.07.2014.
 */
public interface UserRepository extends JpaRepository<User, Long>
{
}
