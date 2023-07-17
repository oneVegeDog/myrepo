package com.fileSystemManage.servlet.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileSystemManage.model.Message;
import com.fileSystemManage.service.imple.FileImple;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/newFolder")
public class NewFolderServlet extends HttpServlet {
    @Override
    protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userId = (String) session.getAttribute("userId");
        //String username = (String)session.getAttribute("username");
        String parentId = req.getParameter("parentId");
        String parentName = req.getParameter("parentName");
        Integer left = Integer.parseInt(req.getParameter("left"));
        String fileName = req.getParameter("fileName");
//        String path = req.getParameter("path");
//        path=req.getServletContext().getRealPath("")+"userFileStorage"+path+"/"+fileName;
//        System.out.println(path);
//        File folder = new File(path);

        PrintWriter printWriter = resp.getWriter();
        Message message = new Message();
        ObjectMapper om = new ObjectMapper();

        //不存在该文件夹则创建并且在修改文件表中该用户其他文件的左右值之后向文件表插记录
        String fileId = UUID.randomUUID().toString();

        //调用service层方法，并且将得到的FileTreeNode对象转换成json
        String jsonstr = om.writeValueAsString(new FileImple().newFolder(message,userId,fileId,fileName,parentName,parentId,left));
        //设置返回对象的json字符串
        message.setJsonstr(jsonstr);
        printWriter.write(om.writeValueAsString(message));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}