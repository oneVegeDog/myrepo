package com.fileSystemManage.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter({"/html/manager/*","/manager/*"})
public class BManagerFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException, IOException {
        System.out.println("manager filter go");
        Integer role = (Integer)req.getSession().getAttribute("role");
        System.out.println("role ="+role);
        if (role!=2) {
            res.sendRedirect("http://localhost:8080/FileManageSystem/html/user.html");
        }else{
            chain.doFilter(req,res);
        }
    }
}