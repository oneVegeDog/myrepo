package com.fileSystemManage.servlet;

import com.fileSystemManage.utils.ConnectionUtil;
import com.fileSystemManage.dao.DataBaseOperate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {


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

        Connection connection = ConnectionUtil.getConnection();
        try {
            //以用户名为关键词，查询用户的密码和用户名以及状态
            PreparedStatement ps = connection.prepareStatement(DataBaseOperate.QUERYUSERPASSWORDANDIDANDSTATUEANDMENUPATH);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            //由于用户名唯一，所以rs只有0和1两个可能
            if(rs.next()){
                //用户存在，再判断密码
                if(password.equals(rs.getString("password"))){
                    if(1==rs.getInt("user_statue")){
                        //输入密码和查询到的密码相同则返回用户对应的页面路径
                        printWriter.write(rs.getString("menu_path"));
                        //存储session，作为用户登录的判断依据
                        HttpSession session = req.getSession();
                        session.setAttribute("username",username);
                        session.setAttribute("userId",rs.getString("user_id"));
                        session.setAttribute("role",rs.getInt("role_id"));
                    }else{
                        printWriter.write("3");
                    }
                }else{
                    //则返回密码错误的标志
                    printWriter.write("1");
                }
            }else{
                //用户名不存在则返回用户不存在的标志
                printWriter.write("0");
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