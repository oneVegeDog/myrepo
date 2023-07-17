package com.fileSystemManage.service.imple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.dao.imple.FileDaoImple;
import com.fileSystemManage.model.FileTreeNode;
import com.fileSystemManage.model.Message;
import com.fileSystemManage.service.FileInterface;
import com.fileSystemManage.utils.QiNiuUtils;

import java.io.File;
import java.sql.Connection;
import java.util.List;

public class FileImple implements FileInterface {
    @Override
    public Message rename(String newname,String userId,String fileId){
        return new FileDaoImple().rename(newname,fileId,userId);
    }

    @Override
    public Message upload(Message message,ObjectMapper om, String userId, String fileId, String fileName, String parentName, String parentId, int leftNum, byte[] data) {
        FileTreeNode fileTreeNode = new FileDaoImple().upload(userId,fileId,fileName,parentName,parentId,leftNum,data);
        if(fileTreeNode==null){
            message.setStatue(500);
            message.setDescription("上传到数据库和云端时出现错误");
        }else{
            try {
                message.setJsonstr(om.writeValueAsString(fileTreeNode));
                message.setStatue(200);
                message.setDescription("成功");
            } catch (JsonProcessingException e) {
                message.setStatue(500);
                message.setDescription("文件节点转换错误");
                e.printStackTrace();
            }
        }
        return message;
    }

    @Override
    public Message changeTextContent(String fileId, byte[] data) {
        Message message = new Message();

        Integer statue = QiNiuUtils.changeContentText(fileId,data);

        message.setStatue(statue);

        return message;
    }

    @Override
    public Message deleteFile(String fileId, Integer left, Integer right, String userId) {
        Message message = new Message();
        FileDaoImple fileDaoImple = new FileDaoImple();
        message.setStatue(fileDaoImple.deleteFile(fileId,left,right,userId));
        return message;
    }

    @Override
    public FileTreeNode[] loadFileTree(String userId) {
        return new FileDaoImple().loadFileTree(userId);
    }

    @Override
    public FileTreeNode newFolder(Message message,String userId, String fileId, String fileName, String parentName, String parentId, Integer leftNum) {
        FileTreeNode fileTreeNode = new FileDaoImple().newFolder(userId,fileId,fileName,parentName,parentId,leftNum,1,2);
        if(fileTreeNode==null){
            message.setStatue(401);
        }else{
            message.setStatue(200);
        }
        return  fileTreeNode;
    }
}
