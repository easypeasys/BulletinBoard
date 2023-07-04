package util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtil {


    private static String driver="com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/web?&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static String username = "root";  //数据库用户名
    private static String password = "123456";//数据库密码


    private static DataSource datasource = null;

    static {
        try {

            Context context =new InitialContext();
            datasource = (DataSource)context.lookup("java:comp/env/jdbc/webExp");
        }
        catch(NamingException ne){
            System.out.println("异常："+ne);
        }
    }

    public static Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    /**
     * @Author msi
     * @Param [rs 结果集对象, stmt 执行sql的对象, conn 数据库连接对象]
     * @return void
     **/
    public static void close(ResultSet rs, Statement stmt, Connection conn){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
