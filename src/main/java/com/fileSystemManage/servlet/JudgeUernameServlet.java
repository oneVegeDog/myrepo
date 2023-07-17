package com.fileSystemManage.servlet;

import com.fileSystemManage.utils.ConnectionUtil;
import com.fileSystemManage.dao.DataBaseOperate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet("/judgeUserName")
public class JudgeUernameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String temp_username = req.getParameter("username");
        PrintWriter printWriter = resp.getWriter();
        Connection connection = ConnectionUtil.getConnection();
        try {
            //直接以用户名查询users表
            PreparedStatement ps = connection.prepareStatement(DataBaseOperate.QUERYUSERID);
            ps.setString(1,temp_username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                //用户名已存在则返回0,代表该用户不能注册
                printWriter.write("0");
            }else{
                //不存在就返回1，代表该用户可注册
                printWriter.write("1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
