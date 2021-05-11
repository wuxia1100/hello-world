package org.cv.sf.core.model.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/servlet/login")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.处理请求过程中的乱码
        request.setCharacterEncoding("utf-8");
        //2.处理响应过程中的乱码
        response.setCharacterEncoding("utf-8");
        //3.设置浏览器页面编码格式
        response.setContentType("text/html;charset=utf-8");

        //接受表单传递的参数
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        //输出到控制台
        //System.out.println("username="+username+",pwd="+pwd);
        //输出到浏览器
        PrintWriter out = response.getWriter();
        //out.println("用户名:"+username);
        //out.println("密码:"+pwd);
        String pw =
                this.getServletContext().
                        getInitParameter("password");
        System.out.println("pw:"+pw);

        if(username.equals("admin")&&pwd.equals("888"))
        {
            out.println(username+"<font size='10'>登录成功</font>");
        }
        else
        {
            //out.println("<h2>用户登录失败</h2>");
            //页面跳转
            response.sendRedirect("/servlet/hello");
            //request.getRequestDispatcher("hello.action").forward(request, response);

            /*redirect和forward跳转的区别
             * 1).浏览器地址栏：
             * forward跳转：地址栏不会显示转向后的地址
             * redirect跳转：地址栏会显示转向后的地址
             * forward:
             * http://localhost:8080/2015-12-15-servlet/hello.action
             * http://localhost:8080/2015-12-15-servlet/login.action
             * redirect:
             * http://localhost:8080/2015-12-15-servlet/hello.action
             * http://localhost:8080/2015-12-15-servlet/hello.action
             * 2).跳转发生的位置：
             *     forward跳转发生在服务器端,即服务器跳转
             *     redirect跳转发生在浏览器端,即客户端跳转
             * 3).跳转的范围：
             *     redirect可以跳转到其他项目中。
             */

        }
        out.flush();
        out.close();
    }
}
