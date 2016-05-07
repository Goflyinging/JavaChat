package xing.client.group;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import xing.client.data.DataManager;
import xing.client.uiChatRoom.ChatRoomPanel;
import xing.client.uiChatRoom.MyTabComponent;
import xing.client.uicommon.MyTreeUI;
import xing.client.uicommon.UseTool;
import xing.client.user.Friends;

/**
 * Ⱥ�����ң������������ʾ���������û������Һ��������ߵ��û���������
 * 
 * @author lxing
 *
 */

public class ChatAllFrame extends JFrame {

	JPanel contentPane; // �����
	/** ������Ϣ��� */
	private JPanel friendInfoPane;
	/** ������Ϣ */
	Friends friend;
	/** ����ͷ�� */
	private JLabel picture;
	/** �����ǳ� */
	private JLabel nickName;
	/** ����ǩ�� */
	private JLabel sign;
	/** ��ʷ��Ϣ��� */
	private JPanel history;
	/** ��ʷ��Ϣ������ */
	private JScrollPane historyScroll;
	/** ��ʷ��Ϣ���� */
	public JTextPane historyTextPane;
	/** ������� */
	private JPanel tools;
	/** ������Ϣ��� */
	private JPanel input;
	/** ������Ϣ������ */
	private JScrollPane inputScroll;
	/** ������Ϣ���� */
	private JTextPane inputTextPane;
	/** ȡ����ť */
	private JButton quitButton;
	/** ���Ͱ�ť */
	private JButton sendButton;
	/** ������Ϣ */
	private String msg;
	/** model */
	DefaultTreeModel model;
	/** ��������� */
	Point location = new Point();
	Point point = new Point();
	private int Lwide = 657;
	private int Lheight = 484;
	private JLabel minButton;
	private JLabel exitButton;
	private JLabel label;
	DefaultMutableTreeNode top; // Ⱥ��tree���ڵ�
	HashMap<Integer, GroupListNode> groupNodeMap = new HashMap<>();
	private JTree tree;
	private static ChatAllFrame chatAllFrame = new ChatAllFrame();

	public static ChatAllFrame getChatAllFrame() {
		return chatAllFrame;
	}

	private ChatAllFrame() {
		initGUI();
		initListener();
	}

	/** ��ʼ��������� */
	private void initGUI() {
		contentPane = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				BufferedImage im = null;
				try {
					im = ImageIO.read(new File(UseTool.CRT_BACKGROUD));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g.drawImage(im, 0, 0, im.getWidth(), im.getHeight(), this);
				this.setOpaque(false);
			}
		};
		contentPane.setOpaque(false);
		contentPane.setLayout(null);
		contentPane.setBorder(UseTool.LIGHT_GRAY_BORDER);
		setContentPane(contentPane);

		// �������
		getContentPane().setLayout(null);
		setSize(657, 484);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		location.x = (int) (screenSize.getWidth() - Lwide) / 2;
		location.y = (int) (screenSize.getHeight() - Lheight) / 2;
		this.setLocation(location);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// ��С����ť
		minButton = new JLabel();
		minButton.setBounds(590, 0, 31, 20);
		minButton.setIcon(new ImageIcon(UseTool.CRT_minimize));
		getContentPane().add(minButton);
		// �˳�
		exitButton = new JLabel();
		exitButton.setIcon(new ImageIcon(UseTool.CRT_close));
		exitButton.setBounds(618, 0, 39, 20);
		getContentPane().add(exitButton);
		// ������Ϣ���
		friendInfoPane = new JPanel();
		friendInfoPane.setBounds(0, 0, 500, 70);
		friendInfoPane.setLayout(null);
		friendInfoPane.setOpaque(false);
		getContentPane().add(friendInfoPane);
		// ͷ��
		picture = new JLabel();
		picture.setBounds(3, 10, 50, 50);
		picture.setBorder(UseTool.LIGHT_GRAY_BORDER);
		picture.setIcon(new ImageIcon((new ImageIcon("image//Head//" + 1 + ".gif")).getImage().getScaledInstance(50, 50,
				Image.SCALE_DEFAULT)));
		friendInfoPane.add(picture);
		// �ǳ�
		nickName = new JLabel();
		nickName.setFont(UseTool.BASIC_FONT);
		nickName.setText("������");
		nickName.setBounds(70, 10, 402, 25);
		friendInfoPane.add(nickName);
		// ǩ��
		sign = new JLabel();
		sign.setFont(UseTool.BASIC_FONT);
		sign.setBounds(70, 35, 402, 25);
		sign.setText("����������Ժ�����������");
		friendInfoPane.add(sign);

		// ��ʷ��¼
		history = new JPanel();
		getContentPane().add(history);
		history.setOpaque(false);
		history.setBounds(3, 70, 518, 240);
		history.setLayout(new BorderLayout());

		historyScroll = new JScrollPane();
		history.add(historyScroll, BorderLayout.CENTER);
		historyTextPane = new JTextPane();
		historyTextPane.setEditable(false);// ������༭

		historyScroll.setViewportView(historyTextPane);
		historyScroll.setBorder(UseTool.LIGHT_GRAY_BORDER);

		// ������
		tools = new JPanel();
		tools.setLayout(null);
		tools.setOpaque(false);
		tools.setBounds(0, 312, 521, 27);
		getContentPane().add(tools);

