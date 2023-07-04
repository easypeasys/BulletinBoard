package dao;

import model.Teacher;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherDao {

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;

    public static Map<String,Object> add(Teacher teacher) {

        String sql1 = "insert into account(username,password,identity,nickname)values (?,?,?,?)";

        Map<String,Object> map =new HashMap<>();
        try {

            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            PreparedStatement ps = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,teacher.getUsername());
            ps.setString(2,"123456");
            ps.setString(3,"2");
            ps.setString(4,teacher.getTeacherName());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            while (rs.next()){
                teacher.setTeacherId(rs.getInt(1));
            }
            String sql2 = null;
            sql2 = "insert into teacher(teacherId,teacherName,classList,title,content) values (?,?,?,?,?)" ;
            ps =conn.prepareStatement(sql2);
            ps.setInt(1,teacher.getTeacherId());
            ps.setString(2,teacher.getTeacherName());
            ps.setString(3,teacher.getClassList());
            ps.setString(4,teacher.getTitle());
            ps.setString(5,teacher.getContent());
            ps.executeUpdate();
            map.put("success","成功");
            //conn.commit();
            JDBCUtil.close(rs, ps, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<Teacher> list() {
        String sql = "select * from teacher";
        List<Teacher> teacherList = new ArrayList<>();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getInt("id"));
                teacher.setTeacherId(rs.getInt("teacherId"));
                teacher.setTeacherName(rs.getString("teacherName"));
                teacher.setClassList(rs.getString("classList"));
                teacher.setTitle(rs.getString("title"));
                teacher.setContent(rs.getString("content"));
                teacherList.add(teacher);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }

        return teacherList;

    }

    public static void remove(int id) {

        String sql = "delete from teacher where id="+id;
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

    public static void update(Teacher teacher) {

        String sql = "update teacher set teacherName = '"+teacher.getTeacherName()+"' , classList = '"+teacher.getClassList()+"' " +
                " , title = '"+teacher.getTitle()+"' , content = '"+teacher.getContent()+"' where id = "+teacher.getId();
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

    public static Teacher selectById(int id){
        String sql = "select * from teacher where id="+id;
        Teacher teacher = new Teacher();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()){
                teacher.setTitle(rs.getString("title"));
                teacher.setContent(rs.getString("content"));
                teacher.setTeacherName(rs.getString("teacherName"));
                teacher.setClassList(rs.getString("classList"));
                teacher.setId(rs.getInt("id"));
                teacher.setTeacherId(rs.getInt("teacherId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }
        return teacher;
    }

}
