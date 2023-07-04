package controller;

import com.alibaba.fastjson.JSON;
import dao.CourseDao;
import dao.FacultyDao;
import model.Course;
import model.Faculty;

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

@WebServlet("/courseAdmin")
public class adminCourseServlet extends HttpServlet {


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        Map<String,Object> map= new HashMap<>();
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("cache-control", "no-cache");
        String type = request.getParameter("type");
        if ("1".equals(type)){
            //插入
            Course course =new Course();
            String[] teachers = request.getParameter("teacher").split(",");
            course.setContent(request.getParameter("content"));
            course.setTeacher(teachers[0]);
            course.setTeacherId(Integer.parseInt(teachers[1]));
            course.setFaculty(request.getParameter("faculty"));
            course.setCourseName(request.getParameter("courseName"));
            map = CourseDao.add(course);
        }else if ("2".equals(type)){
            //更新修改
            Course course =new Course();
            String[] teachers = request.getParameter("teacher").split(",");
            course.setId(Integer.parseInt(request.getParameter("id")));
            course.setContent(request.getParameter("content"));
            course.setTeacher(teachers[0]);
            course.setTeacherId(Integer.parseInt(teachers[1]));
            course.setFaculty(request.getParameter("faculty"));
            course.setCourseName(request.getParameter("courseName"));
            CourseDao.update(course);
            map.put("success","成功");
        }else if ("3".equals(type)) {
            //删除
            CourseDao.remove(Integer.parseInt(request.getParameter("id")));
        }else if ("4".equals(type)){
            //通过id查
            Course course = CourseDao.selectById(Integer.parseInt(request.getParameter("id")));
            List<Course> list =new ArrayList<>();
            list.add(course);
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
        List<Course> list = CourseDao.list();
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
