package dao;

import model.Course;
import model.Student;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDao {

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;

    public static Student selectById(int id){
        String sql = "select * from student where studentId="+id;
        Student student = new Student();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()){
                student.setId(rs.getInt("id"));
                student.setStudentId(rs.getInt("studentId"));
                student.setClassList(rs.getString("classList"));
                student.setNickname(rs.getString("nickname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }
        return student;
    }

}
