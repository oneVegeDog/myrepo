package com.fileSystemManage.dao;

import com.fileSystemManage.model.User;

import java.util.List;

public interface ManagerDao {
    //更新用户状态
    Integer updateUserStatue(String userId,Integer statue);
    //获取用户所有信息
    List<User> getUsers(Integer pageNum);
    //获取用户总数
    Integer getUsersTotalNum();
}
