package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding("utf-8");
        res.setCharacterEncoding("utf-8");
        res.setContentType("utf-8");
        HttpServletRequest request=(HttpServletRequest)req;
        HttpServletResponse response=(HttpServletResponse)res;
        HttpSession session = request.getSession();
        if(session.getAttribute("flag")==null){
            PrintWriter out = response.getWriter();
            out.print("<script type='text/javascript'>alert('非法访问，请登录！');" +
                    "location='/filter/index.jsp'; chartset='utf-8';</script>");

        }else{
            chain.doFilter(request, response);
        }


    }
    public void init(FilterConfig arg0) throws ServletException {

    }

}
