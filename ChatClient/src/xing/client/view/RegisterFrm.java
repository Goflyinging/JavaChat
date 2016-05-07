package xing.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import xing.client.data.DataManager;
import xing.client.uicommon.UseTool;

public class RegisterFrm extends JDialog {
	/** 主面板 */
	private JPanel content;
	/** 退出按钮 */
	private JLabel exitButton;

	private JLabel okButton;
	private JLabel quitButton;

	/** 账号Txt */
	private JLabel userNameLabel;
	/** 昵称Txt */
	private JLabel nickNameLabel;
	/** 密码Txt */
	private JLabel passWordLabel;
	/** 确认密码Txt */
	private JLabel repeatPassLabel;
	/** 签名Txt */
	private JLabel signatureLabel;
	/** 昵称 */
	private JTextField nickNameField;
	/** 密码 */
	private JPasswordField passWordField;
	/** 确认密码 */
	private JPasswordField repeatPassField;
	/** 签名 */
	private JTextArea signatureArea;
	/**性别选择面板*/
	private JPanel sexpanel;
	ButtonGroup group;
	JRadioButton nan;
	JRadioButton nv;
	/**判断输入信息是否全部正确 */
	boolean flag_input = false;
	/** 发送的全部消息 */
	String msg;
	/** */
	int id;
	

	private JScrollPane signatureScroll;
	/** 用于界面移动前 记录界面的坐标 */
	private Point point = new Point();
	static RegisterFrm inst = new RegisterFrm();

	public static RegisterFrm getInstance() {
		inst.setLocationRelativeTo(null);
		inst.setVisible(true);
		return inst;
	}

	private RegisterFrm() {
		super();
		initGUI();
		initListener();
	}

