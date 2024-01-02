package com.example.laborator7.dao.impl;

import com.example.laborator7.entity.User;
import com.example.laborator7.interceptor.Logged;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class UserDaoImpl extends GenericDaoImpl<User> {
    public UserDaoImpl() {
        super(User.class);
    }

    @Logged
    @Override
    public User create(User user) throws Exception {
        if (findByUsername(user.getUsername()) != null)
            throw new Exception("Username already exists");
        return super.create(user);
    }

    @Override
    public User update(User user) {
        return super.update(user);
    }

    @Override
    public User get(Integer id) {
        return super.get(id);
    }

    public List<User> getAll() {
        return super.getAll("User.findAll");
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    public User login(User user) throws Exception {

        User foundUser = findByUsername(user.getUsername());

        if (foundUser == null)
        {
            throw new Exception("User doesn't exist");
        }

        if (!user.getPass().equals(foundUser.getPass()))
            throw new Exception("Wrong credentials");
        return foundUser;
    }

    public User findByUsername(String username) {

        return super.getByProperty("User.findByUsername", username);
    }
}
