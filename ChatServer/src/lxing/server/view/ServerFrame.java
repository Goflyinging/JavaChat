package lxing.server.view;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import lxing.server.thread.ServerThread;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;


public class ServerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	ServerThread serverThread = new ServerThread(this); 
	JButton jbt_start = new JButton("\u542F\u52A8\u670D\u52A1");
	JButton jbt_stop = new JButton("\u505C\u6B62\u670D\u52A1");
	ServerFrame sf = null ;


	/**
	 * Create the frame.
	 */
	public ServerFrame() {
		sf = this;
		setIconImage(Toolkit.getDefaultToolkit().getImage("Images\\icon.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 356, 159);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		jbt_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				jbt_start.setEnabled(false);
				jbt_stop.setEnabled(true);			
				//启动服务			
				sf.serverStart();
			}
		});
		
		
		jbt_start.setFont(new Font("宋体", Font.BOLD, 15));
			
				
				
	
		jbt_start.setBounds(37, 31, 100, 50);
		contentPane.add(jbt_start);
		jbt_stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int flag = JOptionPane.showConfirmDialog(contentPane, "是否要停止服务器？", "", 
	            		JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
	            if(flag == JOptionPane.OK_OPTION){
	            	
	            	jbt_start.setEnabled(true);
	            	jbt_stop.setEnabled(false);
	            	sf.serverStop();
	            	
	            }
			}
		});
		
		
		jbt_stop.setFont(new Font("宋体", Font.BOLD, 14));
		jbt_stop.setBounds(195, 31, 100, 50);
		jbt_stop.setEnabled(false);
		contentPane.add(jbt_stop);
		
		
	}


	
	//启动服务
	public void serverStart(){
	serverThread.setFlag_start(true);
	serverThread.start();
	}
	
	public void serverStop(){
		serverThread.setFlag_start(false);
		serverThread.interrupt();
	}
	
	public static void main(String[] args) {
		ServerFrame sf = new ServerFrame();
		sf.setVisible(true);
	}

	public void setStartAndStopUnable() {
		JOptionPane.showMessageDialog(this, "不能同时开启两个服务器");
		jbt_start.setEnabled(false);
		jbt_stop.setEnabled(false);
	}

}
