package controller;

import com.alibaba.fastjson.JSON;
import dao.FacultyDao;
import dao.TeacherDao;
import model.Faculty;
import model.Teacher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/facultyAdmin")
public class adminFacultyServlet extends HttpServlet {


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        Map<String,Object> map= new HashMap<>();
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("cache-control", "no-cache");
        String type = request.getParameter("type");
        if ("1".equals(type)){
            Faculty faculty = new Faculty();
            faculty.setFacultyName(request.getParameter("facultyName"));
            faculty.setContent(request.getParameter("content"));
            map = FacultyDao.add(faculty);

        }else if ("2".equals(type)){
            Faculty faculty = new Faculty();
            faculty.setId(Integer.parseInt(request.getParameter("id")));
            faculty.setFacultyName(request.getParameter("facultyName"));
            faculty.setContent(request.getParameter("content"));
            FacultyDao.update(faculty);
            map.put("success","成功");
        }else if ("3".equals(type)) {
            FacultyDao.remove(Integer.parseInt(request.getParameter("id")));
        }else if ("4".equals(type)){
            Faculty faculty = FacultyDao.selectById(Integer.parseInt(request.getParameter("id")));
            List<Faculty> list =new ArrayList<>();
            list.add(faculty);
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
        List<Faculty> list = FacultyDao.list();
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
