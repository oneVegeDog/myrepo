package com.fileSystemManage.servlet.user.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.Message;
import com.fileSystemManage.service.imple.ManagerImple;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/manager/getUsers")
public class GetUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer pageNum = Integer.parseInt(req.getParameter("pageNum"));
        ObjectMapper om = new ObjectMapper();
        PrintWriter printWriter = resp.getWriter();
        Message message = new ManagerImple().getUsers(pageNum,om);
        printWriter.write(om.writeValueAsString(message));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}