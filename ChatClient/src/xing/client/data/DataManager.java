package xing.client.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import xing.client.user.Friends;
import xing.client.user.Subgroup;
import xing.client.user.User;

public class DataManager {

	static DataManager dm = new DataManager();
	int port = 10086;
	InetAddress Localip = null;
	Socket socket = null;
	String message;
	DataOutputStream dos = null;
	DataInputStream dis = null;
	datadealThread ddT;// 输入信息处理线程
	boolean isSuccessLogin = false; // 判断是否可以登录成功
	String loginInfo = null; // 登录信息 存储是否可以登录 已经登录等
	public static User user = new User(); // 用户表
	public static ArrayList<Friends> fslist = new ArrayList<Friends>(); // 好友表
	public static ArrayList<Subgroup> sglist = new ArrayList<Subgroup>(); // 分组表
	public static SortedMap<Integer, String> groups = Collections.synchronizedSortedMap(new TreeMap<Integer, String>());

	public static DataManager getDataManager() {
		return dm;
	}

	public int sentRegisterInfo(String msg) { // 发送注册信息
		String[] s = null;
		int s1;
		try {
			Localip = InetAddress.getLocalHost(); // 本地地址
			socket = new Socket(Localip, port);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(msg);
			String len = dis.readUTF();
			if (len.startsWith("@register")) { // 注册是否成功
				s = len.split("@register");
				s1 = Integer.parseInt(s[1]);
				return s1;
			}

		} catch (IOException e1) {
			e1.printStackTrace();

		} finally {
			try {
				dos.close();
				dis.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return 0;

	}

	public boolean sentLoginInfo(String userID, String pwd) { // 发送登录信息
		String[] s = null; // 分解字符
		String mess = "@login" + userID + "@userID" + pwd + "@pwd";
		user.userID = Integer.valueOf(userID);
		user.pwd = pwd;
		try {
			Localip = InetAddress.getLocalHost(); // 本地地址
			socket = new Socket(Localip, port);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(mess);
			String len = dis.readUTF();
			if (len.startsWith("@login")) { // 登录是否成功
				s = len.split("@login");
				loginInfo = s[1];
				if (loginInfo.equals("true")) {
					isSuccessLogin = true;
					readLoginInfo();
				} else {
					try {
						dos.close();
						dis.close();
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (IOException e1) {
			e1.printStackTrace();

		}

		return isSuccessLogin;

	}

	public void readLoginInfo() { // 读取登录信息
		String len = "0";
		String[] s = null; // 分解字符串
		Subgroup sg = new Subgroup();
		Friends fs = new Friends();

		try {
			while (!((len = dis.readUTF()).equals("@loginover")) && len != null) {
				if (len.startsWith("@user")) {
					s = len.split("@user");
					s = s[1].split("@sign");
					user.sign = s[0];
					s = s[1].split("@photoID");
					user.photoID = Integer.parseInt(s[0]);
					s = s[1].split("@nickname");
					user.nickname = s[0];
					s = s[1].split("@sex");
					user.sex = s[0];
				} else if (len.startsWith("@subgroup")) { // 获取分组
					s = len.split("@subgroup");
					s = s[1].split("@sno");
					sg.sno = Integer.parseInt(s[0]);
					s = s[1].split("@sname");
					sg.sname = s[0];
					sglist.add(sg);
				} else if (len.startsWith("@friends")) { // 获取好友列表
					Friends f = new Friends();
					s = len.split("@friends");
					s = s[1].split("@fID");
					f.fID = Integer.parseInt(s[0]);
					s = s[1].split("@fsno");
					f.fsno = Integer.parseInt(s[0]);
					s = s[1].split("@fstatus");
					f.fstatus = Integer.parseInt(s[0]);
					s = s[1].split("@fnickname");
					f.fnickname = s[0];
					s = s[1].split("@fsign");
					f.fsign = s[0];
					s = s[1].split("@fsex");
					f.fsex = s[0];
					s = s[1].split("@fpID");
					f.fpID = Integer.parseInt(s[0]);
					System.out.println("@friends" + f.fID);
					fslist.add(f);
				} else if (len.startsWith("@group")) { // 获取群成员
					s = len.split("@group");
					if (s[1] == null)
						continue;
					s = s[1].split("@userID");
					int ID = Integer.parseInt(s[0]);
					s = s[1].split("@nickname");
					String nickname = s[0];
					groups.put(ID, nickname);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sentDeleteSubgroup(Subgroup sbg) { // 发送删除分组信息
		String message = "@deletesub" + sbg.sname + "@sname" + sbg.sno;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchInfo(int fID) {// 发送查找用户信息

		String message = "@searchInfo" + fID;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sentAddSubgroup(Subgroup sbg) {  //发送添加分组信息
		String message = "@addsub" + sbg.sname + "@sname" + sbg.sno;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sentRenameSubgroup(Subgroup sbg) { // 发送重命名分组信息
		String message = "@renamesub" + sbg.sname + "@sname" + sbg.sno;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sentChageFriendsfsno(Friends f) {// 发送更改好友所在组信息
		String message = "@Chagefriendsfsno" + f.fID + "@fpID" + f.fpID;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sentDeleteFriendsfsno(int fID) { // 发送删除好友信息
		String message = "@deletefriendsfsno" + fID;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sentIsAddFriend(boolean f, int addID) { // 发送是否添加好友信息
		String msg = "@isAdd" + addID + "@addID" + f;
		try {
			dos.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sentAddFriend(int fID) {// 发送添加所查找用户信息
		String msg = "@searchAdd" + fID;
		try {
			dos.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sentChatsingle(String s, int i) { // 发送私聊信息
		String message = i + "@single" + user.userID + "@sglfrom" + s;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sentChatAll(String msg) { // 发送群聊信息
		try {
			dos.writeUTF("@all" + msg);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sentExitInfo() { // 发送用户退出信息
		String message = "@exit" + user.userID;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getloginInfo() {// 返回信息分析
		return this.loginInfo;
	}

	public void close() {
		ddT.setflag_start(false);
		ddT.interrupt();
		try {
			dis.close();
			dos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void startdatadealThread() { // 启动接收进程
		ddT = new datadealThread();
		ddT.setflag_start(true);
		ddT.start();
	}

	public Friends searchfriend(int fID) { // 查询好友
		for (Friends f : fslist) {
			if (f.fID == fID) {
				return f;
			}
		}
		return null;
	}

}
