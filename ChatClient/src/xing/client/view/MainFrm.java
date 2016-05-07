package xing.client.view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.alee.laf.tabbedpane.TabStretchType;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;

import xing.client.data.DataManager;
import xing.client.group.GroupPanel;
import xing.client.uiChatRoom.ChatRoomTable;
import xing.client.uiFriends.FriendsPanel;
import xing.client.uiFriends.SearchInfoPanel;
import xing.client.uicommon.MyButton;
import xing.client.uicommon.UseTool;

public class MainFrm extends JFrame {
	/** 主面板 */
	private JPanel content;
	/** 最小换按钮 */
	private JLabel minButton;
	/** 退出按钮 */
	private JLabel exitButton;
	/** 基本信息面板 */
	private JPanel baseInfo;
	/** 标记（左上角） */
	private JLabel productInfo;
	/** 头像 */
	private JLabel picture;
	/** 签名 */
	private JLabel signature;
	/** 昵称 */
	private JLabel nickName;

	// /** 好友信息面板 */
	// private JPanel friendInfo;
	/** 搜索框面板 */
	private JPanel searchInfo;
	/** 搜索框 */
	private JTextField searchText;
	/** 搜索按钮 */
	private MyButton searchButton;

	/** 好友信息面板 */
	private JPanel userInfo;
	/** 好友 群 面板 */
	WebTabbedPane tabPane;
	/** 目标目录 */
	String mpath = "Image//Head//";
	/** 鼠标坐标 用于移动界面 */
	Point point = new Point();
	/** 系统托盘 */
	private SystemTray tray;
	private TrayIcon icon;
	/** 主界面大小 */
	int Mwide = 300;
	int Mheight = 649;
	/** 数据管理 */
	DataManager dm = DataManager.getDataManager();
	/** 聊天面板 */
	ChatRoomTable croomt;

	public MainFrm() {
		croomt = ChatRoomTable.getRoomTable();
		initGUI();
		initTrayIcon();
		initListener();
	}

	public void initGUI() {
		// 模拟数据
		// user = new User();
		// user.userID = 800000;
		// user.nickname = "不错";
		// user.photoID = 2;
		// user.pwd = null;
		// user.sex = "男";
		// user.sign = "在那遥远的远方";
		// 获取 用户信息数据等
		dm.startdatadealThread(); // 启动接收消息线程

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (screenSize.getWidth() - Mwide) / 2, (int) (screenSize.getHeight() - Mheight) / 2);
		setSize(Mwide, Mheight);
		// setAlwaysOnTop(true);
		setUndecorated(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// 主面板
		content = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				BufferedImage im = null;
				try {
					im = ImageIO.read(new File(UseTool.MAIN_back5));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				g.drawImage(im, 0, 0, null);
				this.setOpaque(false);
			}
		};
		content.setLayout(null);
		add(content, BorderLayout.CENTER);

		// 基本信息面板
		baseInfo = new JPanel();
		baseInfo.setLayout(null);
		baseInfo.setOpaque(false);
		baseInfo.setBounds(0, 0, 300, 118);
		baseInfo.setBorder(UseTool.GRAY_BORDER);
		// 退出按钮
		exitButton = new JLabel();
		baseInfo.add(exitButton);
		exitButton.setBounds(261, 0, 39, 20);
		exitButton.setIcon(new ImageIcon(UseTool.MAIN_close));
		// 最小化
		minButton = new JLabel();
		baseInfo.add(minButton);
		minButton.setBounds(233, 0, 31, 20);
		minButton.setIcon(new ImageIcon(UseTool.MAIN_minimize));
		// 左上角 文字
		productInfo = new JLabel();
		baseInfo.add(productInfo);
		productInfo.setBounds(8, 0, 45, 20);
		productInfo.setText("轻聊");
		// 用户头像
		picture = new JLabel();
		baseInfo.add(picture);
		picture.setBounds(7, 33, 66, 66);
		picture.setBorder(UseTool.GRAY_BORDER);
		picture.setIcon(new ImageIcon((new ImageIcon(mpath + dm.user.photoID + ".gif")).getImage().getScaledInstance(65,
				65, Image.SCALE_DEFAULT)));
		// 昵称
		nickName = new JLabel();
		baseInfo.add(nickName);
		nickName.setFont(UseTool.BASIC_FONT2);
		nickName.setText(dm.user.nickname);
		nickName.setBounds(80, 30, 156, 32);
		// 个性签名
		signature = new JLabel();
		baseInfo.add(signature);
		signature.setFont(UseTool.BASIC_FONT);
		signature.setText(dm.user.sign);
		signature.setToolTipText(dm.user.sign);
		signature.setBounds(80, 64, 156, 32);
		// 搜索面板
		searchInfo = new JPanel();
		searchInfo.setLayout(null);
		searchInfo.setOpaque(false);
		searchInfo.setBounds(0, 117, 300, 32);
		searchInfo.setBorder(UseTool.GRAY_BORDER);
		// 搜索文本框
		searchText = new JTextField();
		searchText.setText(UseTool.SPACE + UseTool.SEARCH_TXT);
		searchText.setOpaque(false);
		// searchText.setFocusable(false);
		searchText.setBounds(1, 1, 253, 30);
		searchText.setBorder(BorderFactory.createEmptyBorder());
		searchInfo.add(searchText);
		// 搜索按钮
		searchButton = new MyButton();
		searchButton.setBounds(254, 1, 45, 30);
		searchButton.setOpaque(false);
		searchButton.setBackground(Color.WHITE);
		searchButton.setIcon(new ImageIcon(UseTool.MAIN_search_icon));
		searchInfo.add(searchButton);
		// 好友信息面板
		userInfo = new JPanel();
		userInfo.setLayout(new BorderLayout());
		userInfo.setOpaque(false);
		userInfo.setBounds(0, 148, 300, 500);
		userInfo.setBackground(Color.WHITE);

