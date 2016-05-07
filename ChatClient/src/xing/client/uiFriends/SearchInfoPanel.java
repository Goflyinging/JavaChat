package xing.client.uiFriends;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import xing.client.data.DataManager;
import xing.client.uicommon.UseTool;
import xing.client.user.Friends;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;
import javax.swing.UIManager;

public class SearchInfoPanel extends JFrame {
	/** 主面板 */
	private JPanel content;
	/** 退出按钮 */
	private JLabel exitButton;

	private JButton okButton;
	private JButton quitButton;
	/** 判断输入信息是否全部正确 */
	boolean flag_input = false;
	/** 发送的全部消息 */
	String msg;
	/** */
	int id;
	/** 用于界面移动前 记录界面的坐标 */
	InfoPanel infoPanel = new InfoPanel();
	/** 信息panel */
	private Point point = new Point();
	static SearchInfoPanel searchPanel = new SearchInfoPanel();
	private JTextField search;
	private JLabel photo;
	private JLabel sign;
	private JTextArea signArea;
	private JTextField sexField;
	private JTextField nicknameField;
	private JTextField IDField;
	private JLabel ID;
	private JLabel nickname;
	private JLabel sex;
	private JLabel ing; // 这在查找
	private String mess = "";
	private JButton button;
	int Mwide = 397;
	int Mheight = 300;

	Friends f; // f传进来
	String mpath = "Image//Head//";

	public static SearchInfoPanel getInstance() {
		searchPanel.setLocationRelativeTo(null);
		searchPanel.setVisible(true);
		return searchPanel;
	}

	private SearchInfoPanel() {
		super();
		initGUI();
		initListener();
	}

	private void initGUI() {
		try {
			setSize(Mwide, Mheight);
			
			setUndecorated(true);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation((int) (screenSize.getWidth() - Mwide) / 2, (int) (screenSize.getHeight() - Mheight) / 2);

			content = new JPanel() {
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					BufferedImage im = null;
					try {
						im = ImageIO.read(new File(UseTool.SEARCH_back));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					g.drawImage(im, 0, 0, null);
					this.setOpaque(false);
				}
			};
			getContentPane().add(content, BorderLayout.CENTER);
			content.setBorder(UseTool.GRAY_BORDER);
			content.setLayout(null);

			exitButton = new JLabel();
			exitButton.setBounds(358, 0, 40, 20);
			content.add(exitButton);
			exitButton.setIcon(new ImageIcon(UseTool.MAIN_close));

			okButton = new JButton("添加");
			okButton.setBounds(110, 260, 80, 30);
			content.add(okButton);
			okButton.setFont(UseTool.BASIC_FONT);
			okButton.setOpaque(true);
			okButton.setBorder(UseTool.GRAY_BORDER);

			quitButton = new JButton("取消");
			quitButton.setBounds(220, 260, 80, 30);
			content.add(quitButton);
			quitButton.setFont(UseTool.BASIC_FONT);
			quitButton.setOpaque(true);
			quitButton.setBorder(UseTool.GRAY_BORDER);

			search = new JTextField("请输入你想查询的ID");
			search.setBorder(UseTool.LIGHT_GRAY_BORDER);
			search.setBounds(30, 33, 230, 26);
			content.add(search);

			button = new JButton("\u67E5\u627E");
			button.setBounds(278, 30, 93, 29);
			button.setFont(UseTool.BASIC_FONT);
			content.add(button);

			ing = new JLabel(mess);
			ing.setFont(new Font("宋体", Font.BOLD, 18));
			ing.setBounds(78, 109, 260, 64);
			content.add(ing);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class InfoPanel extends JPanel {

		public InfoPanel() {
		

		// 模拟数据
		// f = new Friends();
		// f.fsno = 0;
		// f.fnickname = "haha";
		// f.fpID = 2;
		// f.fsex = "女";
		// f.fsign = "aaa";
		// f.fstatus = 0;
		
			setBounds(10, 69, 361, 189);
			setOpaque(false);
			setLayout(null);
			photo = new JLabel();
			photo.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			photo.setBounds(44, 10, 100, 100);
			add(photo);

			sign = new JLabel("\u4E2A\u6027\u7B7E\u540D:");
			sign.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			sign.setBounds(31, 122, 70, 20);
			add(sign);

			signArea = new JTextArea();
			signArea.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			signArea.setWrapStyleWord(true);
			signArea.setTabSize(2);
			signArea.setLineWrap(true);
			signArea.setBounds(112, 120, 238, 58);
			signArea.setEditable(false);
			signArea.setOpaque(false);
			add(signArea);

			sexField = new JTextField();
			sexField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			sexField.setBounds(231, 83, 120, 26);
			sexField.setOpaque(false);
			sexField.setEditable(false);
			sexField.setBorder(null);
			add(sexField);

			nicknameField = new JTextField();
			nicknameField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			nicknameField.setBounds(231, 47, 120, 26);
			nicknameField.setEditable(false);
			nicknameField.setOpaque(false);
			nicknameField.setBorder(null);
			add(nicknameField);
			
			IDField = new JTextField();
			IDField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			IDField.setBounds(231, 11, 120, 26);
			IDField.setEditable(false);
			IDField.setOpaque(false);
			IDField.setBorder(null);
			add(IDField);

			ID = new JLabel(" I D:");
			ID.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			ID.setBounds(175, 11, 46, 26);
			add(ID);

			nickname = new JLabel("\u6635\u79F0:");
			nickname.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			nickname.setBounds(175, 47, 46, 26);
			add(nickname);

			sex = new JLabel("\u6027\u522B:");
			sex.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			sex.setBounds(175, 83, 46, 26);
			add(sex);
			
			}
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
		// 确定按钮事件
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (f != null) {

					DataManager dm = DataManager.getDataManager();
					dm.sentAddFriend(f.fID);
					infoPanel.setVisible(false);
					mess = "发送成功,请等待对方确定";
					ing.setText(mess);
				}

			}
		});
		// 取消按钮事件
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				dispose();
				// client.setRegister(null);
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
				dispose();

			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fID = 0;
				fID = Integer.parseInt(search.getText().trim());
				System.out.println(fID);
				if (fID != 0 && fID >= 80000 && fID <= 1000000) {
					DataManager dm = DataManager.getDataManager();
					dm.searchInfo(fID);
					mess = "正在查找，请稍等。。。。。";
					ing.setText(mess);
				} else {
					ing.setText("80000=<ID<=1000000");
				}
			}
		});
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// search.setText("");
				ing.setText("");
			}
		});
	}

	public void getInfo (Friends f) {
		this.f = f;
		if(f!=null){
			ing.setText("");
			photo.setIcon(new ImageIcon((new ImageIcon(mpath + f.fpID + ".gif")).getImage().getScaledInstance(65, 65,
					Image.SCALE_DEFAULT)));
			IDField.setText(f.fID+"");
			nicknameField.setText(f.fnickname);
			sexField.setText(f.fsex);
			signArea.setText(f.fsign);
			infoPanel.setVisible(true);
			infoPanel.updateUI();
			content.add(infoPanel);
			content.updateUI();
		}
		else{
			mess ="未找到你要查询的ID！！！！";
			ing.setText(mess);
		}
		
	}

	public void setSearchText(String s) {
		search.setText(s);
	}

	public void setingText(String s) {
		ing.setText(s);
	}
}
