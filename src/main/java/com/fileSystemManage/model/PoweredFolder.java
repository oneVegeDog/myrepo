package com.fileSystemManage.model;

public class PoweredFolder {

    private Integer authorizedUserId;
    private Integer fileId;
    private String fileName;
    private Integer enpowerUserId;
    private Integer left;
    private Integer right;

    public PoweredFolder(Integer authorizedUserId,Integer fileid, String fileName, Integer enpowerUserId, Integer left, Integer right) {
        this.authorizedUserId = authorizedUserId;
        this.fileId = fileid;
        this.fileName = fileName;
        this.enpowerUserId = enpowerUserId;
        this.left = left;
        this.right = right;
    }
    public Integer getAuthorizedUserId() {
        return authorizedUserId;
    }

    public void setAuthorizedUserId(Integer authorizedUserId) {
        this.authorizedUserId = authorizedUserId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getEnpowerUserId() {
        return enpowerUserId;
    }

    public void setEnpowerUserId(Integer enpowerUserId) {
        this.enpowerUserId = enpowerUserId;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }
}
