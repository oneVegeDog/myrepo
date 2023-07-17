package com.fileSystemManage.service.imple;

import com.fileSystemManage.dao.imple.FileDaoImple;
import com.fileSystemManage.dao.imple.UserDaoImple;
import com.fileSystemManage.model.Message;
import com.fileSystemManage.model.User;
import com.fileSystemManage.service.UserInterface;

import java.util.List;

public class UserImple implements UserInterface {
    @Override
    public User getPersonalInfo(String username) {
        return new UserDaoImple().getPersonalInfo(username);
    }

    @Override
    public String register(String username, String password,String userId) {
        //直接调用Dao层的方法
        return new UserDaoImple().register(username,password,userId);
    }
    @Override
    public Integer alterPersonalInfo(User user, String userId) {
        return new UserDaoImple().alterPersonalInfo(user,userId);
    }
    @Override
    public Integer login(String username, String password) {
        return null;
    }

    @Override
    public Message deleteUser(String userId) {
        Message message = new Message();
        message.setStatue(new UserDaoImple().deleteUser(userId));
        return message;
    }
}
