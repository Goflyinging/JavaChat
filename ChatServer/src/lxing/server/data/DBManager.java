package lxing.server.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	
	/*
	 * oracle的驱动包的位置   D:\oracle\product\10.2.0\db_1\jdbc\lib
	 * oralce的驱动路径             oracle.jdbc.driver.OracleDriver
	 * 
	 * 
	 * 连接oracle的url
	 * jdbc:oracle:thin:@localhost:1521:itcast
	 *   * jdbc:使用的是jdbc协议
	 *   * oracle:表示使用的是oralce子协议
	 *   * thin :使用的是瘦客户端
	 *   * localhost 主机名(IP)
	 *   * 1521 oracle的监听端口号
	 *   * itcast 表示的oralce数据库的sid,通常跟oracle的全局数据库名相同(不是itcast.com)   
	 * 
	 */
	private String url = "jdbc:oracle:thin:@localhost:1521:xing";
	private String user = "lxing";
	private String password = "xing";

	/**
	 * 加载驱动
	 */
	public DBManager() {
		try {
			//oracle.jdbc.driver.OracleDriver  oracle的驱动类
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接
	 */
	public Connection getConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭资源
	 * 
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public void closeResource(Connection conn, Statement stmt, ResultSet rs) {
		try {
			// 关闭结果集
			if (rs != null) {
				rs.close();
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			// 关闭Statement对像
			try {
				if (stmt != null) {
					stmt.close();
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			} 
			finally {
				// 关闭连接
				try {
					if (conn != null) {
						conn.close();
					}
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

	}
	

}
