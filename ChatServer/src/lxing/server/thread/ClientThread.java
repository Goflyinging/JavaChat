package lxing.server.thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lxing.server.User.User;
import lxing.server.data.Query;
import lxing.server.tools.BroadCast;

public class ClientThread extends Thread {

	BroadCast bc = new BroadCast();
	public Socket clientSocket;
	public DataInputStream dis; // ������ �����û���������Ϣ
	public DataOutputStream dos;// ����� ���û�������Ϣ
	User user;
	public static Map<Integer, ClientThread> clients = Collections
			.synchronizedMap(new HashMap<Integer, ClientThread>()); // ��Ŷ�Ӧ�߳�
	private boolean flag_start = false;
	private boolean IStrue = true;
	Query query = new Query(); // �����ݿ� ͨ��

	public ClientThread(Socket socket) {
		clientSocket = socket;
		try {
			dis = new DataInputStream(clientSocket.getInputStream());
			dos = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {

		}

	}// ClientThread

	public void run() {
		String[] s = null;
		while (flag_start) {
			try {
				String Message = dis.readUTF();
				System.out.println(Message);
				// ע��
				if (Message.startsWith("@register")) {
					User user = new User();
					s = Message.split("@register");
					s = s[1].split("@pwd");
					user.pwd = s[0];
					s = s[1].split("@sign");
					user.sign = s[0];
					s = s[1].split("@nickname");
					user.nickname = s[0];
					user.sex = s[1];
					user.userID = query.getUserID(user);
					dos.writeUTF("@register" + user.userID);
				}
				// ��¼
				else if (Message.startsWith("@login")) {
					s = Message.split("@login");
					s = s[1].split("@userID");
					int userID = Integer.parseInt(s[0]);
					s = s[1].split("@pwd");
					String userPW = s[0];
					IStrue = query.Islogin(userID, userPW);
					dos.writeUTF("@login" + IStrue); // ����Ϣ���û�
					if (IStrue) { // ��֤�ɹ����� ��Ϣ
						user = query.sentUserInfo(userID, this); // �����û�������Ϣ
						query.sentSubgroup(userID, this);// �����û�������Ϣ
						query.sentFriendsInfo(userID, this);// �����û�������Ϣ
						query.sentGroupInfo(userID, this);// �����û�Ⱥ��Ϣ
						dos.writeUTF("@loginover");
						query.senttologFriendsInfo(user, this);// �Ժ��ѷ���������Ϣ
						bc.sentGroupLogin(user.userID, user.nickname);
						synchronized (clients) {
							clients.put(userID, this);
						}
					} else { // ��֤ʧ�� �ر�
						flag_start = false;
						dis.close();
						dos.close();
						this.clientSocket.close();
						this.interrupt();
					}

				} //
					// Ⱥ����
				else if (IStrue && Message.startsWith("@all")) {
					s = Message.split("@all");
					bc.sentAll("@all" + user.nickname + "(" + user.userID + ")  " + s[1], user.userID);
				} // all
					// ˽��
				else if (IStrue && Message.contains("@single")) {
					s = Message.split("@single");
					int toid = Integer.parseInt(s[0]);
					Message = s[1];
					bc.sentSingle(toid, Message);

				} // single
					// ��ѯ��Ϣ
				else if (IStrue && Message.startsWith("@searchInfo")) {
					s = Message.split("@searchInfo");
					int searchID = Integer.parseInt(s[1]);
					String msg = query.searchInfo(searchID);
					if (msg != null) {
						dos.writeUTF(msg);
					} else {
						dos.writeUTF("@searchInfonull");
					}
				}
				// ��Ӳ�ѯ�û�Ϊ����
				else if (IStrue && Message.startsWith("@searchAdd")) {
					s = Message.split("@searchAdd");
					int searchID = Integer.parseInt(s[1]);
					ClientThread cl = clients.get(searchID);

					if (cl != null && !query.isFriend(user.userID, searchID) && user.userID != searchID) {
						DataOutputStream searchdos = cl.dos;
						String msg = "@searchAdd" + user.userID + "@ID" + user.sign + "@sign" + user.photoID + "@pID"
								+ user.nickname + "@nickname" + user.sex + "@sex";
						searchdos.writeUTF(msg);
					} else {
						dos.writeUTF("@isAdd" + searchID + "@searchID" + "false");
					}
				}
				// �Ƿ����
				else if (IStrue && Message.startsWith("@isAdd")) {
					s = Message.split("@isAdd");
					s = s[1].split("@addID");
					int addID = Integer.parseInt(s[0]); // ���ҷ�
					DataOutputStream searchdos = clients.get(addID).dos;
					if (s[1].equals("true")) {
						if (searchdos != null) {
							String msg = "@isAdd" + user.userID + "@searchID" + "true";
							searchdos.writeUTF(msg);
						}
						query.addFriend(addID, user.userID);
					} else {
						String msg = "@isAdd" + user.userID + "@searchID" + "false";
						searchdos.writeUTF(msg);
					}
				} 
				//ɾ������
				else if (IStrue && Message.startsWith("@deletefriendsfsno")) {
					s = Message.split("@deletefriendsfsno");
					int fID = Integer.parseInt(s[1]);
					query.deleteFriend(fID, user.userID);
				}
				// �˳�
				else if (IStrue && Message.startsWith("@exit")) {
					synchronized (clients) {
						clients.remove(user.userID);
					}
					bc.sentGroupLogOut(user.userID);
					flag_start = false;
					query.userExit(user.userID, this);
					dis.close();
					dos.close();
					clientSocket.close();
					this.interrupt();
				} // exit
			} catch (IOException e) {
				synchronized (clients) {
					if (user != null)
						clients.remove(user.userID);
				}
				query.userExit(user.userID, this);
				flag_start = false;
				try {
					dis.close();
					dos.close();
					clientSocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				this.interrupt();
			}
		} // while
	}// run

	public void setFlag_start(boolean b) {
		flag_start = b;

	}// setFlag_exit

}
