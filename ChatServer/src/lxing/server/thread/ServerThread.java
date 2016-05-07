package lxing.server.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lxing.server.view.ServerFrame;

public class ServerThread extends Thread {

	public ServerSocket serverSocket; // 服务器socket
	public int Port = 10086; // 端口
	public ServerFrame serverFrame;
	private boolean flag_start = false; // while 循环终止条件

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
		Socket socket; // 接收客户端socket
		while (flag_start) {
			if (serverSocket.isClosed()) { // 是否已经关闭
				flag_start = false; // 关闭则终止循环
			} else {
				try {
					socket = serverSocket.accept();
				} catch (IOException e) {
					socket = null;
					flag_start = false;
				}
				if (socket != null) {
					// 开启用户进程
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
