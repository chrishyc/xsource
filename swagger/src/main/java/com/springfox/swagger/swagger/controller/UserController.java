package com.springfox.swagger.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.springfox.swagger.swagger.model.User;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "用户相关接口", description = "提供用户相关的 Rest API")
@RestController
@RequestMapping("/user")
public class UserController {
    @ApiOperation("新增用户接口")
    @PostMapping("/add")
    public boolean addUser(@RequestBody User user) {
        return false;
    }
    
    @GetMapping("/find/{id}")
    public List<User> findById(@PathVariable("id") int id) {
        return new ArrayList<>();
    }
    
    @PutMapping("/update")
    public boolean update(@RequestBody User user) {
        return true;
    }
    @ApiIgnore
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return true;
    }
}
