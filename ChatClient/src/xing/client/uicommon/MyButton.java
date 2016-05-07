package xing.client.uicommon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class MyButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;  //���л�ʱ���ְ汾�ļ�����
	public MyButton()
	{
		setContentAreaFilled(false);  //���ð�ť����  ͼ�갴ť
		setBorderPainted(false);     //���ð�ť�߿�
		//setBorder(null);
		//this.setBackground(new Color(0,0,255));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			//	System.out.println("pree");
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				//System.out.println("released");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				setBorderPainted(true);
				
				//System.out.println("enter");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setBorderPainted(false);
			//	System.out.println("exit");
				
			}
		});
	}
	

}
