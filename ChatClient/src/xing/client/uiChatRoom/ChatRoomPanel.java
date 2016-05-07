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
	/** 用户信息 */
	User user;
	/** 好友信息面板 */
	private JPanel friendInfoPane;
	/** 好友信息 */
	Friends friend;
	/** 好友头像 */
	private JLabel picture;
	/** 好友昵称 */
	private JLabel nickName;
	/** 好友签名 */
	private JLabel sign;
	/** 历史消息面板 */
	private JPanel history;
	/** 历史消息滚动条 */
	private JScrollPane historyScroll;
	/** 历史消息区域 */
	public JTextPane historyTextPane;
	/** 工具面板 */
	private JPanel tools;
	/** 截屏按钮 */
	private JLabel screen;
	/** 抖动按钮 */
	private JLabel shake;
	/** 表情按钮 */
	private JLabel emoticon;
	/** 字体按钮 */
	private JLabel textFont;
	/** 输入消息面板 */
	private JPanel input;
	/** 输入消息滚动条 */
	private JScrollPane inputScroll;
	/** 输入消息区域 */
	private JTextPane inputTextPane;
	/** 取消按钮 */
	private JButton quitButton;
	/** 发送按钮 */
	private JButton sendButton;
	/** 输入信息 */
	private String msg;
	/** 消息管理 */
	private DataManager dm = DataManager.getDataManager();

	public ChatRoomPanel(Friends f) {
		this.friend = f;
		initGUI();
		initListener();
	}

	/** 初始化聊天面板 */
	private void initGUI() {

		// 模拟数据
		// friend = new Friends();
		// friend.fsno = 0;
		// friend.fnickname = "haha";
		// friend.fpID = 2;
		// friend.fsex = "女";
		// friend.fsign = "aaa";
		// friend.fstatus = 0;
		// friend.fID = 1111;
		// user = new User();
		// user.userID = 80000;
		// user.photoID = 3;
		// user.nickname = "lalal";
		// user.sex = "男";
		// user.pwd = null;
		// user.sign = "lalalal";

		// 面板设置
		setLayout(null);
		setOpaque(false);
		setSize(650, 445);
		// 好友信息面板
		friendInfoPane = new JPanel();
		friendInfoPane.setBounds(0, 0, 635, 70);
		friendInfoPane.setLayout(null);
		friendInfoPane.setOpaque(false);
		add(friendInfoPane);
		// 头像
		picture = new JLabel();
		picture.setBounds(3, 10, 50, 50);
		picture.setBorder(UseTool.LIGHT_GRAY_BORDER);
		picture.setIcon(new ImageIcon((new ImageIcon("image//Head//" + friend.fpID + ".gif")).getImage()
				.getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
		friendInfoPane.add(picture);
		// 昵称
		nickName = new JLabel();
		nickName.setFont(UseTool.BASIC_FONT);
		nickName.setText(friend.fnickname);
		nickName.setBounds(70, 10, 402, 25);
		friendInfoPane.add(nickName);
		// 签名
		sign = new JLabel();
		sign.setFont(UseTool.BASIC_FONT);
		sign.setBounds(70, 35, 402, 25);
		sign.setText(friend.fsign);
		friendInfoPane.add(sign);

		// 历史记录
		history = new JPanel();
		add(history);
		history.setBounds(3, 70, 650, 240);
		history.setOpaque(false);
		history.setLayout(new BorderLayout());

		historyScroll = new JScrollPane();
		history.add(historyScroll, BorderLayout.CENTER);
		historyTextPane = new JTextPane();
		historyTextPane.setEditable(false);// 不允许编辑

		historyScroll.setViewportView(historyTextPane);		// historyScroll.getVerticalScrollBar().setUI(new MyScrollBarUI());
		historyScroll.setBorder(UseTool.LIGHT_GRAY_BORDER);

		// 工具栏
		tools = new JPanel();
		tools.setLayout(null);
		tools.setOpaque(false);
		tools.setBounds(0, 312, 650, 27);
		add(tools);

		// 输入框
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

		// 取消按钮（关闭）
		quitButton = new JButton();
		add(quitButton);
		quitButton.setBounds(460, 446, 93, 30);
		quitButton.setBorder(UseTool.LIGHT_GRAY_BORDER);
		quitButton.setIcon(new ImageIcon(UseTool.CRT_quitButton));

		// 发送按钮
		sendButton = new JButton();
		add(sendButton);
		sendButton.setBounds(560, 446, 93, 30);
		sendButton.setBorder(UseTool.LIGHT_GRAY_BORDER);
		sendButton.setIcon(new ImageIcon(UseTool.CRT_sendButton));

	}

	private void initListener() {
		// 回车发送消息
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
				// 发送消息
				if (msg == null && msg.equals("")) {
					JOptionPane.showMessageDialog(null, "发送内容不能为空，请重新输入！");
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

	public void insertMyText(String str) { // 插入文本信息
		str = "我" + "  " + str;
		Document doc = historyTextPane.getStyledDocument();
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, Color.BLACK);// 设置文字颜色
		StyleConstants.setFontSize(set, 12);// 设置字体大小
		try {
			doc.insertString(doc.getLength(), str, set);// 插入文字
			historyTextPane.setCaretPosition(historyTextPane.getDocument().getLength());
		} catch (BadLocationException e) {

		}
	}//

	public void insertfdText(String str) { // 插入文本信息
		Document doc = historyTextPane.getStyledDocument();
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, Color.BLACK);// 设置文字颜色
		StyleConstants.setFontSize(set, 12);// 设置字体大小
		try {
			doc.insertString(doc.getLength(), str, set);// 插入文字
			historyTextPane.setCaretPosition(historyTextPane.getDocument().getLength());
		} catch (BadLocationException e) {

		}
		
	}//				



}
