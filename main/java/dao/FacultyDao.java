package dao;

import model.Faculty;
import model.Teacher;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacultyDao {

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;

    public static Map<String,Object> add(Faculty faculty) {

        String sql1 = "insert into faculty(facultyName,content)values (?,?)";

        Map<String,Object> map =new HashMap<>();
        try {

            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            PreparedStatement ps = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,faculty.getFacultyName());
            ps.setString(2,faculty.getContent());
            ps.executeUpdate();
            map.put("success","成功");
            //conn.commit();
            JDBCUtil.close(rs, ps, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<Faculty> list() {
        String sql = "select * from faculty";
        List<Faculty> facultyList = new ArrayList<>();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Faculty faculty = new Faculty();
                faculty.setFacultyName(rs.getString("facultyName"));
                faculty.setContent(rs.getString("content"));
                faculty.setId(rs.getInt("id"));
                facultyList.add(faculty);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }

        return facultyList;

    }

    public static void remove(int id) {

        String sql = "delete from faculty where id="+id;
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

    public static void update(Faculty faculty) {

        String sql = "update faculty set facultyName = '"+faculty.getFacultyName()+"' , content = '"+faculty.getContent()+"'  where id = "+faculty.getId();
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

    public static Faculty selectById(int id){
        String sql = "select * from faculty where id="+id;
        Faculty faculty = new Faculty();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()){
                faculty.setId(rs.getInt("id"));
                faculty.setContent(rs.getString("content"));
                faculty.setFacultyName(rs.getString("facultyName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }
        return faculty;
    }

}
