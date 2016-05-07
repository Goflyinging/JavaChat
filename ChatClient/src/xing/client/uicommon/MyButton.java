package xing.client.uicommon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class MyButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;  //序列化时保持版本的兼容性
	public MyButton()
	{
		setContentAreaFilled(false);  //设置按钮属性  图标按钮
		setBorderPainted(false);     //设置按钮边框
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
