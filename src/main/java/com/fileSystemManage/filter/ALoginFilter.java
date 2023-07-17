package com.fileSystemManage.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


/**
 * 修改文章时在操作数据库时加个userId做判断条件，并且确保userId只能从session里面取可以保证操作文件的是文件对应的登录的对应的用户
 * 但是这样做几乎每个操作文件的行为都要取userId,加一个fileter应该可以简化，但是本项目没有实现
 */
@WebFilter(urlPatterns = {"/html/*","/getUserInfo","/getFileTree","/preview","/newFolder","/renameFile","/deleteFile","/deleteUser","/changeText","/afterLogin/*"})
public class ALoginFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("login  filter go");
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        String userId = (String) session.getAttribute("userId");
        System.out.println("username="+username);
        System.out.println("userId="+userId);
        if(username==null||userId==null){
            res.sendRedirect("http://localhost:8080/FileManageSystem/index.html");
        }else{
            chain.doFilter(req,res);
        }
    }
}