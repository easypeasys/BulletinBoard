package dao;

import model.Course;
import model.Faculty;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDao {

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;

    public static Map<String,Object> add(Course course) {

        String sql1 = "insert into course(teacher,courseName,faculty,content,teacherId)values (?,?,?,?,?)";

        Map<String,Object> map =new HashMap<>();
        try {

            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            PreparedStatement ps = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,course.getTeacher());
            ps.setString(2,course.getCourseName());
            ps.setString(3,course.getFaculty());
            ps.setString(4,course.getContent());
            ps.setInt(5,course.getTeacherId());
            ps.executeUpdate();
            map.put("success","成功");
            //conn.commit();
            JDBCUtil.close(rs, ps, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<Course> list() {
        String sql = "select * from course";
        List<Course> courseList = new ArrayList<>();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Course course = new Course();
                course.setTeacher(rs.getString("teacher"));
                course.setContent(rs.getString("content"));
                course.setTeacherId(rs.getInt("teacherId"));
                course.setId(rs.getInt("id"));
                course.setCourseName(rs.getString("courseName"));
                course.setFaculty(rs.getString("faculty"));
                courseList.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }
        return courseList;
    }

    public static List<Course> listForTea(int teacherId) {
        String sql = "select * from course where teacherId = "+ teacherId;
        List<Course> courseList = new ArrayList<>();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Course course = new Course();
                course.setTeacher(rs.getString("teacher"));
                course.setContent(rs.getString("content"));
                course.setTeacherId(rs.getInt("teacherId"));
                course.setId(rs.getInt("id"));
                course.setCourseName(rs.getString("courseName"));
                course.setFaculty(rs.getString("faculty"));
                courseList.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }
        return courseList;

    }

    public static void remove(int id) {

        String sql = "delete from course where id="+id;
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

    public static void update(Course course) {

        String sql = "update course set teacher = '"+course.getTeacher()+"' , teacherId = "+course.getTeacherId()+" , courseName = '"+course.getCourseName()+"' " +
                " , faculty = '"+course.getFaculty()+"' , content = '"+course.getContent()+"' where id = "+course.getId();
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

    public static Course selectById(int id){
        String sql = "select * from course where id="+id;
        Course course = new Course();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()){
                course.setId(rs.getInt("id"));
                course.setContent(rs.getString("content"));
                course.setCourseName(rs.getString("courseName"));
                course.setFaculty(rs.getString("faculty"));
                course.setTeacher(rs.getString("teacher"));
                course.setTeacherId(rs.getInt("teacherId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }
        return course;
    }

}