		// // 面板（好友、联系人、会话）
		tabPane = new WebTabbedPane();
		tabPane.setOpaque(false);
		tabPane.setTabbedPaneStyle(TabbedPaneStyle.attached);// 不高亮边框
		tabPane.setTabStretchType(TabStretchType.always);// 适应宽度
		tabPane.setTopBg(new Color(240, 240, 240, 60));
		tabPane.setBottomBg(new Color(255, 255, 255, 160));
		tabPane.setSelectedTopBg(new Color(240, 240, 255, 50));
		tabPane.setSelectedBottomBg(new Color(240, 240, 255, 50));
		tabPane.setBackground(new Color(255, 255, 255, 200));
		tabPane.setBorder(UseTool.GRAY_BORDER);
		userInfo.add(tabPane, BorderLayout.CENTER); // 添加到 好友群 信息 Jpanel
		// 好友列表
		FriendsPanel fp = FriendsPanel.getfPanel();
		tabPane.addTab(null, new ImageIcon(
				new ImageIcon(UseTool.MAIN_tab_boddy).getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)), fp);
		// 群列表 ---------------待做
		GroupPanel groupPanel = new GroupPanel();
		tabPane.addTab(null, new ImageIcon(
				new ImageIcon(UseTool.MAIN_tab_boddy).getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)),
				groupPanel);

		content.add(baseInfo); // 基本信息面板
		content.add(userInfo); // 好友 群 面板
		content.add(searchInfo); // 查找面板

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
		// 头像区域事件（边框变色）
		picture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				picture.setBorder(UseTool.LIGHT_GRAY_BORDER);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				picture.setBorder(UseTool.ORANGE_BORDER);
			}
		});

		minButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				minButton.setIcon(new ImageIcon(UseTool.MAIN_minimize));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				minButton.setIcon(new ImageIcon(UseTool.MAIN_minimize_active));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				setVisible(false);
			}
		});
		// 退出按钮事件
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(new ImageIcon(UseTool.MAIN_close));

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(new ImageIcon(UseTool.MAIN_close_active));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				dm.sentExitInfo();
				dm.close();
				croomt.dispose();
				tray.remove(icon);
				System.exit(0);
			}
		});
		// 搜索框鼠标事件
		searchText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				searchText.setText("");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				searchText.setText(UseTool.SPACE + UseTool.SEARCH_TXT);
			}

		});
		searchText.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					searchButton.doClick();

				}
			}
		});
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SearchInfoPanel sInfo = SearchInfoPanel.getInstance();
				String s = searchText.getText().trim();
				if (!s.equals("") && !s.equals(UseTool.SEARCH_TXT)) {
					sInfo.setSearchText(s);
				}
				sInfo.setVisible(true);

			}
		});
	}

	private void initTrayIcon() {
		if (SystemTray.isSupported()) {
			try {
				tray = SystemTray.getSystemTray();
				icon = new TrayIcon(new ImageIcon(UseTool.MAIN_qq_icon).getImage(), dm.user.nickname);
				icon.setImageAutoSize(true); // 自动适应大小
				icon.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if (e.getButton() == MouseEvent.BUTTON1) {
							setVisible(true);
							// 获取焦点
							requestFocus();
						}
					}
				});

				PopupMenu pm = new PopupMenu();
				MenuItem mit = new MenuItem("退出");
				mit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tray.remove(icon);
						System.exit(0);
					}
				});
				pm.add(mit);
				icon.setPopupMenu(pm);
				tray.add(icon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
	}
	// public static void main(String[] args) {
	// MainFrm mf = new MainFrm();
	// mf.setVisible(true);
	// }

}
