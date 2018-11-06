package com.apap.tutorial6.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial6.model.UserRoleModel;
import com.apap.tutorial6.repository.UserRoleDB;

@Service
public class UserRoleServiceImpl implements UserRoleService{
    @Autowired
    private UserRoleDB userDb;

    public UserRoleModel addUser(UserRoleModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public UserRoleModel findUserByUsername(String username) {
        // TODO Auto-generated method stub
        return userDb.findByUsername(username);
    }
    @Override
    public void ubahPassword(UserRoleModel user, String passBaru) {
        String password = encrypt(passBaru);
        user.setPassword(password);
        userDb.save(user);
    }

}