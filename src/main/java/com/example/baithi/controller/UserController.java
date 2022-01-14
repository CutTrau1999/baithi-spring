package com.example.baithi.controller;

import com.example.baithi.entity.User;
import com.example.baithi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    //get list
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

        HashMap<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("limit", limit);
        response.put("data", userService.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // create
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody com.example.baithi.entity.User user) {

        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //search by id
    @RequestMapping(method = RequestMethod.GET, path = "{id}")
    public ResponseEntity<Object> getDetail(@PathVariable int id) {
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }
    //edit
    @RequestMapping(method = RequestMethod.PUT, path = "{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody com.example.baithi.entity.User updatedUser) {
        Optional<com.example.baithi.entity.User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            com.example.baithi.entity.User user = optionalUser.get();
            user.setName(updatedUser.getName());
            user.setAge(updatedUser.getAge());
            user.setSalary(updatedUser.getSalary());
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    //delete
    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        Optional<com.example.baithi.entity.User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            userService.delete(optionalUser.get());
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
}

