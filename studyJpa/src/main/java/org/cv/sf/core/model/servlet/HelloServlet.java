package org.cv.sf.core.model.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login.action")
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.service(req, resp);
        System.out.println("3.处理请求");
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.处理请求过程中的乱码
        request.setCharacterEncoding("utf-8");
        //2.处理响应过程中的乱码
        response.setCharacterEncoding("utf-8");
        //3.设置浏览器页面编码格式
        response.setContentType("text/html;charset=utf-8");
        //4.

		/*String name  = this.getInitParameter("name");
		System.out.println("name:"+name);*/
        //Servlet获取上下文参数
        String pwd =
                this.getServletContext().
                        getInitParameter("password");
        System.out.println("pwd:" + pwd);
        String uri = request.getRequestURI();
        StringBuffer url = request.getRequestURL();
        System.out.println("uri:" + uri);
        System.out.println("url:" + url);
        String realPath = request.getRealPath("LoginAction.java");
        System.out.println("realPath:" + realPath);

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello Servlet</title>");
        out.println
                ("<meta http-equiv='content-type' content='text/html;charset=utf-8'/>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>你好,Servlet！</h2>");
        out.println("<form action='login.action' method='post'>");
        out.println("用户名：<input type='text' name='username'/><br/>");
        out.println("密码：<input type='password' name='pwd'/>");
        out.println("<input type='submit' value='登录'/>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("销毁");

    }

}
