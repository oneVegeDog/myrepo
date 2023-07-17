package com.fileSystemManage.servlet.user.manager;

import com.fileSystemManage.dao.DataBaseOperate;
import com.fileSystemManage.service.imple.ManagerImple;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/getTotalPageNum")
public class GetUserTotalPageNum extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        Integer totalUsersNum = new ManagerImple().getUsersTotalNum();
        Integer totalPageNum = (int)Math.ceil(totalUsersNum/(double) DataBaseOperate.ONEPAGENUM);
        System.out.println(totalPageNum);
        printWriter.write(totalPageNum+"");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}