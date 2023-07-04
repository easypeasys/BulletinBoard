package controller;

import com.alibaba.fastjson.JSON;
import model.Account;
import util.JDBCUtil;
import vo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register")
public class registerServlet extends HttpServlet {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws java.io.IOException, ServletException {

        String username =request.getParameter("username");
        String password =request.getParameter("password");
        String nickname =request.getParameter("nickname");
        String sql1= "insert into account(username,password,identity,nickname) values(?,?,?,?)";
        Map<String,Object> map = new HashMap<>();
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("cache-control", "no-cache");
        try {
            conn = JDBCUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,"1");
            ps.setString(4,nickname);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            int studentId = 0;
            while (rs.next()){
                studentId=rs.getInt(1);
            }
            String sql2 = "insert into student(studentId,nickname) values(?,?)";
            ps = conn.prepareStatement(sql2);
            ps.setInt(1,studentId);
            ps.setString(2,nickname);
            ps.executeUpdate();
            map.put("success","成功");
            String s = JSON.toJSONString(map);
            PrintWriter out = response.getWriter();
            out.print(s);
            out.flush();
            out.close();
            JDBCUtil.close(rs, ps, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws java.io.IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String sql1 = "insert into account(username,password,identity,nickname) values(?,?,?,?)";
        Map<String, Object> map = new HashMap<>();
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("cache-control", "no-cache");
        try {
            conn = JDBCUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, "1");
            ps.setString(4, nickname);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            int studentId = 0;
            while (rs.next()) {
                studentId = rs.getInt(1);
            }
            String sql2 = "insert into student(studentId,nickname) values(?,?)";
            ps = conn.prepareStatement(sql2);
            ps.setInt(1, studentId);
            ps.setString(2, nickname);
            ps.executeUpdate();
            map.put("success", "成功");
            String s = JSON.toJSONString(map);
            PrintWriter out = response.getWriter();
            out.print(s);
            out.flush();
            out.close();
            JDBCUtil.close(rs, ps, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
