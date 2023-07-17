package com.fileSystemManage.service.imple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.dao.imple.ManagerDaoImple;
import com.fileSystemManage.model.Message;
import com.fileSystemManage.model.User;
import com.fileSystemManage.service.ManagerInterface;

import java.util.ArrayList;
import java.util.List;

public class ManagerImple implements ManagerInterface {
    public static final List<Integer> STATUES = new ArrayList<>();
    static {
        STATUES.add(1);
        STATUES.add(2);
    }
    @Override
    public Message updateUserStatue(String userId, Integer statue) {
        Message message = new Message();
        if(STATUES.contains(statue)){
            message.setStatue(new ManagerDaoImple().updateUserStatue(userId,statue));
        }else{
            message.setStatue(401);
            message.setDescription("状态输入错误");
        }
        return message;
    }

    @Override
    public Message getUsers(Integer pageNum, ObjectMapper om) {
        Message message = new Message();
        List<User> list = new ManagerDaoImple().getUsers(pageNum);
        String usersjson = "";
        try {
            usersjson = om.writeValueAsString(list);
            message.setStatue(200);
            message.setJsonstr(usersjson);
        } catch (JsonProcessingException e) {
            message.setDescription("转化json出错");
            message.setStatue(500);
            e.printStackTrace();
        }
        return message;
    }
    @Override
    public Integer getUsersTotalNum(){
        return new ManagerDaoImple().getUsersTotalNum();
    }
}
