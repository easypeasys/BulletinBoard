package dao;

import model.Faculty;
import model.Tips;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipsDao {

    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;

    public static Map<String,Object> add(Tips tips) {

        String sql1 = "insert into tips(userId,content,messageId,status,createTime,type)values (?,?,?,?,?,?)";

        Map<String,Object> map =new HashMap<>();
        try {

            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            PreparedStatement ps = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,tips.getUserId());
            ps.setString(2,tips.getContent());
            ps.setInt(3,tips.getMessageId());
            ps.setString(4,"1");
            ps.setDate(5,new Date(new java.util.Date().getTime()));
            ps.setInt(6,tips.getType());
            ps.executeUpdate();
            map.put("success","成功");
            //conn.commit();
            JDBCUtil.close(rs, ps, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<Tips> list(int userId) {
        String sql = "select * from tips where status = '1' and userId = "+userId + " ORDER BY type ASC";
        List<Tips> tipsList = new ArrayList<>();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Tips tips = new Tips();
                tips.setId(rs.getInt("id"));
                tips.setContent(rs.getString("content"));
                tips.setMessageId(rs.getInt("messageId"));
                tips.setStatus(rs.getString("status"));
                tips.setUserId(rs.getInt("userId"));
                tips.setCreateTime(rs.getDate("createTime"));
                tipsList.add(tips);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }

        return tipsList;

    }

    //逻辑remove
    public static void remove(int id) {

        String sql = "update tips set status = '2' where id =" + id;
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

    public static Tips selectById(int id){
        String sql = "select * from tips where id="+id;
        Tips tips =new Tips();
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()){
                tips.setId(rs.getInt("id"));
                tips.setContent(rs.getString("content"));
                tips.setMessageId(rs.getInt("messageId"));
                tips.setStatus(rs.getString("status"));
                tips.setUserId(rs.getInt("userId"));
                tips.setCreateTime(rs.getDate("createTime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(rs, stmt, conn);
        }
        return tips;
    }

}
