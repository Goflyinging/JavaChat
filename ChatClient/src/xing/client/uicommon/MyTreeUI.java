package xing.client.uicommon;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTreeUI;

public class MyTreeUI extends BasicTreeUI {
	
	 // ȥ��JTree�Ĵ�ֱ��  
    @Override  
    protected void paintVerticalLine(Graphics g, JComponent c, int x, int top,  
            int bottom) {  
    }  
  
    // ȥ��JTree��ˮƽ��  
    @Override  
    protected void paintHorizontalLine(Graphics g, JComponent c, int y,  
            int left, int right) {  
    }  
  
    // ʵ�ָ��ڵ����ӽڵ������  
    @Override  
    public void setLeftChildIndent(int newAmount) {  
  
    }  
  
    // ʵ�ָ��ڵ����ӽڵ��Ҷ���  
    @Override  
    public void setRightChildIndent(int newAmount) {  
  
    }  

}
