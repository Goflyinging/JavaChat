package lxing.server.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lxing.server.view.ServerFrame;

public class ServerThread extends Thread {

	public ServerSocket serverSocket; // ������socket
	public int Port = 10086; // �˿�
	public ServerFrame serverFrame;
	private boolean flag_start = false; // while ѭ����ֹ����

	public ServerThread(ServerFrame serverFrame) {
      this.serverFrame = serverFrame;
		try {
			serverSocket = new ServerSocket(Port);
		} catch (IOException e) {
			this.serverFrame.setStartAndStopUnable();
			System.exit(0);
		}
	}

	@Override
	public void run() {
		Socket socket; // ���տͻ���socket
		while (flag_start) {
			if (serverSocket.isClosed()) { // �Ƿ��Ѿ��ر�
				flag_start = false; // �ر�����ֹѭ��
			} else {
				try {
					socket = serverSocket.accept();
				} catch (IOException e) {
					socket = null;
					flag_start = false;
				}
				if (socket != null) {
					// �����û�����
					ClientThread clientThread = new ClientThread(socket);
					clientThread.setFlag_start(true);
					clientThread.start();				
				}
			} // else
		} // while

	}// run

	public void stopServer() {
		if (this.isAlive()) {
			try {
				serverSocket.close();
				setFlag_start(false);
				this.interrupt();
			} catch (IOException e) {

			}
		}

	}// stopServer

	public void setFlag_start(boolean b) {
		flag_start = b;

	}// setflag_start

}
