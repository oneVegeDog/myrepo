package com.fileSystemManage.dao;

import com.fileSystemManage.model.User;

import java.util.List;

public interface UserDao {
    User getPersonalInfo(String username);
    String register(String uername,String password,String userId);
    Integer alterPersonalInfo(User user,String userId);
    Integer deleteUser(String userId);


//    Integer login(String username,String password);

}
