package base.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class AccessJDBCUtil {

	public static String DRIVER_NAME = "sun.jdbc.odbc.JdbcOdbcDriver";
	public static String USERNAME = "";
	public static String PASSWORD = "";
	public static String DRIVER_URL = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=cioe2011";

	// 通过配置文件为以上字段赋值
	static {
		// 加载驱动类
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 得到连接
	public static Connection getConnection() throws SQLException {
		Properties prop = new Properties();
		prop.put("charSet", "gbk");
		return DriverManager.getConnection("jdbc:odbc:cioe2011", prop);
	}

	// 释放资源
	public static void release(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			} // ignore
			rs = null;
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
			} // ignore
			stmt = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			} // ignore
		}

	}

	public static void main(String[] args) {
		try {
			Connection conn = AccessJDBCUtil.getConnection();
			String sql = "select * from userinfo where hkdate like '2014%'";
			PreparedStatement ps = conn.prepareStatement(sql);
			// ps.setInt(1, 1);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String hkid = rs.getString("hkid");
//				String admin = rs.getString(2);
//				String power = rs.getString(3);
//				String hkname = rs.getString(4);
//				System.out.println(hkid + "    " + admin + "   " + power
//						+ "   " + hkname);
				
				System.err.println(hkid);
			}
			AccessJDBCUtil.release(rs, ps, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
