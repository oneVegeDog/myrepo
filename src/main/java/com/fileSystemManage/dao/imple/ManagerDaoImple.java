package com.fileSystemManage.dao.imple;

import com.fileSystemManage.dao.DataBaseOperate;
import com.fileSystemManage.dao.ManagerDao;
import com.fileSystemManage.model.User;
import com.fileSystemManage.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class   ManagerDaoImple implements ManagerDao {
    @Override
    public Integer updateUserStatue(String userId, Integer statue) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            DataBaseOperate.updateUserStatue(connection,statue,userId);
            connection.commit();
            return 200;
        } catch (SQLException e) {
            try{
                connection.rollback();
            }catch(SQLException ee){
                ee.printStackTrace();
            }
            e.printStackTrace();
            return 500;
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getUsers(Integer pageNum) {
        List<User> list = null;
        Connection connection = ConnectionUtil.getConnection();
        try{
            list = DataBaseOperate.getUsers(connection,pageNum);
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }
    }

    @Override
    public Integer getUsersTotalNum() {
        Connection connection = ConnectionUtil.getConnection();
        Integer totalPageNum = 0;
        try {
            totalPageNum = DataBaseOperate.getUsersTotalNum(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totalPageNum;
    }
}
