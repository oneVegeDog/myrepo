package com.fileSystemManage.servlet.user.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.Message;
import com.fileSystemManage.service.imple.ManagerImple;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/manager/changeUserStatue")
public class ManageUsersStatueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        Integer statue = Integer.parseInt(req.getParameter("statue"));
        System.out.println(statue);
        System.out.println(userId);
        ObjectMapper om = new ObjectMapper();
        PrintWriter printWriter = resp.getWriter();
        Message message = new ManagerImple().updateUserStatue(userId,statue);
        printWriter.write(om.writeValueAsString(message));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}