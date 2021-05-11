package org.cv.sf.service;

import org.cv.sf.dto.entity.User;
import org.cv.sf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(long id){
        return userRepository.findById(id);
    }
}
