package com.fileSystemManage.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter()
public class TestFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("访问到过滤器");
        try {
            res.getWriter().write("401");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}