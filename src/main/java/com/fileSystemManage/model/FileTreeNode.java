package com.fileSystemManage.model;

import java.util.ArrayList;

public class FileTreeNode {
    private String fileId;
    private String fileName;
    private ArrayList<FileTreeNode> sonList;//子节点
    private FileTreeNode parentNode;//父节点
    private String parentId;//父节点的id
    private String parentName;//父节点的名字
    private int left;
    private int right;
    private int isFolder;

    //构造函数
    public FileTreeNode(){}
    //没有父节点与子节点的形参，因为节点之间的关系，在获取到全部文件节点后再去指定
    public FileTreeNode(String fileid, String fileName, String parentId, String parentName, int left, int right, int isFolder) {
        this.fileId = fileid;
        this.fileName = fileName;
        this.parentId = parentId;
        this.parentName = parentName;
        this.left = left;
        this.right = right;
        this.isFolder = isFolder;
        sonList = new ArrayList<>();
    }
    public FileTreeNode(String fileid, String fileName, int left, int right, int isFolder) {
        this.fileId = fileid;
        this.fileName = fileName;
        this.left = left;
        this.right = right;
        this.isFolder = isFolder;
        sonList = new ArrayList<>();
    }
    //指定子节点的函数
    public void add(FileTreeNode sonNode){
        sonList.add(sonNode);
    }

    @Override
    public String toString() {
        return "FileTreeNode{" +
                "fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", sonList=" + sonList +
                ", parent=" + parentNode +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", left=" + left +
                ", right=" + right +
                ", isFolder=" + isFolder +
                '}';
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileTreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(FileTreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public ArrayList<FileTreeNode> getSonList() {
        return sonList;
    }

    public void setSonList(ArrayList<FileTreeNode> list) {
        this.sonList = list;
    }

    public int getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(int isFolder) {
        this.isFolder = isFolder;
    }

}
