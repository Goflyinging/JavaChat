package xing.client.uiFriends;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import xing.client.uicommon.UseTool;
import xing.client.user.Friends;

public class FriendsNode extends DefaultMutableTreeNode {
	private Friends f;
	public JLabel fp;
	public JLabel fnickname;
	public JLabel fsign;
	public JPanel friendsContent ;  //�������
	boolean sel; //�Ƿ�ѡ��
	
	
	public FriendsNode(Friends friends) {
		this.f = friends;
		friendsContent = new JPanel();
		friendsContent.setLayout(null);
		friendsContent.setBackground(Color.WHITE);
		friendsContent.setPreferredSize(new Dimension(300, 50));
		
		//ͷ��
		fp = new JLabel();
		String path ="image//Head//"+f.fpID +".png";
		fp.setIcon(new ImageIcon(path));
		fp.setBounds(8, 4, 39, 42);
		friendsContent.add(fp);
		//�ǳ�
		fnickname = new JLabel();
		fnickname.setFont(UseTool.BASIC_FONT);
		fnickname.setText(f.fnickname);
		fnickname.setBounds(59, 5, 132, 19);
		friendsContent.add(fnickname);
		//����ǩ��
		fsign = new JLabel();
		fsign.setFont(UseTool.BASIC_FONT);
		fsign.setText(f.fsign);
		fsign.setBounds(59, 28, 132, 17);
		friendsContent.add(fsign);
	}
	public void setBackground(Color color){
		friendsContent.setBackground(color);
	}
	
	public Component getView() {
		return friendsContent;
	}
	public Friends getFriends(){
		return f;
	}
	public void setFdsfsno(int fsno){
		f.fsno = fsno;
	}
	
	public boolean isSel() {
		return sel;
	}
	public void setSel(boolean sel) {
		this.sel = sel;
	}

}
