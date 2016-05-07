package xing.client.uiFriends;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import xing.client.uicommon.UseTool;
import xing.client.user.Subgroup;

public class subgroupNode extends DefaultMutableTreeNode {
	private Subgroup sbg = null; // ·Ö×é
	Icon icon = null;
	private JPanel subgroupContent = new JPanel();
	private JLabel name = null;
	private JLabel picture = null;
	
	public subgroupNode(Subgroup subgroup) {
		this.sbg = subgroup;
		subgroupContent.setLayout(null);
		subgroupContent.setBackground(Color.WHITE);
		subgroupContent.setPreferredSize(new Dimension(300, 25));
		
		picture = new JLabel();
		picture.setIcon(new ImageIcon("image//MainIcon//treeNode1.png"));
		picture.setBounds(6, 5, 20, 16);
		subgroupContent.add(picture);
		
		name = new JLabel();
		name.setFont(UseTool.BASIC_FONT);
		name.setText(sbg.sname);
		name.setBounds(19, 0, 132, 28);
		subgroupContent.add(name);

	}

	public Component getView() {
		return subgroupContent;
	}
	public void setIcon(Icon s){
		this.picture.setIcon(s);
	}

	public String getName() {
		return this.name.getText().trim();
	}

	public void setName(String name) {
		this.sbg.sname=name;
		this.name.setText(name);
	}
	public Subgroup getSbg(){
		return sbg;
	}
	


}
