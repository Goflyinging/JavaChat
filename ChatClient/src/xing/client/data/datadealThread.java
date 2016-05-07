package xing.client.data;

import java.io.DataInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import xing.client.group.ChatAllFrame;
import xing.client.uiChatRoom.ChatRoomTable;
import xing.client.uiFriends.FriendsPanel;
import xing.client.uiFriends.MessageFrm;
import xing.client.uiFriends.SearchInfoPanel;
import xing.client.user.Friends;

public class datadealThread extends Thread {
	boolean flag_start = false;
	DataInputStream dis; // 输入流
	Friends fs; // 查找好友后返回的信息
	DataManager dm = DataManager.getDataManager();

	public datadealThread() {
		dis = dm.dis;
	}

	public void run() { // 运行接收信息线程
		String msg = null;
		String[] s = null;
		int fID;
		while (flag_start) {
			try {
				msg = dis.readUTF();
			} catch (IOException e) {
				flag_start = false;
				if (msg==null||!msg.startsWith("@serverexit")) {
					msg = null;
				}
				//e.printStackTrace();
			} // 阻塞
			if (!(msg == null)) {
				if (msg.contains("@sglfrom")) {
					s = msg.split("@sglfrom");
					fID = Integer.parseInt(s[0]);
					msg = s[1];
					Friends fd = dm.searchfriend(fID);
					if (fd != null) {
						ChatRoomTable.getRoomTable().addFriendPanle(fd, msg);
					}
				}else if(msg.startsWith("@all")){
					s = msg.split("@all");
					if(s[1]==null)
						break;
					ChatAllFrame chatAllFrame = ChatAllFrame.getChatAllFrame();
					chatAllFrame.insertfdText(s[1]);
				}
				else if (msg.startsWith("@searchInfo")) {
					SearchInfoPanel SIPanel = SearchInfoPanel.getInstance();
					s = msg.split("@searchInfo");
					if (!s[1].equals("null")) {
						fs = new Friends();
						s = s[1].split("@ID");
						fs.fID = Integer.parseInt(s[0]);
						s = s[1].split("@sign");
						fs.fsign = s[0];
						s = s[1].split("@pID");
						fs.fpID = Integer.parseInt(s[0]);
						s = s[1].split("@nickname");
						fs.fnickname = s[0];
						s = s[1].split("@sex");
						fs.fsex = s[0];
						fs.fstatus = Integer.parseInt(s[1]);
						fs.fsno = 0; // 默认分组
						SIPanel.setingText("");
						SIPanel.getInfo(fs);
					} else {
						SIPanel.setingText("您查找的ID不存在");
					}
				} else if (msg.startsWith("@searchAdd")) {
					fs = new Friends();
					s = msg.split("@searchAdd");
					s = s[1].split("@ID");
					fs.fID = Integer.parseInt(s[0]);
					s = s[1].split("@sign");
					fs.fsign = s[0];
					s = s[1].split("@pID");
					fs.fpID = Integer.parseInt(s[0]);
					s = s[1].split("@nickname");
					fs.fnickname = s[0];
					s = s[1].split("@sex");
					fs.fsex = s[0];
					fs.fstatus = 1;
					// fs.fstatus = Integer.parseInt(s[1]);
					fs.fsno = 0; // 默认分组
					MessageFrm messageFrm = new MessageFrm(fs);
				} else if (msg.startsWith("@isAdd")) {
					s = msg.split("@isAdd");
					s = s[1].split("@searchID");
					int searchID = Integer.parseInt(s[0]); // 查找方
					if (s[1].equals("true")) {
						dm.fslist.add(fs);
						FriendsPanel.getfPanel().addFriendNode(fs);
						JOptionPane.showMessageDialog(null, "添加好友" + searchID + "成功");
					} else {
						JOptionPane.showMessageDialog(null, "添加好友" + searchID + "失败");
					}
				} else if (msg.startsWith("@logon")) {
					s = msg.split("@logon");
					s = s[1].split("@fID");
					fs = new Friends();
					fs.fID = Integer.parseInt(s[0]);
					s = s[1].split("@fsno");
					fs.fsno = Integer.parseInt(s[0]);
					s = s[1].split("@fnickname");
					fs.fnickname = s[0];
					s = s[1].split("@fsign");
					fs.fsign = s[0];
					s = s[1].split("@fsex");
					fs.fsex = s[0];
					s = s[1].split("@fpID");
					fs.fpID = Integer.parseInt(s[0]);
					dm.fslist.add(fs);
					FriendsPanel.getfPanel().addlogonFriendNode(fs);;	
				}else if(msg.startsWith("@logout")){
					s=msg.split("@logout");
					fID = Integer.parseInt(s[1]);
					FriendsPanel.getfPanel().removeFnode(fID);	
				}else if(msg.startsWith("@grouplogin")){
					s = msg.split("@grouplogin");
					s = s[1].split("@userID");
					int userID = Integer.parseInt(s[0]);
					s=s[1].split("@nickname");
					String nickname =s[0];
					ChatAllFrame chatAllFrame = ChatAllFrame.getChatAllFrame();
					chatAllFrame.addNewNode(userID, nickname);
				}else if(msg.startsWith("@grouplogout")){
					s = msg.split("@grouplogout");
					s = s[1].split("@userID");
					int userID = Integer.parseInt(s[0]);
					ChatAllFrame chatAllFrame = ChatAllFrame.getChatAllFrame();
					chatAllFrame.removeNode(userID);
				}
				
				
			}//if msg!=null	
		}//while

	}

	public void setflag_start(boolean f) { // run while
		this.flag_start = f;
	}

	public boolean getFlag_start() {
		return this.flag_start;
	}

}
