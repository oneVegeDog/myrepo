package com.fileSystemManage.dao;

import com.fileSystemManage.model.User;
import com.fileSystemManage.servlet.user.DeleteUser;
import com.mysql.cj.jdbc.ConnectionWrapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseOperate {
    //查询用户id的语句
    public static final String QUERYUSERID = "select user_id from users where username=?";
    //查询用户id，密码，状态和菜单路径
    public static final String QUERYUSERPASSWORDANDIDANDSTATUEANDMENUPATH = "select users.password,users.user_id,users.user_statue,user_role.role_id,menus.menu_path from users inner join user_role on users.user_id=user_role.user_id inner join menu_role on menu_role.role_id=user_role.role_id inner join menus on menus.menu_id=menu_role.menu_id where username=?";

    //查询用户相关信息的语句
    public static final String QUERYUSERINFO = "select user_id,register_date,user_statue from users where username=?";

    //获取到一个用户的所有文件
    public static final String GETUSERFILESBYLEFT = "select file_id,file_name,parent_name,parent_id,leftnum,rightnum,isFolder from files where user_id=? ORDER BY leftnum";

    //插入user表的语句
    public static final String INSERTUSER ="insert into users(username,user_id,password) value(?,?,?)";
    //往users里面插数据
    public static int insertUser(String username,String userId,String password,Connection connection) throws SQLException {
        PreparedStatement insertUserPs = null;
        try {
            insertUserPs = connection.prepareStatement(DataBaseOperate.INSERTUSER);
            insertUserPs.setString(1,username);
            insertUserPs.setString(2,userId);
            insertUserPs.setString(3,password);
            insertUserPs.executeUpdate();
            return 1;
        } catch (SQLException e) {
            throw e;
        }finally {
            try {
                insertUserPs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    //增删文件，修改文件树节点的左右值
    public static final String UPDATELEFT = "update files set leftnum=leftnum+? where leftnum>=? AND user_id=?";
    public static final String UPDATERIGHT = "update files set rightnum=rightnum+? where rightnum>=? AND user_id=?";
    //更新文件树的左右值的函数
    public static int updateLeftAndRight(int left, int changevalue, String userId, Connection connection) throws SQLException {
        try {
            System.out.println("开始更新左值");
            //更新左值
            PreparedStatement preparedStatementLeft = connection.prepareStatement(UPDATELEFT);
            preparedStatementLeft.setInt(1,changevalue);
            preparedStatementLeft.setInt(2,left);
            preparedStatementLeft.setString(3,userId);
            preparedStatementLeft.executeUpdate();
            preparedStatementLeft.close();
            //更新右值
            PreparedStatement preparedStatementRight = connection.prepareStatement(UPDATERIGHT);
            preparedStatementRight.setInt(1,changevalue);
            preparedStatementRight.setInt(2,left);
            preparedStatementRight.setString(3,userId);
            preparedStatementRight.executeUpdate();
            preparedStatementRight.close();
            System.out.println("更新完毕");
            return 1;
        } catch (SQLException e) {
            throw e;
        }
    }


    //插入file表的语句
    public static final String INSERTFILE = "insert into files(user_id,file_id,file_name,parent_name,parent_id,leftnum,rightnum,isFolder) value(?,?,?,?,?,?,?,?)";
    //往文件表里插记录
    public static int insertFile(String userId,String fileId,String fileName,String parentName,String parentId,int leftNum,int rightNum,int isFolder,Connection connection) throws SQLException {
        try {
            PreparedStatement insertFilePs = connection.prepareStatement(INSERTFILE);
            insertFilePs.setString(1, userId);
            insertFilePs.setString(2, fileId);
            insertFilePs.setString(3, fileName);
            insertFilePs.setString(4, parentName);
            insertFilePs.setString(5, parentId);
            insertFilePs.setInt(6, leftNum);
            insertFilePs.setInt(7, rightNum);
            insertFilePs.setInt(8, isFolder);
            insertFilePs.executeUpdate();
            insertFilePs.close();
            return 1;
        }catch (SQLException e){
            throw e;
        }
    }

    //删除files表里的文件
    public static final String DELETEFILE = "delete from files where leftnum>=? AND rightnum<=? AND user_id=?";
    //删除files表里的数据
    public static int deleteFiles(int left,int right,String userId,Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETEFILE);
            preparedStatement.setInt(1,left);
            preparedStatement.setInt(2,right);
            preparedStatement.setString(3,userId);
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            throw e;
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //删除authorizeduploadfiles的文件
    public static final String DELETEAUTHORIZEDFILE = "delete authorizeduploadfiles from authorizeduploadfiles,files where files.leftnum>? AND files.rightnum<? AND files.user_id=? AND files.file_id=authorizeduploadfiles.file_id";
    //删除被授权上传的文件表里的数据
    public static int deleteAuthorizedFiles(int left,int right,String userId,Connection connection) throws SQLException {
        try {
            PreparedStatement authorizedPreparedStatement = connection.prepareStatement(DELETEAUTHORIZEDFILE);
            authorizedPreparedStatement.setInt(1,left);
            authorizedPreparedStatement.setInt(2,right);
            authorizedPreparedStatement.setString(3,userId);
            authorizedPreparedStatement.executeUpdate();
            authorizedPreparedStatement.close();
            return 1;
        } catch (SQLException e) {
            throw e;
        }
    }

    //删除file_power里的文件
    public static final String DELETEPOWERFILE = "delete file_power from file_power,files where files.leftnum>? AND files.rightnum<? AND files.user_id=? AND files.file_id=file_power.file_id";
    //删除授权文件的数据
    public static int deleteEnpowerFile(int left,int right,String userId,Connection connection) throws SQLException {
        try {
            PreparedStatement filepowerPreparedStatement = connection.prepareStatement(DELETEPOWERFILE);
            filepowerPreparedStatement.setInt(1,left);
            filepowerPreparedStatement.setInt(2,right);
            filepowerPreparedStatement.setString(3,userId);
            filepowerPreparedStatement.executeUpdate();
            filepowerPreparedStatement.close();
            return 1;
        } catch (SQLException e) {
            throw e;
        }

    }


    //指定用户角色
    public static final String DESIGNROLE = "insert into user_role(user_id,role_id) value(?,?)";
    //往user_role表里插数据
    public static int designateRole(String userId,int roleId,Connection connection) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DESIGNROLE);
            preparedStatement.setString(1,userId);
            preparedStatement.setInt(2,roleId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return 1;
        }catch (SQLException e) {
            throw e;
        }
    }

    //更新文件名
    public static final String RENAMEFILE = "update files set file_name = ? where file_id=? and user_id=?";
    //重命名文件
    public static Integer renameFile(String newname,String fileId,String userId,Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(RENAMEFILE);
            preparedStatement.setString(1,newname);
            preparedStatement.setString(2,fileId);
            preparedStatement.setString(3,userId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return 200;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static final String RENAMEPARENTFILE = "update files set parent_name = ? where parent_id=? and user_id=?";
    //重命名父文件名字
    public static Integer ranameParentFile(String newname,String fileId,String userId,Connection connection){
        PreparedStatement preparedStatement = null;
        Integer flag = null;
        try {
            preparedStatement = connection.prepareStatement(RENAMEPARENTFILE);
            preparedStatement.setString(1,newname);
            preparedStatement.setString(2,fileId);
            preparedStatement.setString(3,userId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            flag = 200;
        } catch (SQLException e) {
            flag=500;
            throw e;
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return flag;
        }
    }

    //根据父id查询子文件名
    public static final String QUERYSONFILE = "select file_name from files where parent_id=? AND user_id=?";
    //得到所有子文件或目录的名字
    public static List<String> getAllSonsName(String parentId,String user_id,Connection connection){
        PreparedStatement preparedStatement = null;
        List<String> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(QUERYSONFILE);
            preparedStatement.setString(1,parentId);
            preparedStatement.setString(2,user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                list.add(resultSet.getString("file_name"));
            }
        } catch (SQLException e) {
            throw e;
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }
    }

    //根据子id查询父id
    public static final String QUERYFATHERID = "select parent_id from files where file_id=? AND user_id=?";
    //获取父节点id
    public static String getFatherId(String fileId,String userId,Connection connection){
        PreparedStatement preparedStatement =null;
        String str = null;
        try {
            preparedStatement = connection.prepareStatement(QUERYFATHERID);
            preparedStatement.setString(1,fileId);
            preparedStatement.setString(2,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                str = resultSet.getString("parent_id");
            }else{
                str = "";
            }
        } catch (SQLException e) {
            throw e;
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return str;
        }
    }


    //根据左右值获取一定范围内的非文件夹的文件的id
    public static final String GETFILEIDS = "select file_id from files where leftnum>=? AND rightnum<=? AND user_id=? AND isFolder=0";
    public static List<String> getFileIds(Connection connection,Integer left,Integer right,String userId) throws SQLException {
        PreparedStatement preparedStatement=null;
        List<String> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(GETFILEIDS);
            preparedStatement.setInt(1,left);
            preparedStatement.setInt(2,right);
            preparedStatement.setString(3,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                list.add(resultSet.getString("file_id"));
            }
        } catch (SQLException e) {
            throw e;
        }finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw e;
            }
            return list;
        }
    }


    //更新用户信息
    public static final String UPDATEPERSONALINFO = "update users set username = ? where user_id=?";
    public static void alterPersonalInfo(User user, String userId, Connection connection) throws SQLException {
        String username = user.getUsername();
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement(UPDATEPERSONALINFO);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,userId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }


    //管理员改变用户状态
    public static final String UPDATEUSERSTATUE = "update users set user_statue=? where user_id=?";
    public static void updateUserStatue(Connection connection,Integer statue,String userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATEUSERSTATUE);
        preparedStatement.setInt(1,statue);
        preparedStatement.setString(2,userId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    //分页获取用户的信息
    public static final Integer ONEPAGENUM = 10;
    public static  final String GETUSERS = "select user_id,register_date,user_statue,username from users limit ?,?";
    public static List<User> getUsers(Connection connection,Integer pageNum) throws SQLException {
        List<User> list = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(GETUSERS);
        preparedStatement.setInt(1,(pageNum-1)*ONEPAGENUM);
        preparedStatement.setInt(2,ONEPAGENUM);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User user = new User(resultSet.getString("username"),resultSet.getString("user_id"),resultSet.getDate("register_date").toString(),resultSet.getInt("user_statue"));
            list.add(user);
        }
        resultSet.close();
        preparedStatement.close();
        return list;
    }

    //获取用户总数
    public static final String GETUSERNUM = "select count(user_id) as totalNum from users";
    public static Integer getUsersTotalNum(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(GETUSERNUM);
        rs.next();
        Integer totalNum = rs.getInt("totalNum");
        statement.close();
        return totalNum;
    }

    //删除用户
    public static final String DELETEUSER = "delete from users where user_id=?";
    public static void deleteUser(String userId,Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(DELETEUSER);
        preparedStatement.setString(1,userId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    //删除用户在files表里的文件
    public static final String DELETEUSERFILE = "delete from files where user_id=?";
    public static void deleteUserFile(String userId,Connection connection) throws SQLException {
        deleteUserToDeleteFile(userId,connection,DELETEUSERFILE);
    }

    //删除文件权限表里的文件
    public static final String DELETEUSERPOWERFILE = "delete from file_power where enpower_user_id=?";
    public static void deletePowerFile(String userId,Connection connection) throws SQLException {
        deleteUserToDeleteFile(userId,connection,DELETEUSERPOWERFILE);
    }

    //删除被授权用户上传的文件
    public static final String DELETEAUTHORIZEDUPLOADFILE = "delete from authorizeduploadfiles where enpower_user_id=?";
    public static void deleteAutorizedUploadFile(String userId,Connection connection) throws SQLException {
        deleteUserToDeleteFile(userId,connection,DELETEAUTHORIZEDUPLOADFILE);
    }

    public static void deleteUserToDeleteFile(String userId,Connection connection,String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,userId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
}
