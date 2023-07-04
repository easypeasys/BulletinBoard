package controller;

import com.alibaba.fastjson.JSON;
import dao.CourseDao;
import dao.TeacherDao;
import model.Account;
import model.Course;
import model.Teacher;
import util.JDBCUtil;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/teacherAdmin")
public class adminTeacherServlet extends HttpServlet {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        Map<String,Object> map= new HashMap<>();
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("cache-control", "no-cache");
        String type = request.getParameter("type");
        if ("1".equals(type)){
            Teacher teacher =new Teacher();
            teacher.setContent(request.getParameter("content"));
            teacher.setClassList(request.getParameter("classList"));
            teacher.setTeacherName(request.getParameter("teacherName"));
            teacher.setTitle(request.getParameter("title"));
            teacher.setUsername(request.getParameter("username"));
            map = TeacherDao.add(teacher);
        }else if ("2".equals(type)){
            Teacher teacher =new Teacher();
            teacher.setContent(request.getParameter("content"));
            teacher.setClassList(request.getParameter("classList"));
            teacher.setTeacherName(request.getParameter("teacherName"));
            teacher.setTitle(request.getParameter("title"));
            teacher.setId(Integer.parseInt(request.getParameter("id")));
            TeacherDao.update(teacher);
            map.put("success","成功");
        }else if ("3".equals(type)) {
            TeacherDao.remove(Integer.parseInt(request.getParameter("id")));
        }else if ("4".equals(type)){
            Teacher teacher = TeacherDao.selectById(Integer.parseInt(request.getParameter("id")));
            List<Teacher> list =new ArrayList<>();
            list.add(teacher);
            map.put("data",list);
        }
        String s = JSON.toJSONString(map);
        PrintWriter out = response.getWriter();
        out.print(s);
        out.flush();
        out.close();

    }

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        Map<String,Object> map= new HashMap<>();
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("cache-control", "no-cache");
        List<Teacher> list = TeacherDao.list();
        map.put("code",0);
        map.put("msg","成功");
        map.put("data",list);
        map.put("count",list.size());
        String s = JSON.toJSONString(map);
        PrintWriter out = response.getWriter();
        out.print(s);
        out.flush();
        out.close();
    }
}
