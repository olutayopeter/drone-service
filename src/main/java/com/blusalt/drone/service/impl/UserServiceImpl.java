package com.blusalt.drone.service.impl;

import com.blusalt.drone.model.Users;
import com.blusalt.drone.repository.UserRepository;
import com.blusalt.drone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;



@Service
public class UserServiceImpl implements UserService {

    private static Logger log = Logger.getLogger(UserServiceImpl.class.getName());


    @Autowired(required = false)
    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public List<Users> findByUsername(String user) {

        return userRepository.findByUsername(user);
    }

    @Override
    public Iterable<Users> findAll() {

        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findById(long id) {

        return userRepository.findById(id);
    }

    @Override
    public Users save(Users user) {
        return userRepository.save(user);
    }



    @Override
    public List<Users> findByUsernameAndPx(String user, String px) {

        return userRepository.findByUsernameAndPx(user, px);
    }


}