		// �����
		input = new JPanel();
		input.setBounds(3, 340, 518, 100);
		input.setLayout(new BorderLayout());
		inputScroll = new JScrollPane();
		input.add(inputScroll);
		getContentPane().add(input);
		inputTextPane = new JTextPane();
		inputScroll.setViewportView(inputTextPane);
		inputScroll.setBorder(UseTool.LIGHT_GRAY_BORDER);
		inputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// ȡ����ť���رգ�
		quitButton = new JButton();
		getContentPane().add(quitButton);
		quitButton.setBounds(310, 446, 93, 30);
		quitButton.setBorder(UseTool.LIGHT_GRAY_BORDER);
		quitButton.setIcon(new ImageIcon(UseTool.CRT_quitButton));

		// ���Ͱ�ť
		sendButton = new JButton();
		getContentPane().add(sendButton);
		sendButton.setBounds(428, 446, 93, 30);
		sendButton.setBorder(UseTool.LIGHT_GRAY_BORDER);
		sendButton.setIcon(new ImageIcon(UseTool.CRT_sendButton));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(528, 70, 112, 406);
		getContentPane().add(scrollPane);

		top = new DefaultMutableTreeNode("top");
		addGroupUsers();

		model = new DefaultTreeModel(top);
		inputScroll.setBorder(UseTool.LIGHT_GRAY_BORDER);
		inputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tree = new JTree(model);
		tree.setRootVisible(false); // ���ظ��ڵ�
		tree.setUI(new MyTreeUI()); // ��UI ȥ�߿����
		tree.setInvokesStopCellEditing(true);// �޸Ľڵ�����֮����Ч
		tree.setToggleClickCount(1);// �������
		tree.setAutoscrolls(true);
		tree.setCellRenderer(new GroupListCellRenderer());
		tree.setBounds(530, 72, 110, 402);
		scrollPane.setViewportView(tree);

		label = new JLabel("\u5728\u7EBF\u7FA4\u53CB\uFF1A");
		label.setBounds(528, 45, 66, 20);
		label.setFont(UseTool.BASIC_FONT);
		label.setBackground(Color.WHITE);
		getContentPane().add(label);

	}

	private void initListener() {
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
		// �س�������Ϣ
		inputTextPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendButton.doClick();

				}
			}
		});

		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				msg = inputTextPane.getText().trim();
				// ������Ϣ
				if (msg == null && msg.equals("")) {
					JOptionPane.showMessageDialog(null, "�������ݲ���Ϊ�գ����������룡");
				} else {
					// send
					msg = MsgAddDate(msg);
					DataManager dm = DataManager.getDataManager();
					dm.sentChatAll(msg);
					insertMyText(msg);
					inputTextPane.setText("");
				}
			}
		});

	}

	//  Ϊ���͵���Ϣ ��� ���ڴ���
	public String MsgAddDate(String msg) {
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
		String date = form.format(new Date());
		msg = date + "\n" + "  " + msg + "\n";
		return msg;
	}

	public void insertMyText(String str) { // �����ı���Ϣ
		str = "��" + "  " + str;
		Document doc = historyTextPane.getStyledDocument();
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, Color.BLACK);// ����������ɫ
		StyleConstants.setFontSize(set, 12);// ���������С
		try {
			doc.insertString(doc.getLength(), str, set);// ��������
			historyTextPane.setCaretPosition(historyTextPane.getDocument().getLength());
		} catch (BadLocationException e) {

		}
	}//

	public void insertfdText(String str) { // �����ı���Ϣ
		Document doc = historyTextPane.getStyledDocument();
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, Color.BLACK);// ����������ɫ
		StyleConstants.setFontSize(set, 12);// ���������С
		try {
			doc.insertString(doc.getLength(), str, set);// ��������
			historyTextPane.setCaretPosition(historyTextPane.getDocument().getLength());
		} catch (BadLocationException e) {

		}

	}//

	public void addGroupUsers() { // ����û�
		// TreeMap<Integer, String> groups = new TreeMap<>(); // Ⱥ��Ա��
		// groups.put(80000, "����˭");
		// groups.put(80001, "����˭");
		// groups.put(80002, "����˭");
		for (Map.Entry<Integer, String> m : DataManager.groups.entrySet()) {
			int ID = m.getKey();
			String nickname = m.getValue();
			GroupListNode node = new GroupListNode(nickname + "(" + ID + ")");
			top.add(node);
			groupNodeMap.put(ID, node);
		}

	}

	// �����û�����ʱ ���Ҳ��б��tree��������ߵ��û�
	public void addNewNode(int ID, String nickname) {
		GroupListNode node = new GroupListNode(nickname + "(" + ID + ")");
		model.insertNodeInto(node, top, top.getChildCount());
		model.reload();
		groupNodeMap.put(ID, node);
		DataManager.groups.put(ID, nickname);

	}

	// �����û�����ʱ ���Ҳ��б��tree���Ƴ����ߵ��û�
	public void removeNode(int ID) {
		GroupListNode node = groupNodeMap.get(ID);
		if (node != null)
			model.removeNodeFromParent(node);
		groupNodeMap.remove(ID);
		DataManager.groups.remove(ID);

	}
}
