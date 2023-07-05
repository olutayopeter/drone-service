package com.blusalt.drone.repository;


import com.blusalt.drone.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<Users,Long> {

    @Query(value = "SELECT * FROM TBL_USERS WHERE LOWER(USERNAME) = :username", nativeQuery = true)
    List<Users> findByUsername(@Param("username") String user);

    @Query(value = "SELECT * FROM TBL_USERS WHERE LOWER(USERNAME) = :username and PX = :px",nativeQuery = true)
    List<Users> findByUsernameAndPx(@Param("username") String user, @Param("px") String px);
}

