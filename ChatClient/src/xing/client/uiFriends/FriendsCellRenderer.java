package xing.client.uiFriends;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;


public class FriendsCellRenderer extends DefaultTreeCellRenderer {
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {
		if (value instanceof FriendsNode) {
			//((FriendsNode) value).setSel(sel);	
			return ((FriendsNode) value).getView();
		} else if (value instanceof subgroupNode) {
			if (expanded) {
				ImageIcon icon = new ImageIcon("image//MainIcon//treeNode1.png");
				((subgroupNode) value).setIcon(icon);
				// 设置图片路径
			} else {
				// 设置图标
				ImageIcon icon = new ImageIcon("image//MainIcon//treeNode.png");
				((subgroupNode) value).setIcon(icon);
			}
			return ((subgroupNode) value).getView();

		}
		return this;

	}


}
