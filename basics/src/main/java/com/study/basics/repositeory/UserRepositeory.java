package com.study.basics.repositeory;

import com.study.basics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositeory extends JpaRepository<User, Long> {


}
