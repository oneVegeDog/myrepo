package com.fileSystemManage.dao.imple;

import com.fileSystemManage.model.Message;
import com.fileSystemManage.utils.ConnectionUtil;
import com.fileSystemManage.dao.DataBaseOperate;
import com.fileSystemManage.dao.UserDao;
import com.fileSystemManage.model.User;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class UserDaoImple implements UserDao {

    @Override
    public User getPersonalInfo(String username) {
        User user = null;
        Connection connection = ConnectionUtil.getConnection();
        try {
            //查询用户信息
            PreparedStatement ps = connection.prepareStatement(DataBaseOperate.QUERYUSERINFO);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String userId = rs.getString("user_id");
            String registerDate = rs.getDate("register_date").toString();
            Integer statue = rs.getInt("user_statue");
            //封装为User对象，转成json发给前端
            user = new User(username,userId,registerDate,statue);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }


    @Override
    public String register(String username, String password, String userId) {
        Connection connection = ConnectionUtil.getConnection();
        int flag = 0;
        try {
            connection.setAutoCommit(false);
            //往user表里面插记录
            com.fileSystemManage.dao.DataBaseOperate.insertUser(username,userId,password,connection);
            //往文件表里面插记录，作为用户的文件根目录
            com.fileSystemManage.dao.DataBaseOperate.insertFile(userId, UUID.randomUUID().toString(),username,null,null,1,2,1,connection);
            //指定用户的角色
            com.fileSystemManage.dao.DataBaseOperate.designateRole(userId,1,connection);
            //提交数据库更改
            connection.commit();
            return "html/user.html";
        } catch (SQLException e) {
            e.printStackTrace();
            return "0";
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Integer alterPersonalInfo(User user, String userId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            DataBaseOperate.alterPersonalInfo(user,userId,connection);
            return 200;
        } catch (SQLException e) {
            e.printStackTrace();
            return 500;
        }
    }

    @Override
    public Integer deleteUser(String userId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            DataBaseOperate.deleteUser(userId,connection);
            DataBaseOperate.deleteUserFile(userId,connection);
            DataBaseOperate.deletePowerFile(userId,connection);
            DataBaseOperate.deleteAutorizedUploadFile(userId,connection);
            connection.commit();
            return 200;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return 500;
        }
    }
}
