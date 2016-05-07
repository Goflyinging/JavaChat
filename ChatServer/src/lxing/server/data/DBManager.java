package lxing.server.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	
	/*
	 * oracle����������λ��   D:\oracle\product\10.2.0\db_1\jdbc\lib
	 * oralce������·��             oracle.jdbc.driver.OracleDriver
	 * 
	 * 
	 * ����oracle��url
	 * jdbc:oracle:thin:@localhost:1521:itcast
	 *   * jdbc:ʹ�õ���jdbcЭ��
	 *   * oracle:��ʾʹ�õ���oralce��Э��
	 *   * thin :ʹ�õ����ݿͻ���
	 *   * localhost ������(IP)
	 *   * 1521 oracle�ļ����˿ں�
	 *   * itcast ��ʾ��oralce���ݿ��sid,ͨ����oracle��ȫ�����ݿ�����ͬ(����itcast.com)   
	 * 
	 */
	private String url = "jdbc:oracle:thin:@localhost:1521:xing";
	private String user = "lxing";
	private String password = "xing";

	/**
	 * ��������
	 */
	public DBManager() {
		try {
			//oracle.jdbc.driver.OracleDriver  oracle��������
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ����
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
	 * �ر���Դ
	 * 
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public void closeResource(Connection conn, Statement stmt, ResultSet rs) {
		try {
			// �رս����
			if (rs != null) {
				rs.close();
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			// �ر�Statement����
			try {
				if (stmt != null) {
					stmt.close();
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			} 
			finally {
				// �ر�����
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
