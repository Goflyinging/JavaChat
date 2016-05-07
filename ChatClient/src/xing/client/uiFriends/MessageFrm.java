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
	/** ����� */
	private JPanel contentPane;
	/** ��С����ť */
	private JLabel minButton;
	/** ��󻯰�ť */
	private JLabel exitButton;
	/** ��ʾ��Ϣ����***�����У� */
	public JLabel titleLabel;
	/** �·����촰�� */
	private JPanel downPanel;
	/** ������壨�ɺϲ��� */
	public WebTabbedPane tabbedPane;
	/** ���꣨���ڼ�¼�����קʱ����갴����һ�̵����꣩ */
	private Point point = new Point();
	/** ��Ϣ���� */
	private DataManager dm = DataManager.getDataManager();
	/** ���ʹ�����Ϣ */
	String message;
	/** �������С */
	int Lwide = 400;
	int Lheight = 200;

	private Friends f;
	public JLabel fp;
	public JLabel fnickname;
	public JLabel fsign;
	public JPanel friendsContent; // �������
	JButton confirm; // ȷ�ϰ�ť
	JButton cancel; // ȡ����ť
	MessageFrm mf;

	public MessageFrm(Friends f) {
		mf = this;
		this.f = f;
		initGUI();
		initListener();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.requestFocus();
	} // ˽�л����췽��

	private void initGUI() {
		setSize(400, 200); // 660, 560
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (screenSize.getWidth() - Lwide) / 2, (int) (screenSize.getHeight() - Lheight) / 2);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// �����
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

		// ��֤���ںϲ����
		downPanel = new JPanel();
		downPanel.setOpaque(false);
		downPanel.setBounds(1, 40, 400, 160);
		downPanel.setLayout(new BorderLayout());
		contentPane.add(downPanel);
		// ��С����ť
		minButton = new JLabel();
		minButton.setBounds(334, 0, 31, 20);
		minButton.setIcon(new ImageIcon(UseTool.CRT_minimize));
		contentPane.add(minButton);
		// �˳�
		exitButton = new JLabel();
		exitButton.setIcon(new ImageIcon(UseTool.CRT_close));
		exitButton.setBounds(362, 0, 39, 20);
		contentPane.add(exitButton);
		// tabel
		tabbedPane = new WebTabbedPane();
		downPanel.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.setOpaque(false);
		tabbedPane.setTabbedPaneStyle(TabbedPaneStyle.attached);// �������߿�
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
		// ͷ��
		fp = new JLabel();
		String path = "image//Head//" + f.fpID + ".png";
		fp.setIcon(new ImageIcon(path));
		fp.setBounds(8, 4 + 7, 50, 50);
		fp.setBorder(UseTool.ORANGE_BORDER);
		friendsContent.add(fp);
		// ID
		JLabel fID = new JLabel();
		fID.setFont(UseTool.BASIC_FONT);
		fID.setText("I   D��"+f.fID);
		fID.setBounds(59 + 10, 5 + 5, 132, 19);
		//fID.setBorder(UseTool.ORANGE_BORDER);
		friendsContent.add(fID);
		// �ǳ�
		fnickname = new JLabel();
		fnickname.setFont(UseTool.BASIC_FONT);
		fnickname.setText("�ǳƣ�"+f.fnickname);// f.fnickname);
		fnickname.setBounds(59 + 10, 5 + 7 + 10 + 5, 132, 19);
		friendsContent.add(fnickname);
		//fnickname.setBorder(UseTool.ORANGE_BORDER);
		// �Ա�
		fsign = new JLabel();
		fsign.setFont(UseTool.BASIC_FONT);
		fsign.setText("�Ա�"+f.fsex);
		fsign.setBounds(59 + 10, 28 + 7 + 10, 132, 17);
		friendsContent.add(fsign);
		//fsign.setBorder(UseTool.ORANGE_BORDER);

		confirm = new JButton();
		confirm.setFont(UseTool.BASIC_FONT);
		confirm.setText("ȷ��");
		confirm.setBorder(null);
		confirm.setBounds(245, 21, 60, 30);
		friendsContent.add(confirm);
		confirm.setBorder(UseTool.ORANGE_BORDER);

		cancel = new JButton();
		cancel.setFont(UseTool.BASIC_FONT);
		cancel.setText("ȡ��");
		cancel.setBorder(null);
		cancel.setBounds(315, 21, 60, 30);
		friendsContent.add(cancel);
		cancel.setBorder(UseTool.ORANGE_BORDER);

		// 310 380 100 120
		frContent.add(friendsContent);
		tabbedPane.addTab("������֤", null, frContent);
		// tabbedPane.addTab(null, new ImageIcon(
		// new
		// ImageIcon(UseTool.MAIN_tab_boddy).getImage().getScaledInstance(35,
		// 35, Image.SCALE_DEFAULT)), frContent);
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
		// �������Ҽ��ر�
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				// // TODO ��ռ�¼��������
				// client.setRoom(null);
				// client.tabMap.clear();
			}
		});
		// ��С����ť�¼�
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
		// �˳���ť�¼�
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
		// ȷ����ť
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
