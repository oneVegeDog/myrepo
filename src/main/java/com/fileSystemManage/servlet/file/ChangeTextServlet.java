package com.fileSystemManage.servlet.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.Message;
import com.fileSystemManage.service.imple.FileImple;
import com.fileSystemManage.utils.QiNiuUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

@WebServlet("/changeText")
@MultipartConfig
public class ChangeTextServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part fileId = req.getPart("fileId");
        Part content = req.getPart("contentText");
        byte[] data = content.getInputStream().readAllBytes();
        System.out.println("文章内容是："+new String(data,"utf-8"));
        String fileIdStr = new String(fileId.getInputStream().readAllBytes(),"utf-8");
        System.out.println("覆盖的文章的id是："+fileIdStr);
        Message message = new FileImple().changeTextContent(fileIdStr,data);
        ObjectMapper om = new ObjectMapper();
        String messagejson = om.writeValueAsString(message);
        resp.getWriter().write(messagejson);
    }

}