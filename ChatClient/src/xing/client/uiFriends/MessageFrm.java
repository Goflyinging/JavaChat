package xing.client.uiFriends;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;

import xing.client.data.DataManager;
import xing.client.uiChatRoom.ChatRoomTable;
import xing.client.uicommon.UseTool;
import xing.client.user.Friends;

public class MessageFrm extends JFrame {
	/** 主面板 */
	private JPanel contentPane;
	/** 最小化按钮 */
	private JLabel minButton;
	/** 最大化按钮 */
	private JLabel exitButton;
	/** 提示信息（与***聊天中） */
	public JLabel titleLabel;
	/** 下方聊天窗体 */
	private JPanel downPanel;
	/** 窗体面板（可合并） */
	public WebTabbedPane tabbedPane;
	/** 坐标（用于记录鼠标拖拽时，鼠标按下那一刻的坐标） */
	private Point point = new Point();
	/** 消息管理 */
	private DataManager dm = DataManager.getDataManager();
	/** 发送处理消息 */
	String message;
	/** 主窗体大小 */
	int Lwide = 400;
	int Lheight = 200;

	private Friends f;
	public JLabel fp;
	public JLabel fnickname;
	public JLabel fsign;
	public JPanel friendsContent; // 好友面板
	JButton confirm; // 确认按钮
	JButton cancel; // 取消按钮
	MessageFrm mf;

	public MessageFrm(Friends f) {
		mf = this;
		this.f = f;
		initGUI();
		initListener();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.requestFocus();
	} // 私有化构造方法

	private void initGUI() {
		setSize(400, 200); // 660, 560
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (screenSize.getWidth() - Lwide) / 2, (int) (screenSize.getHeight() - Lheight) / 2);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 主面板
		contentPane = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				BufferedImage im = null;
				try {
					im = ImageIO.read(new File(UseTool.CRT_BACKGROUD));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				g.drawImage(im, 0, 0, 400, 200, this);
				this.setOpaque(false);
			}
		};
		contentPane.setLayout(null);
		contentPane.setBorder(UseTool.LIGHT_GRAY_BORDER);
		setContentPane(contentPane);

		// 验证窗口合并面板
		downPanel = new JPanel();
		downPanel.setOpaque(false);
		downPanel.setBounds(1, 40, 400, 160);
		downPanel.setLayout(new BorderLayout());
		contentPane.add(downPanel);
		// 最小化按钮
		minButton = new JLabel();
		minButton.setBounds(334, 0, 31, 20);
		minButton.setIcon(new ImageIcon(UseTool.CRT_minimize));
		contentPane.add(minButton);
		// 退出
		exitButton = new JLabel();
		exitButton.setIcon(new ImageIcon(UseTool.CRT_close));
		exitButton.setBounds(362, 0, 39, 20);
		contentPane.add(exitButton);
		// tabel
		tabbedPane = new WebTabbedPane();
		downPanel.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setOpaque(false);
		tabbedPane.setTabbedPaneStyle(TabbedPaneStyle.attached);// 不高亮边框
		tabbedPane.setTopBg(new Color(240, 240, 240, 60));
		tabbedPane.setBottomBg(new Color(255, 255, 255, 160));
		tabbedPane.setSelectedTopBg(new Color(240, 240, 255, 50));
		tabbedPane.setSelectedBottomBg(new Color(240, 240, 255, 50));
		tabbedPane.setBackground(new Color(255, 255, 255, 200));

		JPanel frContent = new JPanel();
		frContent.setLayout(null);
		frContent.setBorder(null);
		frContent.setOpaque(false);
		// friendsContent.setPreferredSize(new Dimension(300, 50));

		friendsContent = new JPanel();
		friendsContent.setLayout(null);
		friendsContent.setBorder(UseTool.LIGHT_GRAY_BORDER);
		friendsContent.setBackground(Color.WHITE);
		friendsContent.setBounds(5, 15, 390, 70);
		// 头像
		fp = new JLabel();
		String path = "image//Head//" + f.fpID + ".png";
		fp.setIcon(new ImageIcon(path));
		fp.setBounds(8, 4 + 7, 50, 50);
		fp.setBorder(UseTool.ORANGE_BORDER);
		friendsContent.add(fp);
		// ID
		JLabel fID = new JLabel();
		fID.setFont(UseTool.BASIC_FONT);
		fID.setText("I   D："+f.fID);
		fID.setBounds(59 + 10, 5 + 5, 132, 19);
		//fID.setBorder(UseTool.ORANGE_BORDER);
		friendsContent.add(fID);
		// 昵称
		fnickname = new JLabel();
		fnickname.setFont(UseTool.BASIC_FONT);
		fnickname.setText("昵称："+f.fnickname);// f.fnickname);
		fnickname.setBounds(59 + 10, 5 + 7 + 10 + 5, 132, 19);
		friendsContent.add(fnickname);
		//fnickname.setBorder(UseTool.ORANGE_BORDER);
		// 性别
		fsign = new JLabel();
		fsign.setFont(UseTool.BASIC_FONT);
		fsign.setText("性别："+f.fsex);
		fsign.setBounds(59 + 10, 28 + 7 + 10, 132, 17);
		friendsContent.add(fsign);
		//fsign.setBorder(UseTool.ORANGE_BORDER);

		confirm = new JButton();
		confirm.setFont(UseTool.BASIC_FONT);
		confirm.setText("确定");
		confirm.setBorder(null);
		confirm.setBounds(245, 21, 60, 30);
		friendsContent.add(confirm);
		confirm.setBorder(UseTool.ORANGE_BORDER);

		cancel = new JButton();
		cancel.setFont(UseTool.BASIC_FONT);
		cancel.setText("取消");
		cancel.setBorder(null);
		cancel.setBounds(315, 21, 60, 30);
		friendsContent.add(cancel);
		cancel.setBorder(UseTool.ORANGE_BORDER);

		// 310 380 100 120
		frContent.add(friendsContent);
		tabbedPane.addTab("好友验证", null, frContent);
		// tabbedPane.addTab(null, new ImageIcon(
		// new
		// ImageIcon(UseTool.MAIN_tab_boddy).getImage().getScaledInstance(35,
		// 35, Image.SCALE_DEFAULT)), frContent);
	}

	private void initListener() {
		// 主窗体事件
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				point.x = e.getX();
				point.y = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
			}
		});
		// 任务栏右键关闭
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				// // TODO 清空记录窗体数据
				// client.setRoom(null);
				// client.tabMap.clear();
			}
		});
		// 最小化按钮事件
		minButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				minButton.setIcon(new ImageIcon(UseTool.CRT_minimize));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				minButton.setIcon(new ImageIcon(UseTool.CRT_minimize_active));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});
		// 退出按钮事件
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(new ImageIcon(UseTool.CRT_close));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(new ImageIcon(UseTool.CRT_close_active));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				dispose();
			}
		});
		// 确定按钮
		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dm.fslist.add(f);
				FriendsPanel.getfPanel().addFriendNode(f);
				dm.sentIsAddFriend(true,f.fID);
				mf.dispose();
				
			}
		});
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dm.sentIsAddFriend(false,f.fID);
				mf.dispose();
			}
		});

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MessageFrm mf = new MessageFrm(null);
		mf.setVisible(true);
	}

}
