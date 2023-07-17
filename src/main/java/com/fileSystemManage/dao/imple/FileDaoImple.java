package com.fileSystemManage.dao.imple;

import com.fileSystemManage.utils.ConnectionUtil;
import com.fileSystemManage.dao.DataBaseOperate;
import com.fileSystemManage.dao.FileDao;
import com.fileSystemManage.model.FileTreeNode;
import com.fileSystemManage.model.Message;
import com.fileSystemManage.utils.QiNiuUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class  FileDaoImple implements FileDao {

    public Integer deleteFile(String fileId, Integer left, Integer right, String userId) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            connection.setAutoCommit(false);
            List<String> list = getFileIds(left,right,userId,connection);
            for(String id:list){
                QiNiuUtils.delete(id);
            }
            DataBaseOperate.deleteAuthorizedFiles(left,right,userId,connection);
            DataBaseOperate.deleteEnpowerFile(left,right,userId,connection);
            DataBaseOperate.deleteFiles(left,right,userId,connection);
            com.fileSystemManage.dao.DataBaseOperate.updateLeftAndRight(left,-(right-left+1),userId,connection);
            System.out.println("数据库执行成功");
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
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public FileTreeNode[] loadFileTree(String userId) {
        HashMap<String,FileTreeNode> map = new HashMap();
        ArrayList<FileTreeNode> list = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            PreparedStatement getFilesPs = connection.prepareStatement(DataBaseOperate.GETUSERFILESBYLEFT);
            getFilesPs.setString(1,userId);
            ResultSet rs = getFilesPs.executeQuery();
            while(rs.next()){
                String fileId = rs.getString("file_id");
                String fileName = rs.getString("file_name");
                String parentId = rs.getString("parent_id");
                String parentName = rs.getString("parent_name");
                Integer left = rs.getInt("leftnum");
                Integer right = rs.getInt("rightnum");
                Integer isFolder = rs.getInt("isFolder");
                FileTreeNode node = new FileTreeNode(fileId,fileName,parentId,parentName,left,right,isFolder);
                map.put(fileId,node);
                list.add(node);
            }
            return setRelationship(map,list);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //由于双向树在转为json字符串的时候会报错，所以这里就没有指定父节点关系，在前端再去指定
    public static FileTreeNode[] setRelationship(HashMap<String,FileTreeNode> map, ArrayList<FileTreeNode> list){
        //遍历list，指定节点之间的关系，并且返回一个只有根节点的数组
        FileTreeNode sonnode = null;
        FileTreeNode parentNode = null;
        for(int i = 1;i< list.size();i++){
            sonnode = list.get(i);
            String parentid = sonnode.getParentId();
            parentNode = map.get(parentid);
            parentNode.add(sonnode);
        }
        FileTreeNode[] root = new FileTreeNode[1];
        root[0] = list.get(0);
        return root;
    }

    @Override
    public FileTreeNode newFolder(String userId, String fileId, String fileName, String parentName, String parentId, int leftNum, int isFolder, int changevalue) {
        Connection connection = ConnectionUtil.getConnection();
        FileTreeNode newnode = null;
        try {
            if(checkRepeat(fileName,userId,parentId,connection)){
                return null;
            }
            connection.setAutoCommit(false);
            //修改用户的文件树的左右值
            DataBaseOperate.updateLeftAndRight(leftNum,2,userId,connection);
            //插入文件表
            com.fileSystemManage.dao.DataBaseOperate.insertFile(userId,fileId,fileName,parentName,parentId,leftNum,leftNum+1,1,connection);
            //修改完就提交
            connection.commit();
            //生成新节点
            newnode = new FileTreeNode(fileId,fileName,parentId,parentName,leftNum,leftNum+1,1);

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return newnode;
        }
    }

    @Override
    public Message rename(String newname, String fileId, String userId) {
        Message message = new Message();
        Connection connection = null;
        try{
            connection = ConnectionUtil.getConnection();
            if(checkRepeat(newname,userId,DataBaseOperate.getFatherId(fileId,userId,connection),connection)){
                message.setStatue(401);
                message.setDescription("文件名重复");
                return message;
            }
            connection.setAutoCommit(false);
            Integer statue = DataBaseOperate.renameFile(newname,fileId,userId,connection);
            statue = DataBaseOperate.ranameParentFile(newname,fileId,userId,connection);
            message.setStatue(statue);
            if(statue==200){
                message.setDescription("更新文件名成功");
                connection.commit();
            }else{
                message.setDescription("服务器出错");
                connection.rollback();
            }
        }catch (SQLException e){
            try {
                message.setStatue(500);
                message.setDescription("服务器出错");
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return message;
        }
    }

    @Override
    public boolean checkRepeat(String fileName,String userId,String parentId) {
        Connection connection = ConnectionUtil.getConnection();
        boolean flag = checkRepeat(fileName,userId,parentId,connection);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean checkRepeat(String fileName,String userId,String parentId,Connection connection) {
        List<String> list = DataBaseOperate.getAllSonsName(parentId,userId,connection);
        return list.contains(fileName);
    }

    @Override
    public List<String> getFileIds(Integer left, Integer right, String userId) {
        Connection connection = ConnectionUtil.getConnection();
        return getFileIds(left,right,userId,connection);
    }

    @Override
    public List<String> getFileIds(Integer left, Integer right, String userId, Connection connection) {
        List<String> list = null;
        try {
            list = DataBaseOperate.getFileIds(connection,left,right,userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public FileTreeNode upload(String userId, String fileId, String fileName, String parentName, String parentId, int leftNum,byte[] data) {
        Connection connection = ConnectionUtil.getConnection();
        FileTreeNode fileTreeNode =null;
        try {
            connection.setAutoCommit(false);
            DataBaseOperate.updateLeftAndRight(leftNum,2,userId,connection);
            DataBaseOperate.insertFile(userId,fileId,fileName,parentName,parentId,leftNum,leftNum+1,0,connection);
            QiNiuUtils.uploadByByte(fileId,data);
            fileTreeNode = new FileTreeNode();
            fileTreeNode.setFileId(fileId);
            fileTreeNode.setFileName(fileName);
            fileTreeNode.setIsFolder(0);
            fileTreeNode.setLeft(leftNum);
            fileTreeNode.setRight(leftNum+1);
            fileTreeNode.setParentName(parentName);
            fileTreeNode.setParentId(parentId);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return fileTreeNode;
        }
    }
}
