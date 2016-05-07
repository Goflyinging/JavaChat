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

	JPLbg lbg = new JPLbg(); // JPanel ��������
	JTextField L_user;

	public JLoginFrm() {
		// TODO Auto-generated constructor stub
		lbg.setLayout(null);
		int Lwide = 429, Lheight = 330;
		JLoginFrm jLl = this;
		// �ر�
		MyButton L_close = new MyButton();
		L_close.setBounds(Lwide - 30, 0, 30, 30);
		L_close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

		});
		lbg.add(L_close);

		// ��С��
		MyButton L_min = new MyButton();
		L_min.setBounds(Lwide - 60, 0, 30, 30);
		L_min.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED); // ��С��
			}
		});
		lbg.add(L_min);

		// �ʺſ�
		L_user = new JTextField();
		L_user.setBounds(132, 195, 195, 30); // 325 225
		lbg.add(L_user);
		//
		// �����
		JPasswordField L_pswd = new JPasswordField();
		L_pswd.setBounds(132, 225, 195, 30);
		lbg.add(L_pswd);

		// ��¼��ť
		MyButton L_login = new MyButton();
		L_login.setBounds(133, 286, 195, 33);
		L_login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String userID = L_user.getText().trim();
				String pwd = String.valueOf(L_pswd.getPassword());

				DataManager dm = DataManager.getDataManager();

				if (userID.equals("")) { // QQ���Ƿ�Ϊ��
					JOptionPane.showMessageDialog(null, "������QQ��!");
				} else if (pwd.equals("")) {// �����Ƿ�Ϊ��
					JOptionPane.showMessageDialog(null, "����������!");
				} else if (dm.sentLoginInfo(userID, pwd)) { // �˶��û��Ƿ�Ϸ�
					jLl.dispose();
					MainFrm jmf = new MainFrm();
					jmf.setVisible(true);

				} else {
					String str = dm.getloginInfo();
					if (str==null||str.equals("false")) {
						JOptionPane.showMessageDialog(null, "��������û������������!");
					} else if (str.equals("reLogin")) {
						JOptionPane.showMessageDialog(null, "�����ظ���¼!");
					}
				}

			}
		});
		lbg.add(L_login);

		// ע�ᰴť
		MyButton L_reg = new MyButton();// ע��
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
		this.setUndecorated(true);// ����Ҫ��������װ��
		this.setVisible(true);

		lbg.addMouseMotionListener(new MouseAdapter() { // ���ô��ڿ��ƶ�
			private Point draggingAnchor = null;

			@Override
			public void mouseMoved(MouseEvent e) {
				draggingAnchor = new Point(e.getX() + lbg.getX(), e.getY() + lbg.getY());

			}

			// ����Ӧ�ڵ�λ�� = ��굱ǰ��λ�� - ����갴��ʱ��λ�� -����λ�ã�
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(e.getLocationOnScreen().x - draggingAnchor.x, e.getLocationOnScreen().y - draggingAnchor.y);
			}
		});
		//Tab �����л�
		ArrayList<Component> list = new ArrayList<Component>();  //����  
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
