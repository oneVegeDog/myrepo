package com.fileSystemManage.servlet;

import com.fileSystemManage.service.imple.UserImple;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    //有一个bug是如果一个用户通过了用户名重名的检测，另一个用户刚注册成功，这个时候两个用户的用户名可能是相同的
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        PrintWriter printWriter = resp.getWriter();
        if(!username.matches("\\w{2,10}")){
            printWriter.write("6");
            return;
        }
        if(!password.matches("\\w{6,20}")){
            printWriter.write("7");
            return;
        }

        //生成一个随机字符串，作为user的id
        String userId = UUID.randomUUID().toString();
        //调用service层的方法，得到注册操作的状态码
        String data = new UserImple().register(username,password,userId);
        //注册成功data就不会是0，由于注册成功后直接就是登录状态了，所以要存session
        if (!"0".equals(data)){
            HttpSession session = req.getSession();
            session.setAttribute("username",username);
            session.setAttribute("userId",userId);
            session.setAttribute("role",1);//因为只有普通用户才能注册，而管理员是系统指定的，所以注册的肯定是1
        }
        printWriter.write(data);

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}