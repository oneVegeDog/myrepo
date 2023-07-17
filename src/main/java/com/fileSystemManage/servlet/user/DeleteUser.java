package com.fileSystemManage.servlet.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.Message;
import com.fileSystemManage.service.imple.UserImple;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/deleteUser")
public class DeleteUser extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId= (String) req.getSession().getAttribute("userId");
        Message message = new UserImple().deleteUser(userId);
        ObjectMapper om = new ObjectMapper();
        resp.getWriter().write(om.writeValueAsString(message));
    }
}
