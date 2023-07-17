package com.fileSystemManage.servlet.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.Message;
import com.fileSystemManage.service.imple.FileImple;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteFile")
public class DeleteNodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取参数
        String fileId = req.getParameter("fileId");
        Integer left = Integer.parseInt(req.getParameter("left"));
        Integer right = Integer.parseInt(req.getParameter("right"));
        String path = req.getParameter("path");
        String userId = (String)req.getSession().getAttribute("userId");
        path = req.getServletContext().getRealPath("")+"userFileStorage"+path;
        System.out.println(path);

        PrintWriter printWriter = resp.getWriter();
        //调用service删除数据库的文件信息，并且删除存在云端的文件
        Message message = new FileImple().deleteFile(fileId,left,right,userId);
        printWriter.write(new ObjectMapper().writeValueAsString(message));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}