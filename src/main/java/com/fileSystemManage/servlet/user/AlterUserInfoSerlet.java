package com.fileSystemManage.servlet.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.Message;
import com.fileSystemManage.model.User;
import com.fileSystemManage.service.UserInterface;
import com.fileSystemManage.service.imple.UserImple;
import com.mysql.cj.util.Base64Decoder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.Base64;

@WebServlet("/alterUserInfo")
@MultipartConfig
public class AlterUserInfoSerlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("访问到get");
//        String userJson = req.getParameter("userJson");
//        System.out.println(userJson);
//        Message message = new Message();
//        ObjectMapper om = new ObjectMapper();
//
//        User user = om.readValue(userJson,User.class);
//        message.setStatue(new UserImple().alterPersonalInfo(user,(String)req.getSession().getAttribute("userId")));
//
//        resp.getWriter().write(om.writeValueAsString(message));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("访问到post");
        String userJson = req.getParameter("userJson");
        System.out.println(userJson);
        Message message = new Message();
        ObjectMapper om = new ObjectMapper();

        User user = om.readValue(userJson,User.class);
        message.setStatue(new UserImple().alterPersonalInfo(user,(String)req.getSession().getAttribute("userId")));

        resp.getWriter().write(om.writeValueAsString(message));
    }
}