package kaizone.songmaya.qingtian.servlet.page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置返回类型为json
        response.setContentType("application/json");
        //设置返回字符集
        response.setCharacterEncoding("utf-8");
        //输出对象
        PrintWriter writer = response.getWriter();
        //输出error消息
        writer.write("执行TestServlet内doGet方法成功!");
        writer.close();
    }
}
