package com.fileSystemManage.servlet.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.Message;
import com.fileSystemManage.service.imple.FileImple;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/renameFile")
public class RenameFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newname = req.getParameter("newname");
        String fileId = req.getParameter("fileId");
        String userId = (String)req.getSession().getAttribute("userId");

        //改变磁盘存储的文件名或者云存储的对象名





        Message message = null;
        ObjectMapper om = new ObjectMapper();
        //调用service层，获取响应状态码
        message = new FileImple().rename(newname,userId,fileId);
        PrintWriter writer = resp.getWriter();
        writer.write(om.writeValueAsString(message));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}