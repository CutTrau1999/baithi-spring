package com.example.baithi.service;

import com.example.baithi.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    User update(User user);

    List<User> findAll();

    Optional<User> findById(int id);

    boolean delete(User user);
}
