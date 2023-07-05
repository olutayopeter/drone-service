package com.blusalt.drone.service;


import com.blusalt.drone.model.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {

   List<Users> findByUsername(String user);

   Iterable<Users> findAll();

   Optional<Users> findById(long id);

   Users save(Users user);

   List<Users> findByUsernameAndPx(String user, String px);
}
