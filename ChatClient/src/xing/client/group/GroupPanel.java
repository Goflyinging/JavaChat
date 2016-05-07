package xing.client.group;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import xing.client.uiChatRoom.ChatRoomTable;
import xing.client.uicommon.UseTool;

public class GroupPanel extends JPanel {

	private JPanel groupContent;
	private JLabel fp;
	private JLabel groupName;
	private JLabel groupSign;
	private JLabel picture;


	/**
	 * Create the panel.
	 */
	public GroupPanel() {

		setBounds(0, 200, 300, 450);
		setLayout(null);
		setBorder(null);
		setBackground(Color.WHITE);

		groupContent = new JPanel();
		groupContent.setBounds(0, 22, 300, 54);
		groupContent.setLayout(null);
		groupContent.setBackground(Color.WHITE);
		groupContent.setBorder(UseTool.LIGHT_GRAY_BORDER);

		// 头像
		fp = new JLabel();
		String path = "image//Head//" + 1 + ".png";
		fp.setIcon(new ImageIcon(path));
		fp.setBounds(8, 4, 39, 42);
		groupContent.add(fp);
		// 昵称
		groupName = new JLabel("所有人");
		groupName.setFont(UseTool.BASIC_FONT);
		groupName.setBounds(59, 5, 83, 19);
		groupContent.add(groupName);
		// 个性签名
		groupSign = new JLabel();
		groupSign.setText(
				"\u5728\u8FD9\u4E2A\u7FA4\u91CC\u4F60\u53EF\u4EE5\u548C\u6240\u6709\u5728\u7EBF\u7684\u4EBA\u804A\u5929");
		groupSign.setFont(UseTool.BASIC_FONT);
		groupSign.setBounds(59, 28, 231, 17);
		groupContent.add(groupSign);

		add(groupContent);

		JPanel panel = new JPanel(); // 我的群
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 300, 23);
		add(panel);
		panel.setLayout(null);

		picture = new JLabel();
		picture.setIcon(new ImageIcon("image//MainIcon//treeNode1.png"));
		picture.setBounds(6, 5, 20, 16);
		panel.add(picture);

		JLabel groupLable = new JLabel("\u6211\u7684\u7FA4");
		groupLable.setBounds(22, 5, 121, 16);
		panel.add(groupLable);
		groupLable.setFont(UseTool.BASIC_FONT);

		groupContent.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {

				groupContent.setBackground(Color.WHITE);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				groupContent.setBackground(new Color(109, 196, 225));

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					ChatAllFrame chatAll = ChatAllFrame.getChatAllFrame();
					chatAll.setVisible(true);

				}

			}
		});
	}
}
