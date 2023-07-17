package com.fileSystemManage.servlet.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.Message;
import com.fileSystemManage.service.imple.FileImple;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@WebServlet("/uploadFile")
@MultipartConfig
public class UploadFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = (String)req.getSession().getAttribute("userId");

        String fileName = "";
        String parentName = "";
        String parentId = "";
        Integer leftNum = null;
        byte[] data = null;
        System.out.println("访问到post");

        ObjectMapper om = new ObjectMapper();
        PrintWriter printWriter = resp.getWriter();
        Message message = new Message();
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setSizeThreshold(4*1024);
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        upload.setFileSizeMax(100*1024*1024);
        try {
            List<FileItem> list = upload.parseRequest(req);
            for(FileItem item:list){
                if(item.isFormField()){
                    String filedName = item.getFieldName();
                    String value = item.getString();
                    System.out.println(filedName+":"+value);
                    switch (filedName){
                        case "parentName":
                            parentName = value;
                            break;
                        case "parentId":
                            parentId = value;
                            break;
                        case "leftNum":
                            leftNum = Integer.parseInt(value);
                            break;
                        case "fileName":
                            fileName = value;
                    }
                }else{
                    long size = item.getSize();
                    if(size==0){
                        message.setStatue(401);
                        message.setDescription("文件为空");
                        printWriter.write(om.writeValueAsString(message));
                        return;
                    }else{
                        data = item.getInputStream().readAllBytes();
                    }
                }
            }
            System.out.println(fileName);
            String fileId = UUID.randomUUID().toString();
            new FileImple().upload(message,om,userId,fileId,fileName,parentName,parentId,leftNum,data);
            printWriter.write(om.writeValueAsString(message));
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }
}