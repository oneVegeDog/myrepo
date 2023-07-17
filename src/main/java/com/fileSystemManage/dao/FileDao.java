package com.fileSystemManage.dao;

import com.fileSystemManage.model.FileTreeNode;
import com.fileSystemManage.model.Message;

import java.sql.Connection;
import java.util.List;

public interface FileDao {
    //删除文件
    Integer deleteFile(String fileId,Integer left,Integer right,String userId );
    //加载文件树
    FileTreeNode[] loadFileTree(String userId);
    //新建文件夹
    FileTreeNode newFolder(String userId, String fileId, String fileName, String parentName, String parentId, int leftNum, int isFolder,int changevalue);
    //重命名文件
    Message rename(String newname, String fileId, String userId);
    //查照同一目录下的文件是否重名
    boolean checkRepeat(String fileName,String userId,String parentId);
    boolean checkRepeat(String fileName,String userId,String parentId,Connection connection);
    //获取到左右值之间的所有文件的id
    List<String> getFileIds(Integer left,Integer right,String userId);
    List<String> getFileIds(Integer left,Integer right,String userId,Connection connection);
    //上传文件
    //dao层应该只处理数据库的，上传文件和新建文件夹对数据库的操作一摸一样，但是由于这个现在的实现里，dao层不仅要处理数据库，还要实现云端操作，后面做项目要改。
    //而这个项目之所以云端和数据库操作耦合了，主要是云端删除文件时，需要查询数据库获得一对左右值之内的所有非文件夹的文件的id
    //但是不想在一个请求里建立两个数据库连接，所以就将dao层和云端操作耦合起来了。
    FileTreeNode upload(String userId, String fileId, String fileName, String parentName, String parentId, int leftNum,byte[] data);
}
