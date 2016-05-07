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
	/** ����� */
	private JPanel content;
	/** ��С����ť */
	private JLabel minButton;
	/** �˳���ť */
	private JLabel exitButton;
	/** ������Ϣ��� */
	private JPanel baseInfo;
	/** ��ǣ����Ͻǣ� */
	private JLabel productInfo;
	/** ͷ�� */
	private JLabel picture;
	/** ǩ�� */
	private JLabel signature;
	/** �ǳ� */
	private JLabel nickName;

	// /** ������Ϣ��� */
	// private JPanel friendInfo;
	/** ��������� */
	private JPanel searchInfo;
	/** ������ */
	private JTextField searchText;
	/** ������ť */
	private MyButton searchButton;

	/** ������Ϣ��� */
	private JPanel userInfo;
	/** ���� Ⱥ ��� */
	WebTabbedPane tabPane;
	/** Ŀ��Ŀ¼ */
	String mpath = "Image//Head//";
	/** ������� �����ƶ����� */
	Point point = new Point();
	/** ϵͳ���� */
	private SystemTray tray;
	private TrayIcon icon;
	/** �������С */
	int Mwide = 300;
	int Mheight = 649;
	/** ���ݹ��� */
	DataManager dm = DataManager.getDataManager();
	/** ������� */
	ChatRoomTable croomt;

	public MainFrm() {
		croomt = ChatRoomTable.getRoomTable();
		initGUI();
		initTrayIcon();
		initListener();
	}

	public void initGUI() {
		// ģ������
		// user = new User();
		// user.userID = 800000;
		// user.nickname = "����";
		// user.photoID = 2;
		// user.pwd = null;
		// user.sex = "��";
		// user.sign = "����ңԶ��Զ��";
		// ��ȡ �û���Ϣ���ݵ�
		dm.startdatadealThread(); // ����������Ϣ�߳�

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (screenSize.getWidth() - Mwide) / 2, (int) (screenSize.getHeight() - Mheight) / 2);
		setSize(Mwide, Mheight);
		// setAlwaysOnTop(true);
		setUndecorated(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// �����
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

		// ������Ϣ���
		baseInfo = new JPanel();
		baseInfo.setLayout(null);
		baseInfo.setOpaque(false);
		baseInfo.setBounds(0, 0, 300, 118);
		baseInfo.setBorder(UseTool.GRAY_BORDER);
		// �˳���ť
		exitButton = new JLabel();
		baseInfo.add(exitButton);
		exitButton.setBounds(261, 0, 39, 20);
		exitButton.setIcon(new ImageIcon(UseTool.MAIN_close));
		// ��С��
		minButton = new JLabel();
		baseInfo.add(minButton);
		minButton.setBounds(233, 0, 31, 20);
		minButton.setIcon(new ImageIcon(UseTool.MAIN_minimize));
		// ���Ͻ� ����
		productInfo = new JLabel();
		baseInfo.add(productInfo);
		productInfo.setBounds(8, 0, 45, 20);
		productInfo.setText("����");
		// �û�ͷ��
		picture = new JLabel();
		baseInfo.add(picture);
		picture.setBounds(7, 33, 66, 66);
		picture.setBorder(UseTool.GRAY_BORDER);
		picture.setIcon(new ImageIcon((new ImageIcon(mpath + dm.user.photoID + ".gif")).getImage().getScaledInstance(65,
				65, Image.SCALE_DEFAULT)));
		// �ǳ�
		nickName = new JLabel();
		baseInfo.add(nickName);
		nickName.setFont(UseTool.BASIC_FONT2);
		nickName.setText(dm.user.nickname);
		nickName.setBounds(80, 30, 156, 32);
		// ����ǩ��
		signature = new JLabel();
		baseInfo.add(signature);
		signature.setFont(UseTool.BASIC_FONT);
		signature.setText(dm.user.sign);
		signature.setToolTipText(dm.user.sign);
		signature.setBounds(80, 64, 156, 32);
		// �������
		searchInfo = new JPanel();
		searchInfo.setLayout(null);
		searchInfo.setOpaque(false);
		searchInfo.setBounds(0, 117, 300, 32);
		searchInfo.setBorder(UseTool.GRAY_BORDER);
		// �����ı���
		searchText = new JTextField();
		searchText.setText(UseTool.SPACE + UseTool.SEARCH_TXT);
		searchText.setOpaque(false);
		// searchText.setFocusable(false);
		searchText.setBounds(1, 1, 253, 30);
		searchText.setBorder(BorderFactory.createEmptyBorder());
		searchInfo.add(searchText);
		// ������ť
		searchButton = new MyButton();
		searchButton.setBounds(254, 1, 45, 30);
		searchButton.setOpaque(false);
		searchButton.setBackground(Color.WHITE);
		searchButton.setIcon(new ImageIcon(UseTool.MAIN_search_icon));
		searchInfo.add(searchButton);
		// ������Ϣ���
		userInfo = new JPanel();
		userInfo.setLayout(new BorderLayout());
		userInfo.setOpaque(false);
		userInfo.setBounds(0, 148, 300, 500);
		userInfo.setBackground(Color.WHITE);

		// // ��壨���ѡ���ϵ�ˡ��Ự��
		tabPane = new WebTabbedPane();
		tabPane.setOpaque(false);
		tabPane.setTabbedPaneStyle(TabbedPaneStyle.attached);// �������߿�
		tabPane.setTabStretchType(TabStretchType.always);// ��Ӧ���
		tabPane.setTopBg(new Color(240, 240, 240, 60));
		tabPane.setBottomBg(new Color(255, 255, 255, 160));
		tabPane.setSelectedTopBg(new Color(240, 240, 255, 50));
		tabPane.setSelectedBottomBg(new Color(240, 240, 255, 50));
		tabPane.setBackground(new Color(255, 255, 255, 200));
		tabPane.setBorder(UseTool.GRAY_BORDER);
		userInfo.add(tabPane, BorderLayout.CENTER); // ��ӵ� ����Ⱥ ��Ϣ Jpanel
		// �����б�
		FriendsPanel fp = FriendsPanel.getfPanel();
		tabPane.addTab(null, new ImageIcon(
				new ImageIcon(UseTool.MAIN_tab_boddy).getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)), fp);
		// Ⱥ�б� ---------------����
		GroupPanel groupPanel = new GroupPanel();
		tabPane.addTab(null, new ImageIcon(
				new ImageIcon(UseTool.MAIN_tab_boddy).getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT)),
				groupPanel);

		content.add(baseInfo); // ������Ϣ���
		content.add(userInfo); // ���� Ⱥ ���
		content.add(searchInfo); // �������

	}

	private void initListener() {
		// �������¼�
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
		// ͷ�������¼����߿��ɫ��
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
		// �˳���ť�¼�
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
		// ����������¼�
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
				icon.setImageAutoSize(true); // �Զ���Ӧ��С
				icon.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if (e.getButton() == MouseEvent.BUTTON1) {
							setVisible(true);
							// ��ȡ����
							requestFocus();
						}
					}
				});

				PopupMenu pm = new PopupMenu();
				MenuItem mit = new MenuItem("�˳�");
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
