package com.fileSystemManage.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/getMap")
public class Test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper om = new ObjectMapper();
        HashMap<String,Object> map = new HashMap<>();
        map.put("user",new ArrayList<User>(List.of(new User[]{new User("aa","123","2024",1),new User("bb","345","2022",1)})));
        map.put("age",34);
        resp.getWriter().write(om.writeValueAsString(map));
        Class clas = User.class;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}