	private void initGUI() {
		try {
			setSize(397, 300);
			setUndecorated(true);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

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
			getContentPane().add(content, BorderLayout.CENTER);
			content.setBorder(UseTool.GRAY_BORDER);

			exitButton = new JLabel();
			content.add(exitButton);
			exitButton.setBounds(358, 0, 40, 20);
			exitButton.setIcon(new ImageIcon(UseTool.MAIN_close));

			nickNameLabel = new JLabel("昵       称:");
			content.add(nickNameLabel);
			nickNameLabel.setFont(UseTool.BASIC_FONT);
			nickNameLabel.setBounds(30, 33, 70, 20);

			nickNameField = new JTextField();
			content.add(nickNameField);
			nickNameField.setBounds(110, 30, 240, 28);
			nickNameField.setBorder(UseTool.LIGHT_GRAY_BORDER);

			
			userNameLabel = new JLabel("性       别:");
			content.add(userNameLabel);
			userNameLabel.setFont(UseTool.BASIC_FONT);
			userNameLabel.setBounds(30, 73, 70, 20);

			group = new ButtonGroup();
			nan = new JRadioButton("男",true);
			group.add(nan);
			nv = new JRadioButton("女",false);
			group.add(nv);
			sexpanel = new JPanel();
			sexpanel.setLayout(new BorderLayout());
			sexpanel.add(nan,BorderLayout.WEST);
			sexpanel.add(nv,BorderLayout.CENTER);
			content.add(sexpanel);
			sexpanel.setBounds(110, 70, 240, 28);
			sexpanel.setBorder(null);
			sexpanel.setOpaque(false);

			passWordLabel = new JLabel("密       码:");
			content.add(passWordLabel); 
			passWordLabel.setFont(UseTool.BASIC_FONT);
			passWordLabel.setBounds(30, 113, 70, 20);

			passWordField = new JPasswordField();
			content.add(passWordField);
			passWordField.setBounds(110, 110, 240, 28);
			passWordField.setBorder(UseTool.LIGHT_GRAY_BORDER);

			repeatPassLabel = new JLabel("确认密码:");
			content.add(repeatPassLabel);
			repeatPassLabel.setFont(UseTool.BASIC_FONT);
			repeatPassLabel.setBounds(30, 153, 70, 20);

			repeatPassField = new JPasswordField();
			content.add(repeatPassField);
			repeatPassField.setBounds(110, 150, 240, 28);
			repeatPassField.setBorder(UseTool.LIGHT_GRAY_BORDER);

			signatureLabel = new JLabel("个性签名:");
			content.add(signatureLabel);
			signatureLabel.setFont(UseTool.BASIC_FONT);
			signatureLabel.setBounds(30, 193, 70, 20);

			signatureScroll = new JScrollPane();
			content.add(signatureScroll);
			signatureArea = new JTextArea();
			signatureArea.setTabSize(2);// TAB键多少位
			signatureArea.setLineWrap(true);// 激活自动换行功能
			signatureArea.setWrapStyleWord(true);// 激活断行不断字功能
			signatureScroll.setBorder(UseTool.LIGHT_GRAY_BORDER);
			signatureScroll.setViewportView(signatureArea);
			signatureScroll.setBounds(110, 190, 240, 60);

			okButton = new JLabel("确定", JLabel.CENTER);
			content.add(okButton);
			okButton.setFont(UseTool.BASIC_FONT);
			okButton.setBorder(null);
			okButton.setBounds(110, 260, 80, 30);
			okButton.setOpaque(false);
			okButton.setBackground(new Color(240, 245, 240, 60));

			quitButton = new JLabel("取消", JLabel.CENTER);
			content.add(quitButton);
			quitButton.setFont(UseTool.BASIC_FONT);
			quitButton.setBorder(null);
			quitButton.setBounds(220, 260, 80, 30);
			quitButton.setOpaque(false);
			quitButton.setBackground(new Color(240, 245, 240, 60));
		} catch (Exception e) {
			e.printStackTrace();
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
				okButton.setBorder(null);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				okButton.setBorder(UseTool.GRAY_BORDER);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				register();
				if(flag_input){
					okButton.setEnabled(false);
					quitButton.setEnabled(false);
       				DataManager dm = DataManager.getDataManager();
					id = dm.sentRegisterInfo(msg);
					if(id!=0){
					JOptionPane.showMessageDialog(inst, id+"","注册成功     请牢记你的用户名",1);
					}
					
					
				}
			}
		});
		// 取消按钮事件
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setBorder(null);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setBorder(UseTool.GRAY_BORDER);
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
		// 输入框焦点事件
		nickNameField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				nickNameField.setBorder(UseTool.LIGHT_GRAY_BORDER);
			}

			@Override
			public void focusGained(FocusEvent e) {
				nickNameField.setBorder(UseTool.ORANGE_BORDER);
			}
		});
		passWordField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				passWordField.setBorder(UseTool.LIGHT_GRAY_BORDER);
			}

			@Override
			public void focusGained(FocusEvent e) {
				passWordField.setBorder(UseTool.ORANGE_BORDER);
			}
		});
		repeatPassField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				repeatPassField.setBorder(UseTool.LIGHT_GRAY_BORDER);
			}

			@Override
			public void focusGained(FocusEvent e) {
				repeatPassField.setBorder(UseTool.ORANGE_BORDER);
			}
		});
		signatureArea.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				signatureScroll.setBorder(UseTool.LIGHT_GRAY_BORDER);
			}

			@Override
			public void focusGained(FocusEvent e) {
				signatureScroll.setBorder(UseTool.ORANGE_BORDER);
			}
		});
	}

	// 注册
	private void register() {
		
		String nick = nickNameField.getText();
		String sex  = nan.isSelected()?"男":"女"; 
		String pass = String.valueOf(passWordField.getPassword());
		String rept = String.valueOf(repeatPassField.getPassword());
		String sign = signatureArea.getText().trim();
		// // 验证
		 if (nick.equals("")) {
			 JOptionPane.showMessageDialog(this,"昵称不能为空");
			 return ;
		 }
		if (nick.length() > 10) {
			JOptionPane.showMessageDialog(this,"昵称不能为超过10位");
			return ;
		}
		if (pass.equals("")) {
			JOptionPane.showMessageDialog(this,"密码不能为空");
			return ;
		}
		if (!pass.equals(rept)) {
			JOptionPane.showMessageDialog(this,"密码输入不一致");
			return ;
		}
		if (pass.length() > 10||pass.length()<5) {
			 JOptionPane.showMessageDialog(this,"密码长度必须大于5位小于10位");
			 return ;
		}
		flag_input = true;
		msg = "@register" +pass+"@pwd"+sign+"@sign" +nick+"@nickname"+sex;
		return;

	}


}
