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
	datadealThread ddT;// ������Ϣ�����߳�
	boolean isSuccessLogin = false; // �ж��Ƿ���Ե�¼�ɹ�
	String loginInfo = null; // ��¼��Ϣ �洢�Ƿ���Ե�¼ �Ѿ���¼��
	public static User user = new User(); // �û���
	public static ArrayList<Friends> fslist = new ArrayList<Friends>(); // ���ѱ�
	public static ArrayList<Subgroup> sglist = new ArrayList<Subgroup>(); // �����
	public static SortedMap<Integer, String> groups = Collections.synchronizedSortedMap(new TreeMap<Integer, String>());

	public static DataManager getDataManager() {
		return dm;
	}

	public int sentRegisterInfo(String msg) { // ����ע����Ϣ
		String[] s = null;
		int s1;
		try {
			Localip = InetAddress.getLocalHost(); // ���ص�ַ
			socket = new Socket(Localip, port);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(msg);
			String len = dis.readUTF();
			if (len.startsWith("@register")) { // ע���Ƿ�ɹ�
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

	public boolean sentLoginInfo(String userID, String pwd) { // ���͵�¼��Ϣ
		String[] s = null; // �ֽ��ַ�
		String mess = "@login" + userID + "@userID" + pwd + "@pwd";
		user.userID = Integer.valueOf(userID);
		user.pwd = pwd;
		try {
			Localip = InetAddress.getLocalHost(); // ���ص�ַ
			socket = new Socket(Localip, port);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(mess);
			String len = dis.readUTF();
			if (len.startsWith("@login")) { // ��¼�Ƿ�ɹ�
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

	public void readLoginInfo() { // ��ȡ��¼��Ϣ
		String len = "0";
		String[] s = null; // �ֽ��ַ���
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
				} else if (len.startsWith("@subgroup")) { // ��ȡ����
					s = len.split("@subgroup");
					s = s[1].split("@sno");
					sg.sno = Integer.parseInt(s[0]);
					s = s[1].split("@sname");
					sg.sname = s[0];
					sglist.add(sg);
				} else if (len.startsWith("@friends")) { // ��ȡ�����б�
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
				} else if (len.startsWith("@group")) { // ��ȡȺ��Ա
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

	public void sentDeleteSubgroup(Subgroup sbg) { // ����ɾ��������Ϣ
		String message = "@deletesub" + sbg.sname + "@sname" + sbg.sno;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void searchInfo(int fID) {// ���Ͳ����û���Ϣ

		String message = "@searchInfo" + fID;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sentAddSubgroup(Subgroup sbg) {  //������ӷ�����Ϣ
		String message = "@addsub" + sbg.sname + "@sname" + sbg.sno;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sentRenameSubgroup(Subgroup sbg) { // ����������������Ϣ
		String message = "@renamesub" + sbg.sname + "@sname" + sbg.sno;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sentChageFriendsfsno(Friends f) {// ���͸��ĺ�����������Ϣ
		String message = "@Chagefriendsfsno" + f.fID + "@fpID" + f.fpID;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sentDeleteFriendsfsno(int fID) { // ����ɾ��������Ϣ
		String message = "@deletefriendsfsno" + fID;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sentIsAddFriend(boolean f, int addID) { // �����Ƿ���Ӻ�����Ϣ
		String msg = "@isAdd" + addID + "@addID" + f;
		try {
			dos.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sentAddFriend(int fID) {// ��������������û���Ϣ
		String msg = "@searchAdd" + fID;
		try {
			dos.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sentChatsingle(String s, int i) { // ����˽����Ϣ
		String message = i + "@single" + user.userID + "@sglfrom" + s;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sentChatAll(String msg) { // ����Ⱥ����Ϣ
		try {
			dos.writeUTF("@all" + msg);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sentExitInfo() { // �����û��˳���Ϣ
		String message = "@exit" + user.userID;
		try {
			dos.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getloginInfo() {// ������Ϣ����
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

	public void startdatadealThread() { // �������ս���
		ddT = new datadealThread();
		ddT.setflag_start(true);
		ddT.start();
	}

	public Friends searchfriend(int fID) { // ��ѯ����
		for (Friends f : fslist) {
			if (f.fID == fID) {
				return f;
			}
		}
		return null;
	}

}
