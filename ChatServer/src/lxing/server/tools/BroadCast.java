package lxing.server.tools;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map.Entry;
import lxing.server.thread.ClientThread;

public class BroadCast {

	int sentUserID;
	int receiveUserID;
	public DataOutputStream dos;// 输出流 给用户发送信息

	public synchronized void sentSingle(int toID, String Message) {
		ClientThread clt = ClientThread.clients.get(toID);
		if (clt == null) {
			return;
		}
		dos = clt.dos;
		try {
			dos.writeUTF(Message);
		} catch (IOException e) {

		}
	}

	public synchronized void sentAll(String Message, int userID) {
		for (Entry<Integer, ClientThread> m : ClientThread.clients.entrySet()) {
			if (m.getKey() == userID)
				continue;
			dos = m.getValue().dos;
			try {
				dos.writeUTF(Message);
			} catch (IOException e) {

			}
		}
	}

	public synchronized void sentGroupLogin(int userID, String nickname) {
		
		for (Entry<Integer, ClientThread> m : ClientThread.clients.entrySet()) {
			dos = m.getValue().dos;
			try {
				dos.writeUTF("@grouplogin" + userID + "@userID" + nickname + "@nickname");
			} catch (IOException e) {

			}
		}

	}

	public synchronized void sentGroupLogOut(int userID) {
		for (Entry<Integer, ClientThread> m : ClientThread.clients.entrySet()) {
			dos = m.getValue().dos;
			try {
				dos.writeUTF("@grouplogout" + userID + "@userID" );
			} catch (IOException e) {

			}
		}

	}

}
