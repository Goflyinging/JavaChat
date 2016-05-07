package xing.client.uiChatRoom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import xing.client.data.DataManager;
import xing.client.uicommon.UseTool;
import xing.client.user.Friends;
import xing.client.user.User;

public class ChatRoomPanel extends JPanel {
	/** �û���Ϣ */
	User user;
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
	/** ������ť */
	private JLabel screen;
	/** ������ť */
	private JLabel shake;
	/** ���鰴ť */
	private JLabel emoticon;
	/** ���尴ť */
	private JLabel textFont;
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
	/** ��Ϣ���� */
	private DataManager dm = DataManager.getDataManager();

	public ChatRoomPanel(Friends f) {
		this.friend = f;
		initGUI();
		initListener();
	}

	/** ��ʼ��������� */
	private void initGUI() {

		// ģ������
		// friend = new Friends();
		// friend.fsno = 0;
		// friend.fnickname = "haha";
		// friend.fpID = 2;
		// friend.fsex = "Ů";
		// friend.fsign = "aaa";
		// friend.fstatus = 0;
		// friend.fID = 1111;
		// user = new User();
		// user.userID = 80000;
		// user.photoID = 3;
		// user.nickname = "lalal";
		// user.sex = "��";
		// user.pwd = null;
		// user.sign = "lalalal";

		// �������
		setLayout(null);
		setOpaque(false);
		setSize(650, 445);
		// ������Ϣ���
		friendInfoPane = new JPanel();
		friendInfoPane.setBounds(0, 0, 635, 70);
		friendInfoPane.setLayout(null);
		friendInfoPane.setOpaque(false);
		add(friendInfoPane);
		// ͷ��
		picture = new JLabel();
		picture.setBounds(3, 10, 50, 50);
		picture.setBorder(UseTool.LIGHT_GRAY_BORDER);
		picture.setIcon(new ImageIcon((new ImageIcon("image//Head//" + friend.fpID + ".gif")).getImage()
				.getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
		friendInfoPane.add(picture);
		// �ǳ�
		nickName = new JLabel();
		nickName.setFont(UseTool.BASIC_FONT);
		nickName.setText(friend.fnickname);
		nickName.setBounds(70, 10, 402, 25);
		friendInfoPane.add(nickName);
		// ǩ��
		sign = new JLabel();
		sign.setFont(UseTool.BASIC_FONT);
		sign.setBounds(70, 35, 402, 25);
		sign.setText(friend.fsign);
		friendInfoPane.add(sign);

		// ��ʷ��¼
		history = new JPanel();
		add(history);
		history.setBounds(3, 70, 650, 240);
		history.setOpaque(false);
		history.setLayout(new BorderLayout());

		historyScroll = new JScrollPane();
		history.add(historyScroll, BorderLayout.CENTER);
		historyTextPane = new JTextPane();
		historyTextPane.setEditable(false);// ������༭

		historyScroll.setViewportView(historyTextPane);		// historyScroll.getVerticalScrollBar().setUI(new MyScrollBarUI());
		historyScroll.setBorder(UseTool.LIGHT_GRAY_BORDER);

		// ������
		tools = new JPanel();
		tools.setLayout(null);
		tools.setOpaque(false);
		tools.setBounds(0, 312, 650, 27);
		add(tools);

		// �����
		input = new JPanel();
		input.setBounds(3, 340, 650, 100);
		input.setLayout(new BorderLayout());
		inputScroll = new JScrollPane();
		input.add(inputScroll);
		add(input);
		inputTextPane = new JTextPane();
		inputScroll.setViewportView(inputTextPane);
		// inputScroll.getVerticalScrollBar().setUI(new MyScrollBarUI());
		inputScroll.setBorder(UseTool.LIGHT_GRAY_BORDER);
		inputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// ȡ����ť���رգ�
		quitButton = new JButton();
		add(quitButton);
		quitButton.setBounds(460, 446, 93, 30);
		quitButton.setBorder(UseTool.LIGHT_GRAY_BORDER);
		quitButton.setIcon(new ImageIcon(UseTool.CRT_quitButton));

		// ���Ͱ�ť
		sendButton = new JButton();
		add(sendButton);
		sendButton.setBounds(560, 446, 93, 30);
		sendButton.setBorder(UseTool.LIGHT_GRAY_BORDER);
		sendButton.setIcon(new ImageIcon(UseTool.CRT_sendButton));

	}

	private void initListener() {
		// �س�������Ϣ
		inputTextPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
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
					dm.sentChatsingle(msg, friend.fID);
					insertMyText(msg);
					inputTextPane.setText("");
				}
			}
		});

	}

	public String MsgAddDate(String msg) {
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
		String date = form.format(new Date());
		msg = date + "\n" + "  " + msg +"\n" ;
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



}
