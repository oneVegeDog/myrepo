package com.fileSystemManage.servlet.user.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.FileTreeNode;
import com.fileSystemManage.service.imple.FileImple;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/manager/getFileTreeById")
public class LookUserFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String jsonstr = new ObjectMapper().writeValueAsString(new FileImple().loadFileTree(userId));
        System.out.println(jsonstr);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(jsonstr);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}