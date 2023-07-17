package com.fileSystemManage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.Message;

public interface ManagerInterface {
    //修改用户状态
    Message updateUserStatue(String userId,Integer statue);
    //获取所有用户
    Message getUsers(Integer pageNum, ObjectMapper om);
    //获取用户总数
    Integer getUsersTotalNum();
}
