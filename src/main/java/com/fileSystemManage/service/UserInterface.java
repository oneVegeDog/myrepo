package com.fileSystemManage.service;

import com.fileSystemManage.model.Message;
import com.fileSystemManage.model.User;

import java.util.List;

public interface UserInterface {
    User getPersonalInfo(String username);
    String register(String uername,String password,String userId);
    Integer alterPersonalInfo(User user,String userId);
    Integer login(String username,String password);
    Message deleteUser(String userId);
}
