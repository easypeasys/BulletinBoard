package controller;

import com.alibaba.fastjson.JSON;
import dao.CourseDao;
import dao.MessageDao;
import dao.StudentDao;
import model.Course;
import model.Message;
import model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/courseCon")
public class courseServlet extends HttpServlet {


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        Map<String,Object> map= new HashMap<>();
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("cache-control", "no-cache");
        String type = request.getParameter("type");
        if ("1".equals(type)){
            Course course =new Course();
            course.setContent(request.getParameter("content"));
            course.setTeacher(request.getParameter("teacher"));
            course.setFaculty(request.getParameter("faculty"));
            course.setCourseName(request.getParameter("courseName"));
            map = CourseDao.add(course);
        }else if ("2".equals(type)){
            Course course =new Course();
            course.setId(Integer.parseInt(request.getParameter("id")));
            course.setContent(request.getParameter("content"));
            course.setTeacher(request.getParameter("teacher"));
            course.setFaculty(request.getParameter("faculty"));
            course.setCourseName(request.getParameter("courseName"));
            CourseDao.update(course);
            map.put("success","成功");
        }else if ("3".equals(type)) {
            CourseDao.remove(Integer.parseInt(request.getParameter("id")));
        }else if ("4".equals(type)){
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
        HttpSession session= request.getSession();
        String userId = (String)session.getAttribute("userId");
        String type = request.getParameter("type");

        if ("1".equals(type)) {
            Student student = StudentDao.selectById(Integer.parseInt(userId));
            String[] split = student.getClassList().split(",");
            List<Course> list =new ArrayList<>();
            for (String str: split){
                Course course = CourseDao.selectById(Integer.parseInt(str));
                list.add(course);
            }
            map.put("code",0);
            map.put("msg","成功");
            map.put("data",list);
            map.put("count",list.size());
        }else if ("2".equals(type)){
            List<Course> courseList = CourseDao.listForTea(Integer.parseInt(userId));
            map.put("code",0);
            map.put("msg","成功");
            map.put("data",courseList);
            map.put("count",courseList.size());
        }
        String s = JSON.toJSONString(map);
        PrintWriter out = response.getWriter();
        out.print(s);
        out.flush();
        out.close();
    }
}
