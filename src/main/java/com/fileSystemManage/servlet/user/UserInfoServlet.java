package com.fileSystemManage.servlet.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.User;
import com.fileSystemManage.service.imple.UserImple;
import com.fileSystemManage.service.UserInterface;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/getUserInfo")
public class UserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        PrintWriter printWriter = resp.getWriter();
        UserInterface userImple = new UserImple();
        User user = userImple.getPersonalInfo(username);
        ObjectMapper om = new ObjectMapper();
        String userJson = om.writeValueAsString(user);
        printWriter.write(userJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}