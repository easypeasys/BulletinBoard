package controller;

import com.alibaba.fastjson.JSON;
import javafx.application.Application;
import model.Account;
import util.JDBCUtil;
import vo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class loginServlet extends HttpServlet {

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        Map<String,Object> map= new HashMap<>();
        String username =request.getParameter("username");
        String password =request.getParameter("password");
        String identity =request.getParameter("identity");
        String sql= "select * from account where username = '"+ username+"' and password = '" +password+"' and identity = "+identity;
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("cache-control", "no-cache");
        HttpSession session= request.getSession();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()){
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setIdentity(Integer.parseInt(rs.getString("identity")));
                account.setNickname(rs.getString("nickname"));
                session.setAttribute("username",account.getUsername());
                session.setAttribute("nickname",account.getNickname());
                session.setAttribute("userId",rs.getString("id"));
                session.setAttribute("identity", account.getIdentity());
                map.put("message","成功");
                if ("1".equals(rs.getString("identity"))){
                    map.put("url","./indexStu.jsp");
                } else if ("2".equals(rs.getString("identity"))){
                    map.put("url","./indexTea.jsp");
                }else {
                    map.put("url","./indexAdmin.jsp");
                }
                String s = JSON.toJSONString(map);
                PrintWriter out = response.getWriter();
                out.print(s);
                out.flush();
                out.close();
            }else {
                map.put("message","失败");
                String s = JSON.toJSONString(map);
                PrintWriter out = response.getWriter();
                out.print(s);
                out.flush();
                out.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtil.close(rs, stmt, conn);
        }

    }

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws java.io.IOException, ServletException {

        Map<String, Object> map = new HashMap<>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String identity = request.getParameter("identity");
        String sql = "select * from account where username = '" + username + "' and password = '" + password + "' and identity = " + identity;
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setIdentity(Integer.parseInt(rs.getString("identity")));
                account.setNickname(rs.getString("nickname"));
                session.setAttribute("username",account.getUsername());
                session.setAttribute("nickname",account.getNickname());
                session.setAttribute("userId",account.getId());
                session.setAttribute("identity", account.getIdentity());
                map.put("message", "成功");
                if ("1".equals(rs.getString("identity"))) {
                    map.put("url","./indexStu.jsp");
                } else if ("2".equals(rs.getString("identity"))) {
                    map.put("url","./indexTea.jsp");
                } else {
                    map.put("url","./indexAdmin.jsp");
                }
                String s = JSON.toJSONString(map);
                PrintWriter out = response.getWriter();
                out.print(s);
                out.flush();
                out.close();
            } else {
                map.put("message", "失败");
                String s = JSON.toJSONString(map);
                PrintWriter out = response.getWriter();
                out.print(s);
                out.flush();
                out.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, stmt, conn);
        }
    }
}
