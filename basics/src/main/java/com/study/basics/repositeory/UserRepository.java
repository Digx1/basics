package com.study.basics.repositeory;

import com.study.basics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findUsersByName(String name);
    List<User> findUsersByEmail(String email );
    List<User> findUsersByRole(String role);
    List<User> findUsersByCompany_Id(Integer companyId);

     @Query(nativeQuery = true, value = "SELECT * FROM users WHERE user_name = :query")
    List<User> searchUsersByQuery(@Param("query") String query);
}
