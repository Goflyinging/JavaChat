package xing.client.uiChatRoom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;

import xing.client.data.DataManager;
import xing.client.group.ChatAllFrame;
import xing.client.uicommon.UseTool;
import xing.client.user.Friends;

public class ChatRoomTable extends JFrame {
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
	/**�ƶ����ػ�*/
	private Point location = new Point();
	/** ��Ϣ���� */	
	private DataManager dm = DataManager.getDataManager();
	/** ����table ����ģʽ ͨ�� getChatRoomTable() ��ȡ */
	static ChatRoomTable croomt = new ChatRoomTable();
	/** �������С */
	public static HashMap<Integer, ChatRoomPanel> frooms = new HashMap<>(); // �����ر�
	int isGroupAdd = 0;
	int GroupIndex;
	int Lwide = 660;
	int Lheight = 560;

	private ChatRoomTable() {
		initGUI();
		initListener();
	} // ˽�л����췽��

	public static ChatRoomTable getRoomTable() {
		//croomt.setLocationRelativeTo(null);
		//croomt.requestFocus();
		return croomt;
	}

	private void initGUI() {
		setSize(660, 560);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		location.x = (int) (screenSize.getWidth() - Lwide) / 2;
		location.y = (int) (screenSize.getHeight() - Lheight) / 2;
		this.setLocation(location);
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
				g.drawImage(im, 0, 0, im.getWidth(), im.getHeight(), this);
				this.setOpaque(false);
			}
		};
		contentPane.setLayout(null);
		contentPane.setBorder(UseTool.LIGHT_GRAY_BORDER);
		setContentPane(contentPane);

		// ��������
		titleLabel = new JLabel();
		titleLabel.setFont(UseTool.BASIC_FONT);
		titleLabel.setBounds(10, 0, 619, 30);
		contentPane.add(titleLabel);

		// ���촰�ںϲ����
		downPanel = new JPanel();
		downPanel.setOpaque(false);
		downPanel.setBounds(1, 40, 658, 519);
		downPanel.setLayout(new BorderLayout());
		contentPane.add(downPanel);
		// ��С����ť
		minButton = new JLabel();
		minButton.setBounds(593, 0, 31, 20);
		minButton.setIcon(new ImageIcon(UseTool.CRT_minimize));
		contentPane.add(minButton);
		// �˳�
		exitButton = new JLabel();
		exitButton.setIcon(new ImageIcon(UseTool.CRT_close));
		exitButton.setBounds(621, 0, 39, 20);
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
				location.x = p.x + e.getX() - point.x;
				location.y = p.y + e.getY() - point.y;
				setLocation(location);
			}
		});
		// �������Ҽ��ر�
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
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

	}

	public void addFriendPanle(Friends f, String msg) {
		if (f == null) {
			return;
		}
		ChatRoomPanel crp = frooms.get(f.fID); // ��������Ƿ��Ѿ�����
		if (crp != null) {
			croomt.tabbedPane.setSelectedComponent(crp);		
		}else{
			crp = new ChatRoomPanel(f);
			croomt.tabbedPane.addTab(f.fnickname,null,crp);
			int i = tabbedPane.indexOfComponent(crp);
			tabbedPane.setTabComponentAt(i, new MyTabComponent(f.fID, f.fnickname,i));
			frooms.put(f.fID, crp);
		}
		if(!isShowing()){
			setVisible(true);
		}
		if (msg != null) {
			msg = f.fnickname+"  " +msg;
			crp.insertfdText(msg);
		}
		this.requestFocus();
	}

}
