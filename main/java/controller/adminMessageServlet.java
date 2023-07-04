package controller;

import com.alibaba.fastjson.JSON;
import dao.CourseDao;
import dao.MessageDao;
import model.Course;
import model.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/messageAdmin")
public class adminMessageServlet extends HttpServlet {


    private static final long serialVersionUID = 3116616550045119677L;

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        Map<String,Object> map= new HashMap<>();
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("cache-control", "no-cache");
        String type = request.getParameter("type");
        HttpSession session =request.getSession();
        if ("1".equals(type)){
            //插入
            String userId = (String) session.getAttribute("userId");
            Message message = new Message();
            message.setQuestioner((String)session.getAttribute("nickname"));
            message.setQuestionerId(Integer.parseInt(userId));
            message.setTitle(request.getParameter("title"));
            message.setCourseId(Integer.parseInt(request.getParameter("courseId")));
            message.setCourseName(request.getParameter("courseName"));
            message.setAttachForStu(request.getParameter("attachForStu"));
            map = MessageDao.add(message);
        }else if ("2".equals(type)){
            //更新修改
            Message message = new Message();
            message.setTitle(request.getParameter("title"));
            message.setAttachForStu(request.getParameter("attachForStu"));
            message.setId(Integer.parseInt(request.getParameter("id")));
            MessageDao.updateForStu(message);
            map.put("success","成功");
        }else if ("3".equals(type)) {
            //删除
            MessageDao.remove(Integer.parseInt(request.getParameter("id")));
        }else if ("4".equals(type)){
            //通过id查
            Message message = MessageDao.selectById(Integer.parseInt(request.getParameter("id")));
            List<Message> list =new ArrayList<>();
            list.add(message);
            map.put("data",list);
        }else if ("5".equals(type)){
            Message message = new Message();
            message.setContent(request.getParameter("content"));
            message.setAttachForTea(request.getParameter("attachForTea"));
            message.setId(Integer.parseInt(request.getParameter("id")));
            MessageDao.updateForTea(message);
            map.put("success","成功");
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
        String type = request.getParameter("type");
        if ("1".equals(type)) { //管理员显示留言列表
            List<Message> list = MessageDao.listForAdmin();
            map.put("code",0);
            map.put("msg","成功");
            map.put("data",list);
            map.put("count",list.size());
        } else if ("2".equals(type)) {//学生显示留言列表
            String userId = (String) session.getAttribute("userId");
            List<Message> list = MessageDao.listForStu(Integer.parseInt(userId));
            map.put("code",0);
            map.put("msg","成功");
            map.put("data",list);
            map.put("count",list.size());
        }else if ("3".equals(type)){//单门课程留言列表
            List<Message> list = MessageDao.listOneCourse(Integer.parseInt(request.getParameter("courseId")));
            map.put("code",0);
            map.put("msg","成功");
            map.put("data",list);
            map.put("count",list.size());
        } else if ("4".equals(type)) {//教师 获取留言列表
            List<Message> list =new ArrayList<>();
            String userId = (String) session.getAttribute("userId");
            List<Course> courseList = CourseDao.listForTea(Integer.parseInt(userId));
            for (Course course:courseList){
                List<Message> messages = MessageDao.listOneCourse(course.getId());
                list.addAll(messages);
            }
            map.put("code",0);
            map.put("msg","成功");
            map.put("data",list);
            map.put("count",list.size());
        }
        String s = JSON.toJSONString(map);
        PrintWriter out = response.getWriter();
        out.print(s);
        out.flush();
        out.close();
    }
}
