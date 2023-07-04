package dao;

import model.Course;
import model.Message;
import org.apache.commons.lang3.StringUtils;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDao {

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;


    public static Map<String,Object> add(Message message) {

        String sql1 = "insert into message(questioner,questionerId,courseName,courseId,title,status,attachForStu,createTime)values (?,?,?,?,?,?,?,?)";

        Map<String,Object> map =new HashMap<>();
        try {

            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            PreparedStatement ps = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,message.getQuestioner());
            ps.setInt(2,message.getQuestionerId());
            ps.setString(3,message.getCourseName());
            ps.setInt(4,message.getCourseId());
            ps.setString(5,message.getTitle());
            ps.setString(6,"1");
            ps.setString(7,message.getAttachForStu());
            ps.setDate(8, new Date(new java.util.Date().getTime()));
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            while (rs.next()){
                map.put("messageId",rs.getInt(1));
            }
            map.put("success","成功");
            //conn.commit();
            JDBCUtil.close(rs, ps, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<Message> listForStu(int id) {
        String sql = "select * from message where questionerId ="+id;
        List<Message> messageList = new ArrayList<>();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Message message = new Message();
                String status = rs.getString("status");
                if ("1".equals(status)) {
                    message.setStatusMessage("老师未读");
                }else if ("2".equals(status)){
                    message.setStatusMessage("老师已读");
                }else if ("3".equals(status)){
                    message.setStatusMessage("老师回复学生未读");
                }else if ("4".equals(status)){
                    message.setStatusMessage("老师回复学生已读");
                }
                message.setId(rs.getInt("id"));
                message.setTitle(rs.getString("title"));
                message.setAttachForTea(rs.getString("attachForTea"));
                message.setAttachForStu(rs.getString("attachForStu"));
                message.setStatus(rs.getString("status"));
                if (rs.getString("content") == null){
                    message.setContent("教师未回复");
                }else {
                    message.setContent(rs.getString("content"));
                }
                message.setCourseName(rs.getString("courseName"));
                message.setCourseId(rs.getInt("courseId"));
                message.setCreateTime(rs.getDate("createTime"));
                message.setQuestionerId(rs.getInt("questionerId"));
                messageList.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }

        return messageList;

    }
    public static List<Message> listOneCourse(int id) {
        String sql = "select * from message where courseId ="+id;
        List<Message> messageList = new ArrayList<>();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Message message = new Message();
                if (StringUtils.isBlank(rs.getString("attachForStu"))){
                    message.setAttachForStu("");
                }else {
                    message.setAttachForStu(rs.getString("attachForStu"));
                }
                if (StringUtils.isBlank(rs.getString("attachForTea"))){
                    message.setAttachForTea("");
                }else {
                    message.setAttachForTea(rs.getString("attachForTea"));
                }
                if (StringUtils.isBlank(rs.getString("content"))){
                    message.setContent("教师未回复");
                }else {
                    message.setContent(rs.getString("content"));
                }
                message.setId(rs.getInt("id"));
                message.setCourseName(rs.getString("courseName"));
                message.setCourseId(rs.getInt("courseId"));
                message.setTitle(rs.getString("title"));
                message.setQuestioner(rs.getString("questioner"));
                message.setCreateTime(rs.getDate("createTime"));
                message.setQuestionerId(rs.getInt("questionerId"));
                messageList.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }

        return messageList;

    }
    public static List<Message> listForAdmin() {
        String sql = "select * from message ";
        List<Message> messageList = new ArrayList<>();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id"));
                message.setContent(rs.getString("content"));
                message.setCourseName(rs.getString("courseName"));
                message.setQuestioner(rs.getString("content"));
                message.setCourseId(rs.getInt("courseId"));
                message.setQuestionerId(rs.getInt("questionerId"));
                message.setTitle(rs.getString("title"));
                message.setCreateTime(rs.getDate("createTime"));
                message.setAttachForStu(rs.getString("attachForStu"));
                message.setAttachForTea(rs.getString("attachForTea"));
                messageList.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }

        return messageList;

    }

    public static void remove(int id) {

        String sql = "delete from message where id="+id;
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }
    }



    //
    //
    //
    public static void updateForStu(Message message) {

        String sql = "update message set title = '"+message.getTitle()+"' , attachForStu = '"+message.getAttachForStu()+"' where id = "+message.getId();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            //conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }
    }
    public static void updateForTea(Message message) {

        String sql = "update message set content = '"+message.getContent()+"' , attachForTea = '"+message.getAttachForTea()+"' , status = 2 where id = "+message.getId();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            //conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }
    }

    public static Message selectById(int id){
        String sql = "select * from message where id="+id;
        Message message = new Message();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()){
                message.setId(rs.getInt("id"));
                if (StringUtils.isBlank(rs.getString("attachForStu"))){
                    message.setAttachForStu("");
                }else {
                    message.setAttachForStu(rs.getString("attachForStu"));
                }
                if (StringUtils.isBlank(rs.getString("attachForTea"))){
                    message.setAttachForTea("");
                }else {
                    message.setAttachForTea(rs.getString("attachForTea"));
                }
                if (StringUtils.isBlank(rs.getString("content"))){
                    message.setContent("教师未回复");
                }else {
                    message.setContent(rs.getString("content"));
                }
                message.setCourseName(rs.getString("courseName"));
                message.setCourseId(rs.getInt("courseId"));
                message.setQuestionerId(rs.getInt("questionerId"));
                message.setQuestioner(rs.getString("questioner"));
                message.setTitle(rs.getString("title"));
                message.setCreateTime(rs.getDate("createTime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }
        return message;
    }

}
