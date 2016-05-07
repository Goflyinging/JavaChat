package lxing.server.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lxing.server.User.Friends;
import lxing.server.User.Subgroup;
import lxing.server.User.User;
import lxing.server.thread.ClientThread;

public class Query {

	Connection conn = null;
	PreparedStatement pre = null;
	ResultSet rs = null;
	DBManager db = null;

	// ��������֪ͨ
	public void senttologFriendsInfo(User user, ClientThread clientTh) {
		link();
		int fID;
		int usersno;
		String m1 = "@logon" + user.userID + "@fID";
		String m2 = "@fsno" + user.nickname + "@fnickname" + user.sign + "@fsign" + user.sex + "@fsex" + user.photoID
				+ "@fpID";
		String sql1 = "select a.fID fID,b.fsno fsno from friends a,friends b where a.userID = " + user.userID
				+ "AND b.fID = " + user.userID + "AND b.userID = a.fID";
		try {
			pre = conn.prepareStatement(sql1);
			rs = pre.executeQuery();
			while (rs.next()) {
				fID = rs.getInt("fID");
				usersno = rs.getInt("fsno");
				ClientThread clt = null;
				synchronized (ClientThread.clients) {
					clt = ClientThread.clients.get(fID);
				}
				if (clt != null) {
					clt.dos.writeUTF(m1 + usersno + m2);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			close();
		}
	}

	// �������û��������ѵ���Ϣ
	public void sentFriendsInfo(int userID, ClientThread clientTh) {
		link();
		Friends friend = new Friends();
		String message = null;
		String sql1 = "select fID,fsno,b.status fstatus,b.nickname fnickname,b.sign fsign,b.sex fsex,b.photoID fpID from friends a,"
				+ "userinfo b where a.userID = " + userID + " AND b.userID = a.fID AND b.status = 1";
		try {
			pre = conn.prepareStatement(sql1);
			rs = pre.executeQuery();
			while (rs.next()) {
				friend.fID = rs.getInt("fID");
				friend.fsno = rs.getInt("fsno");
				friend.fstatus = rs.getInt("fstatus");
				friend.fnickname = rs.getString("fnickname");
				friend.fsign = rs.getString("fsign");
				friend.fsex = rs.getString("fsex");
				friend.fpID = rs.getInt("fpID");
				message = "@friends" + friend.fID + "@fID" + friend.fsno + "@fsno" + friend.fstatus + "@fstatus"
						+ friend.fnickname + "@fnickname" + friend.fsign + "@fsign" + friend.fsex + "@fsex"
						+ friend.fpID + "@fpID";
				System.out.println("frend: " + message);
				clientTh.dos.writeUTF(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	// ����Ⱥ��Ա��Ϣ
	public void sentGroupInfo(int userID, ClientThread clientTh) {
		link();
		String sql = "select userID,nickname from userInfo where status = '1' and userID !=" + userID;
		// select userID,nickname from userInfo where status = '0' and userID !=
		// 80000;

		try {
			pre = conn.prepareStatement(sql);
			rs = pre.executeQuery();
			while (rs.next()) {
				StringBuilder sb = new StringBuilder("@group");
				sb.append(rs.getInt("userID"));
				sb.append("@userID");
				sb.append(rs.getString("nickname"));
				sb.append("@nickname");
				clientTh.dos.writeUTF(sb.toString());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	// ��ȡ��Ҫ���ҵ��û���Ϣ
	public String searchInfo(int searchID) {
		link();
		String message = null;
		String sql = "select userID,sign,photoID,nickname,sex,status from userInfo" + " where userID = " + searchID;
		// select userID,nickname,sign,photoID,sex from userInfo where userID =
		try {
			pre = conn.prepareStatement(sql);
			rs = pre.executeQuery();
			while (rs.next()) {
				int ID = rs.getInt("userID");
				String sign = rs.getString("sign");
				int pID = rs.getInt("photoID");
				String nickname = rs.getString("nickname");
				String sex = rs.getString("sex");
				int status = rs.getInt("status");
				message = "@searchInfo" + ID + "@ID" + sign + "@sign" + pID + "@pID" + nickname + "@nickname" + sex
						+ "@sex" + status;
			}
			return message;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	// �ж���Ҫ��ӵ��û��Ƿ��Ѿ����Լ��ĺ���
	public boolean isFriend(int fID, int userID) {
		link();
		String sql = "select * from friends" + " where userID = " + userID + "AND fID=" + fID;
		// select userID,nickname,sign,photoID,sex from userInfo where userID =
		try {
			pre = conn.prepareStatement(sql);
			rs = pre.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;

	}

	// ����ӵĺ���д�����ݿ�
	public void addFriend(int fID, int userID) {
		link();
		String sql = "insert into friends values(?,?,?)";
		try {
			pre = conn.prepareStatement(sql);
			pre.setInt(1, userID);
			pre.setInt(2, fID);
			pre.setInt(3, 0);
			pre.executeUpdate();
			pre = conn.prepareStatement(sql);
			pre.setInt(2, userID);
			pre.setInt(1, fID);
			pre.setInt(3, 0);
			pre.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}
	//ɾ������
	public void deleteFriend(int fID, int userID) {
		link();
		String sql = "delete from friends where (userID = ? AND Fid = ?) or (userID = ? AND Fid = ?)";
		try {
			pre = conn.prepareStatement(sql);
			pre.setInt(1, userID);
			pre.setInt(2, fID);
			pre.setInt(3, fID);
			pre.setInt(4, userID);
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}

	// ��������¼�û��������ѵ���Ϣ
	public void sentSubgroup(int userID, ClientThread clientTh) {
		link();
		Subgroup sub = new Subgroup();
		String message = null;
		String sql = "select sno,sname from subgroup where userID = " + userID + "order by sno";
		try {
			pre = conn.prepareStatement(sql);
			rs = pre.executeQuery();
			while (rs.next()) {
				sub.sno = rs.getInt("sno");
				sub.sname = rs.getString("sname");
				message = "@subgroup" + sub.sno + "@sno" + sub.sname + "@sname";
				System.out.println("subgroup: " + message);
				clientTh.dos.writeUTF(message);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}

	// ��������¼�û������Լ�����Ϣ
	public User sentUserInfo(int userID, ClientThread clientTh) {
		link();
		User user = new User();
		user.userID = userID;
		String message = null;
		String sql1 = "select sign,photoID,nickname,sex from userinfo where userID = " + userID; // ����״̬
		try {
			db = new DBManager();
			conn = db.getConnection();
			pre = conn.prepareStatement(sql1);
			rs = pre.executeQuery();
			while (rs.next()) {
				user.sign = rs.getString("sign");
				System.out.println(user.sign);
				user.photoID = rs.getInt("photoID");
				user.nickname = rs.getString("nickname");
				user.sex = rs.getString("sex");
			}
			message = "@user" + user.sign + "@sign" + user.photoID + "@photoID" + user.nickname + "@nickname" + user.sex
					+ "@sex";
			System.out.println("user: " + message);
			clientTh.dos.writeUTF(message);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return user;

	}

	// ��ȡ�û�ע���ID �����û�����Ϣд�����ݿ���
	public int getUserID(User user) {
		link();
		String sql1 = "select max(userID) from UserInfo";
		try {
			db = new DBManager();
			conn = db.getConnection();
			pre = conn.prepareStatement(sql1);
			rs = pre.executeQuery();
			user.userID = 0;
			while (rs.next()) {
				int i = rs.getInt("max(userID)");
				if (i < 80000) {
					user.userID = 80000;
				} else {
					user.userID = i + 1;
				}
			}
			String sql2 = "insert into UserInfo values(?,?,?,?,?,?,?)";
			pre = conn.prepareStatement(sql2);
			pre.setInt(1, user.userID);
			pre.setString(2, user.pwd);
			pre.setString(3, user.sign);
			pre.setInt(4, 1);
			pre.setString(5, user.nickname);
			pre.setString(6, user.sex);
			pre.setInt(7, 0);
			pre.executeUpdate();
			String sql3 = "insert into Subgroup values(?,?,?)";
			pre = conn.prepareStatement(sql3);
			pre.setInt(1, user.userID);
			pre.setInt(2, 0);
			pre.setString(3, "�ҵĺ���");
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return user.userID;
	}

	// ��¼���� ������״̬д�����ݿ�
	public boolean Islogin(int userID, String pwd) {
		link();
		String sql = "select pwd,status from UserInfo where userID = " + userID;
		String pwdt = null;
		int status;
		try {
			db = new DBManager();
			conn = db.getConnection();
			pre = conn.prepareStatement(sql);
			rs = pre.executeQuery();
			if (rs.next()) {
				pwdt = rs.getString("pwd");
				status = rs.getInt("status");
				if (pwdt.equals(pwd) && status == 0) {
					// ʹ����״̬ Ϊ1 ����
					sql = "update userinfo set status = ?  where userID = " + userID;
					pre = conn.prepareStatement(sql);
					pre.setInt(1, 1);
					pre.executeUpdate();
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public void userExit(int userID, ClientThread clientTh) {
		link();
		String sql = "update userinfo set status = ?  where userID = " + userID;
		try {
			pre = conn.prepareStatement(sql);
			pre.setInt(1, 0);
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = "select fID from friends where userID = " + userID;
		int fID;
		try {
			pre = conn.prepareStatement(sql);
			rs = pre.executeQuery();
			while (rs.next()) {
				fID = rs.getInt("fID");
				ClientThread clt;
				synchronized (ClientThread.clients) {
					clt = ClientThread.clients.get(fID);
				}
				if (clt != null) {
					clt.dos.writeUTF("@logout" + userID);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			close();
		}
	}

	public void link() {
		db = new DBManager();
		conn = db.getConnection();
	}

	public void close() {
		db.closeResource(conn, pre, rs);
		db = null;
	}

}
