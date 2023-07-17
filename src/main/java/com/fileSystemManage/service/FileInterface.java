package com.fileSystemManage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.FileTreeNode;
import com.fileSystemManage.model.Message;

import java.sql.Connection;

public interface FileInterface {
    Message deleteFile(String fileId, Integer left, Integer right, String userId);

    FileTreeNode[] loadFileTree(String userId);

    FileTreeNode newFolder(Message message,String userId, String fileId, String fileName, String parentName, String parentId, Integer leftNum);

    Message rename(String newname,String userId,String fileId);

    Message upload(Message message,ObjectMapper om, String userId, String fileId, String fileName, String parentName, String parentId, int leftNum, byte[] data);

    Message changeTextContent(String fileId,byte[] data);
}
