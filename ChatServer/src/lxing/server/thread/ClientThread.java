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
	public DataInputStream dis; // 输入流 接收用户发来的信息
	public DataOutputStream dos;// 输出流 给用户发送信息
	User user;
	public static Map<Integer, ClientThread> clients = Collections
			.synchronizedMap(new HashMap<Integer, ClientThread>()); // 存放对应线程
	private boolean flag_start = false;
	private boolean IStrue = true;
	Query query = new Query(); // 与数据库 通信

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
				// 注册
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
				// 登录
				else if (Message.startsWith("@login")) {
					s = Message.split("@login");
					s = s[1].split("@userID");
					int userID = Integer.parseInt(s[0]);
					s = s[1].split("@pwd");
					String userPW = s[0];
					IStrue = query.Islogin(userID, userPW);
					dos.writeUTF("@login" + IStrue); // 发消息给用户
					if (IStrue) { // 验证成功处理 信息
						user = query.sentUserInfo(userID, this); // 发送用户个人消息
						query.sentSubgroup(userID, this);// 发送用户分组消息
						query.sentFriendsInfo(userID, this);// 发送用户好友信息
						query.sentGroupInfo(userID, this);// 发送用户群信息
						dos.writeUTF("@loginover");
						query.senttologFriendsInfo(user, this);// 对好友发送上线信息
						bc.sentGroupLogin(user.userID, user.nickname);
						synchronized (clients) {
							clients.put(userID, this);
						}
					} else { // 验证失败 关闭
						flag_start = false;
						dis.close();
						dos.close();
						this.clientSocket.close();
						this.interrupt();
					}

				} //
					// 群聊天
				else if (IStrue && Message.startsWith("@all")) {
					s = Message.split("@all");
					bc.sentAll("@all" + user.nickname + "(" + user.userID + ")  " + s[1], user.userID);
				} // all
					// 私聊
				else if (IStrue && Message.contains("@single")) {
					s = Message.split("@single");
					int toid = Integer.parseInt(s[0]);
					Message = s[1];
					bc.sentSingle(toid, Message);

				} // single
					// 查询信息
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
				// 添加查询用户为好友
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
				// 是否添加
				else if (IStrue && Message.startsWith("@isAdd")) {
					s = Message.split("@isAdd");
					s = s[1].split("@addID");
					int addID = Integer.parseInt(s[0]); // 查找方
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
				//删除好友
				else if (IStrue && Message.startsWith("@deletefriendsfsno")) {
					s = Message.split("@deletefriendsfsno");
					int fID = Integer.parseInt(s[1]);
					query.deleteFriend(fID, user.userID);
				}
				// 退出
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
