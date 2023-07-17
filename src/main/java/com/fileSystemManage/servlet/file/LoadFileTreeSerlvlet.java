package com.fileSystemManage.servlet.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.dao.imple.FileDaoImple;
import com.fileSystemManage.service.imple.FileImple;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/getFileTree")
public class LoadFileTreeSerlvlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("访问到");
        HttpSession session = req.getSession();
        String userId = (String) session.getAttribute("userId");
        //获取到文件树的根节点，然后转换成json字符串
        String jsonstr = new ObjectMapper().writeValueAsString(new FileImple().loadFileTree(userId));
        System.out.println(jsonstr);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(jsonstr);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}