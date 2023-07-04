package controller;

import com.alibaba.fastjson.JSON;
import dao.FacultyDao;
import dao.TipsDao;
import model.Faculty;
import model.Tips;

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

@WebServlet("/tipsServlet")
public class tipsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        Map<String,Object> map= new HashMap<>();
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("cache-control", "no-cache");
        String type = request.getParameter("type");
        HttpSession session = request.getSession();
//        System.out.println(type);
        if ("1".equals(type)){//添加 教师已回答学生未读信息
            Tips tips = new Tips();
            tips.setUserId(Integer.parseInt(request.getParameter("questionerId")));
            tips.setContent("您的留言已有教师回答，请尽快查看！！");
            tips.setMessageId(Integer.parseInt(request.getParameter("messageId")));
            tips.setType(1);
            map = TipsDao.add(tips);
        }else if ("2".equals(type)){// 添加  教师修改的信息学生未读
            Tips tips = new Tips();
            tips.setUserId(Integer.parseInt(request.getParameter("questionerId")));
            tips.setContent("教师修改了留言的回答，请尽快查看！！");
            tips.setMessageId(Integer.parseInt(request.getParameter("messageId")));
            tips.setType(2);
            TipsDao.add(tips);
            map.put("success","成功");
        }else if ("3".equals(type)) { // 添加  教师所删除的留言 需要通知学生
            Tips tips = new Tips();
            tips.setUserId(Integer.parseInt(request.getParameter("questionerId")));
            tips.setContent("您的留言已被教师删除！！");
            tips.setMessageId(Integer.parseInt(request.getParameter("messageId")));
            tips.setType(3);
//            System.out.println("tipss=========="+tips);
            TipsDao.add(tips);
            map.put("success","成功");
        }else if ("4".equals(type)){ //更新  提示状态 转变成2
            TipsDao.remove(Integer.parseInt(request.getParameter("tipsId")));
            map.put("success","成功");
        }else if ("5".equals(type)){//添加 提醒老师有哪些新留言
            Tips tips = new Tips();
            tips.setUserId(Integer.parseInt(request.getParameter("teacherId")));
            tips.setContent("您的课程又有新的留言了！！");
            tips.setMessageId(Integer.parseInt(request.getParameter("messageId")));
            tips.setType(4);
            TipsDao.add(tips);
        }
        String s = JSON.toJSONString(map);
        PrintWriter out = response.getWriter();
        out.print(s);
        out.flush();
        out.close();
    }
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        HttpSession session =request.getSession();
        Map<String,Object> map =new HashMap<>();
        String userId = (String) session.getAttribute("userId");
        List<Tips> list = TipsDao.list(Integer.parseInt(userId));
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("cache-control", "no-cache");
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
