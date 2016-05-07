package xing.client.uiChatRoom;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import xing.client.uicommon.UseTool;

public class MyTabComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel text;
	private JLabel icon;
	private int fID ;
	private  int i; //×é¼þindex
	private String fnickname;
	ChatRoomTable croomt = ChatRoomTable.getRoomTable();
	public MyTabComponent(int fID, String fnickname,int i) {
		this.fID = fID;
		this.fnickname = fnickname;
		this.i = i;
		initGUI();
		initListener();
	}
	
	private void initGUI() {
		setOpaque(false);
		setLayout(new FlowLayout());
		text = new JLabel(fnickname);
		icon = new JLabel(new ImageIcon(UseTool.CRT_empty));
		add(text);
		add(icon);
	}

	private void initListener() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				icon.setIcon(new ImageIcon(UseTool.CRT_empty));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				icon.setIcon(new ImageIcon(UseTool.CRT_closeTab));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				croomt.tabbedPane.setSelectedIndex(i);
			}
		});
		icon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				icon.setIcon(new ImageIcon(UseTool.CRT_empty));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				icon.setIcon(new ImageIcon(UseTool.CRT_closeTab_active));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				int n  = croomt.frooms.size();
				croomt.tabbedPane.remove(i);
				croomt.frooms.remove(fID);
				if(n<=1){
					croomt.setVisible(false);
				}
				
				
			}
		});
	}
	
}
