package xing.client.group;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import xing.client.uicommon.UseTool;
import xing.client.user.Friends;

public class GroupListNode extends DefaultMutableTreeNode {

	public JPanel GroupContent ;  //∫√”—√Ê∞Â
	private JLabel name;   //»∫”—Í«≥∆
	
	
	public GroupListNode(String s) {
		GroupContent = new JPanel();
		GroupContent.setLayout(null);
		GroupContent.setBackground(Color.WHITE);
		GroupContent.setPreferredSize(new Dimension(100, 20));
		
		name = new JLabel();
		name.setFont(UseTool.BASIC_FONT);
		name.setText(s);
		name.setBounds(0, 0, 100, 20);
		GroupContent.add(name);
	}
	public void setBackground(Color color){
		GroupContent.setBackground(color);
	}
	
	public Component getView() {
		return GroupContent;
	}
	

}
