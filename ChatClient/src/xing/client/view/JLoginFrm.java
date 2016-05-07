package xing.client.view;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Graphics;
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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import xing.client.data.DataManager;
import xing.client.uicommon.MyButton;

public class JLoginFrm extends JFrame {

	JPLbg lbg = new JPLbg(); // JPanel 背景设置
	JTextField L_user;

	public JLoginFrm() {
		// TODO Auto-generated constructor stub
		lbg.setLayout(null);
		int Lwide = 429, Lheight = 330;
		JLoginFrm jLl = this;
		// 关闭
		MyButton L_close = new MyButton();
		L_close.setBounds(Lwide - 30, 0, 30, 30);
		L_close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

		});
		lbg.add(L_close);

		// 最小化
		MyButton L_min = new MyButton();
		L_min.setBounds(Lwide - 60, 0, 30, 30);
		L_min.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED); // 最小化
			}
		});
		lbg.add(L_min);

		// 帐号框
		L_user = new JTextField();
		L_user.setBounds(132, 195, 195, 30); // 325 225
		lbg.add(L_user);
		//
		// 密码框
		JPasswordField L_pswd = new JPasswordField();
		L_pswd.setBounds(132, 225, 195, 30);
		lbg.add(L_pswd);

		// 登录按钮
		MyButton L_login = new MyButton();
		L_login.setBounds(133, 286, 195, 33);
		L_login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String userID = L_user.getText().trim();
				String pwd = String.valueOf(L_pswd.getPassword());

				DataManager dm = DataManager.getDataManager();

				if (userID.equals("")) { // QQ号是否为空
					JOptionPane.showMessageDialog(null, "请输入QQ号!");
				} else if (pwd.equals("")) {// 密码是否为空
					JOptionPane.showMessageDialog(null, "请输入密码!");
				} else if (dm.sentLoginInfo(userID, pwd)) { // 核对用户是否合法
					jLl.dispose();
					MainFrm jmf = new MainFrm();
					jmf.setVisible(true);

				} else {
					String str = dm.getloginInfo();
					if (str==null||str.equals("false")) {
						JOptionPane.showMessageDialog(null, "您输入的用户名或密码错误!");
					} else if (str.equals("reLogin")) {
						JOptionPane.showMessageDialog(null, "不能重复登录!");
					}
				}

			}
		});
		lbg.add(L_login);

		// 注册按钮
		MyButton L_reg = new MyButton();// 注册
		L_reg.setBounds(328, 195, 63, 26);
		lbg.add(L_reg);
		L_reg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RegisterFrm rf = RegisterFrm.getInstance();
			}
		});

		lbg.setBounds(this.getBounds());
		lbg.setLocation(0, 0);
		lbg.setOpaque(false);
		this.add(lbg);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (screenSize.getWidth() - Lwide) / 2, (int) (screenSize.getHeight() - Lheight) / 2);
		this.setSize(Lwide, Lheight);
		this.setResizable(false);
		this.setUndecorated(true);// 不需要标题栏等装饰
		this.setVisible(true);

		lbg.addMouseMotionListener(new MouseAdapter() { // 设置窗口可移动
			private Point draggingAnchor = null;

			@Override
			public void mouseMoved(MouseEvent e) {
				draggingAnchor = new Point(e.getX() + lbg.getX(), e.getY() + lbg.getY());

			}

			// 窗口应在的位置 = 鼠标当前的位置 - （鼠标按下时的位置 -窗口位置）
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(e.getLocationOnScreen().x - draggingAnchor.x, e.getLocationOnScreen().y - draggingAnchor.y);
			}
		});
		//Tab 焦点切换
		ArrayList<Component> list = new ArrayList<Component>();  //焦点  
	     list.add(L_user);  
	     list.add(L_pswd);   
	    final ArrayList comList = list;  
	    FocusTraversalPolicy policy = new FocusTraversalPolicy() {  
	      public Component getFirstComponent(Container focusCycleRoot) {  
	        return  (Component)comList.get(0);  
	      }  
	      public Component getLastComponent(Container focusCycleRoot) {  
	        return (Component) comList.get(comList.size()-1);  
	      }  
	      public Component getComponentAfter(Container focusCycleRoot,   
	          Component aComponent) {  
	        int index = comList.indexOf(aComponent);  
	          
	        return (Component) comList.get((index + 1) % comList.size());  
	      }  
	      public Component getComponentBefore(Container focusCycleRoot,   
	          Component aComponent) {  
	        int index = comList.indexOf(aComponent);  
	        return (Component) comList.get((index - 1 + comList.size()) % comList.size());  
	      }  
	      public Component getDefaultComponent(Container focusCycleRoot) {  
	        return (Component) comList.get(0);  
	      }  
	    };  
	    this.setFocusTraversalPolicy(policy);  
	    L_pswd.addKeyListener(new KeyAdapter() {
	    	@Override
	    	public void keyPressed(KeyEvent e) {
	    		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
	    			L_login.doClick();			
				}
	    	}
		});

	}

	public class JPLbg extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			String Bk = "image//Chat//login.png";
			super.paintComponent(g);
			BufferedImage im = null;
			try {
				im = ImageIO.read(new File(Bk));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(im, 0, 0, im.getWidth(), im.getHeight(), this);
		}

	}

	public static void main(String[] args) {
		new JLoginFrm();
	}

}
