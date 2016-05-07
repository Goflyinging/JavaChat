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
	/** ����� */
	private JPanel content;
	/** �˳���ť */
	private JLabel exitButton;

	private JLabel okButton;
	private JLabel quitButton;

	/** �˺�Txt */
	private JLabel userNameLabel;
	/** �ǳ�Txt */
	private JLabel nickNameLabel;
	/** ����Txt */
	private JLabel passWordLabel;
	/** ȷ������Txt */
	private JLabel repeatPassLabel;
	/** ǩ��Txt */
	private JLabel signatureLabel;
	/** �ǳ� */
	private JTextField nickNameField;
	/** ���� */
	private JPasswordField passWordField;
	/** ȷ������ */
	private JPasswordField repeatPassField;
	/** ǩ�� */
	private JTextArea signatureArea;
	/**�Ա�ѡ�����*/
	private JPanel sexpanel;
	ButtonGroup group;
	JRadioButton nan;
	JRadioButton nv;
	/**�ж�������Ϣ�Ƿ�ȫ����ȷ */
	boolean flag_input = false;
	/** ���͵�ȫ����Ϣ */
	String msg;
	/** */
	int id;
	

	private JScrollPane signatureScroll;
	/** ���ڽ����ƶ�ǰ ��¼��������� */
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

			nickNameLabel = new JLabel("��       ��:");
			content.add(nickNameLabel);
			nickNameLabel.setFont(UseTool.BASIC_FONT);
			nickNameLabel.setBounds(30, 33, 70, 20);

			nickNameField = new JTextField();
			content.add(nickNameField);
			nickNameField.setBounds(110, 30, 240, 28);
			nickNameField.setBorder(UseTool.LIGHT_GRAY_BORDER);

			
			userNameLabel = new JLabel("��       ��:");
			content.add(userNameLabel);
			userNameLabel.setFont(UseTool.BASIC_FONT);
			userNameLabel.setBounds(30, 73, 70, 20);

			group = new ButtonGroup();
			nan = new JRadioButton("��",true);
			group.add(nan);
			nv = new JRadioButton("Ů",false);
			group.add(nv);
			sexpanel = new JPanel();
			sexpanel.setLayout(new BorderLayout());
			sexpanel.add(nan,BorderLayout.WEST);
			sexpanel.add(nv,BorderLayout.CENTER);
			content.add(sexpanel);
			sexpanel.setBounds(110, 70, 240, 28);
			sexpanel.setBorder(null);
			sexpanel.setOpaque(false);

			passWordLabel = new JLabel("��       ��:");
			content.add(passWordLabel); 
			passWordLabel.setFont(UseTool.BASIC_FONT);
			passWordLabel.setBounds(30, 113, 70, 20);

			passWordField = new JPasswordField();
			content.add(passWordField);
			passWordField.setBounds(110, 110, 240, 28);
			passWordField.setBorder(UseTool.LIGHT_GRAY_BORDER);

			repeatPassLabel = new JLabel("ȷ������:");
			content.add(repeatPassLabel);
			repeatPassLabel.setFont(UseTool.BASIC_FONT);
			repeatPassLabel.setBounds(30, 153, 70, 20);

			repeatPassField = new JPasswordField();
			content.add(repeatPassField);
			repeatPassField.setBounds(110, 150, 240, 28);
			repeatPassField.setBorder(UseTool.LIGHT_GRAY_BORDER);

			signatureLabel = new JLabel("����ǩ��:");
			content.add(signatureLabel);
			signatureLabel.setFont(UseTool.BASIC_FONT);
			signatureLabel.setBounds(30, 193, 70, 20);

			signatureScroll = new JScrollPane();
			content.add(signatureScroll);
			signatureArea = new JTextArea();
			signatureArea.setTabSize(2);// TAB������λ
			signatureArea.setLineWrap(true);// �����Զ����й���
			signatureArea.setWrapStyleWord(true);// ������в����ֹ���
			signatureScroll.setBorder(UseTool.LIGHT_GRAY_BORDER);
			signatureScroll.setViewportView(signatureArea);
			signatureScroll.setBounds(110, 190, 240, 60);

			okButton = new JLabel("ȷ��", JLabel.CENTER);
			content.add(okButton);
			okButton.setFont(UseTool.BASIC_FONT);
			okButton.setBorder(null);
			okButton.setBounds(110, 260, 80, 30);
			okButton.setOpaque(false);
			okButton.setBackground(new Color(240, 245, 240, 60));

			quitButton = new JLabel("ȡ��", JLabel.CENTER);
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
		// ȷ����ť�¼�
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
					JOptionPane.showMessageDialog(inst, id+"","ע��ɹ�     ���μ�����û���",1);
					}
					
					
				}
			}
		});
		// ȡ����ť�¼�
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
				dispose();

			}
		});
		// ����򽹵��¼�
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

	// ע��
	private void register() {
		
		String nick = nickNameField.getText();
		String sex  = nan.isSelected()?"��":"Ů"; 
		String pass = String.valueOf(passWordField.getPassword());
		String rept = String.valueOf(repeatPassField.getPassword());
		String sign = signatureArea.getText().trim();
		// // ��֤
		 if (nick.equals("")) {
			 JOptionPane.showMessageDialog(this,"�ǳƲ���Ϊ��");
			 return ;
		 }
		if (nick.length() > 10) {
			JOptionPane.showMessageDialog(this,"�ǳƲ���Ϊ����10λ");
			return ;
		}
		if (pass.equals("")) {
			JOptionPane.showMessageDialog(this,"���벻��Ϊ��");
			return ;
		}
		if (!pass.equals(rept)) {
			JOptionPane.showMessageDialog(this,"�������벻һ��");
			return ;
		}
		if (pass.length() > 10||pass.length()<5) {
			 JOptionPane.showMessageDialog(this,"���볤�ȱ������5λС��10λ");
			 return ;
		}
		flag_input = true;
		msg = "@register" +pass+"@pwd"+sign+"@sign" +nick+"@nickname"+sex;
		return;

	}


}
