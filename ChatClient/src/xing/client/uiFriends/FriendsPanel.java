package xing.client.uiFriends;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import xing.client.data.DataManager;
import xing.client.uiChatRoom.ChatRoomTable;
import xing.client.uicommon.MyTreeUI;
import xing.client.uicommon.UseTool;
import xing.client.user.Friends;
import xing.client.user.Subgroup;

public class FriendsPanel extends JPanel {
	private DefaultTreeModel model; // tree model
	DefaultMutableTreeNode lnode; // �ϴ�����ƶ��¼���õ�node
	FriendsCellRenderer myfRenderer; // tree
	DataManager dm = DataManager.getDataManager();
	private JTextField textField; // ����༭��
	subgroupNode sbgnode; // ѡ�з����
	FriendsNode selfdnode;
	ChatRoomTable croomt;
	subgroupNode[] sbgArrnode = new subgroupNode[10]; //����
	private static FriendsPanel fPanel = new FriendsPanel();
	public static FriendsPanel getfPanel(){
		return fPanel;
	}

	private FriendsPanel() {
		croomt = ChatRoomTable.getRoomTable();
		this.setLayout(null);
		myfRenderer = new FriendsCellRenderer();
		// ---------------------ģ������
		// ArrayList<Friends> fslist = new ArrayList<Friends>(); // ���ѱ�
		// ArrayList<Subgroup> sglist = new ArrayList<Subgroup>(); // �����
		// Subgroup sbp = new Subgroup();
		// sbp.sname = "����";
		// sbp.sno = 0;
		// sglist.add(sbp);
		// Subgroup sbp2 = new Subgroup();
		// sbp2.sname = "�ܺ�";
		// sbp2.sno = 1;
		// sglist.add(sbp2);
		// int n = sglist.size();
		// Friends f1 = new Friends();
		// f1.fsno = 0;
		// f1.fnickname = "haha";
		// f1.fpID = 2;
		// f1.fsex = "Ů";
		// f1.fsign = "aaa";
		// f1.fstatus = 0;
		// f1.fID = 1111;
		// fslist.add(f1);
		// Friends f2 = new Friends();
		// f2.fsno = 0;
		// f2.fnickname = "hah";
		// f2.fpID = 4;
		// f2.fsex = "Ů";
		// f2.fsign = "aaa";
		// f2.fstatus = 0;
		// f2.fID = 1111;
		// fslist.add(f2);
		// Friends f3 = new Friends();
		// f3.fsno = 0;
		// f3.fnickname = "hah";
		// f3.fpID = 4;
		// f3.fsex = "Ů";
		// f3.fsign = "aaa";
		// f3.fstatus = 0;
		// f3.fID = 1111;
		// fslist.add(f3);
		// ------------------------------------- ģ������
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("top");
		model = new DefaultTreeModel(top);
		for (Subgroup s : DataManager.sglist) {
			sbgArrnode[s.sno] = new subgroupNode(s);
			top.add(sbgArrnode[s.sno]);
		}
		for (Friends f : DataManager.fslist) {
			System.out.println("FriendsPanel fslist" + f.fID);
			sbgArrnode[f.fsno].add(new FriendsNode(f));
		}

		JTree tree = new JTree(model);
		tree.setCellRenderer(myfRenderer);
		tree.setRootVisible(false); // ���ظ��ڵ�
		tree.setUI(new MyTreeUI()); // ��UI ȥ�߿����
		tree.setInvokesStopCellEditing(true);// �޸Ľڵ�����֮����Ч
		tree.setToggleClickCount(1);// �������
		tree.setAutoscrolls(true);

		// ����һ�����������
		JScrollPane scrollPane = new JScrollPane(tree);
		// ����ʾˮƽ������
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 300, 450);
		scrollPane.setBorder(null);
		// ���� �༭���
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(297, 23));
		textField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		tree.setCellEditor(new DefaultCellEditor(textField));
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (tree.isEditable()) {
						boolean s = (!textField.getText().equals(""));
						if (s && sbgnode != null) {
							boolean isAdd = sbgnode.getSbg().sname.equals(UseTool.NONAME_SUBG);
							sbgnode.setName(textField.getText().trim());
							model.reload(sbgnode);
							Subgroup sbg = sbgnode.getSbg();
							if (isAdd) {  
								dm.sglist.add(sbg);
								dm.sentAddSubgroup(sbgnode.getSbg());
								// //������ӷ�����Ϣ
							} else {
								dm.sglist.set(sbg.sno,sbg);
							    dm.sentRenameSubgroup(sbgnode.getSbg());
								// //���͸�����Ϣ
							}

						}
					
						
					}
					tree.stopEditing();
					tree.setEditable(false);
				}
			}
		});

		// ��������ɫ
		tree.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) { // �����������ȥ �ı䱳��ɫ
				TreePath path = tree.getPathForLocation(e.getX(), e.getY());
				if (null != path && (lnode == null || !lnode.getPath().equals(path))) {
					DefaultMutableTreeNode snode = (DefaultMutableTreeNode) path.getLastPathComponent();
					if (snode instanceof FriendsNode) { // ������ѱ���
						if (lnode != null) {
							if (lnode instanceof FriendsNode) {
								((FriendsNode) lnode).setBackground(Color.WHITE);
								model.reload(((FriendsNode) lnode));
							} else {
								// ���� ���� ɫ ���� ������
							}
						}
						lnode = snode;
						((FriendsNode) lnode).setBackground(new Color(109, 196, 225));
						model.reload(((FriendsNode) lnode));
					} else if (snode instanceof subgroupNode) { // ���� ���� ɫ �ı�
						if (lnode != null) {
							if (lnode instanceof FriendsNode) {
								((FriendsNode) lnode).setBackground(Color.WHITE);
								model.reload(((FriendsNode) lnode));
							} else {
								// ���� ���� ɫ ���� ������

							}
						}
					}
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					TreePath path = tree.getPathForLocation(e.getX(), e.getY());
					if (path == null) {
						return;
					}
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
					if (e.getClickCount() == 1) {

					}
					if (e.getClickCount() == 2) {  //���˫������
						if (node instanceof FriendsNode) {		
							Friends f = ((FriendsNode)node).getFriends();						
							ChatRoomTable.getRoomTable().addFriendPanle(f, null);
						}
					}
				}
			} // clicked

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					TreePath path = tree.getPathForLocation(e.getX(), e.getY());
					if (null == path) {
						return;
					}
					// path�е�node�ڵ㣨path��Ϊ�գ������������գ�
					Object object = path.getLastPathComponent();
					if (object instanceof subgroupNode) {
						// �������
						sbgnode = (subgroupNode) object;
						JPopupMenu subpm = new JPopupMenu();
						subpm.setBackground(Color.WHITE);
						subpm.setBorder(UseTool.LIGHT_GRAY_BORDER);
						JMenuItem mit0 = new JMenuItem("��ӷ���");
						mit0.setOpaque(false);
						mit0.setFont(UseTool.BASIC_FONT);
						JMenuItem mit1 = new JMenuItem("ɾ������");
						mit1.setOpaque(false);
						mit1.setFont(UseTool.BASIC_FONT);
						JMenuItem mit2 = new JMenuItem("��������");
						mit2.setOpaque(false);
						mit2.setFont(UseTool.BASIC_FONT);
						// ��ӷ���
						mit0.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// ��ӷ���
								Subgroup addsbg = new Subgroup(); // ����µ�Ĭ�Ϸ���
								addsbg.sname = UseTool.NONAME_SUBG;
								addsbg.sno = dm.sglist.size();
								subgroupNode addsbgnode = new subgroupNode(addsbg);
								model.insertNodeInto(addsbgnode, top, addsbg.sno ); // ����µķ��� ������˳��
								sbgnode = addsbgnode;
								tree.setEditable(true);
								tree.startEditingAtPath(new TreePath(addsbgnode.getPath()));
							}
						});
						// ɾ������
						mit1.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								if (!sbgnode.getName().equals(UseTool.DEFAULT_SUBG)) {
									dm.sentDeleteSubgroup(sbgnode.getSbg());
									// //����ɾ��������Ϣ
									model.removeNodeFromParent(sbgnode);
									Subgroup sbg = sbgnode.getSbg();
									dm.sglist.remove(sbg);
									for (Subgroup sp : dm.sglist) {
										System.out.println(sp.sname + " " + sp.sname);
										if (sp.sno > sbg.sno) {
											sp.sno++;
										}
									}
								}
							}
						});
						// ������
						mit2.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								tree.setEditable(true);
								tree.startEditingAtPath(new TreePath(sbgnode.getPath()));
								
							}
						});
						subpm.add(mit0);
						subpm.add(mit1);
						subpm.add(mit2);
						subpm.show(tree, e.getX(), e.getY());
					}//���鴦��
					//���Ѵ���
					if (object instanceof FriendsNode) {
						selfdnode = (FriendsNode) object;
						JPopupMenu fdpm = new JPopupMenu();
						fdpm.setBackground(Color.WHITE);
						fdpm.setBorder(UseTool.LIGHT_GRAY_BORDER);
						JMenuItem fdmit0 = new JMenuItem("������Ϣ");
						fdmit0.setOpaque(false);
						fdmit0.addActionListener(new ActionListener() {					
							@Override
							public void actionPerformed(ActionEvent e) {
								Friends f = selfdnode.getFriends();
								ChatRoomTable.getRoomTable().addFriendPanle(f,null);							
							}
						});
						fdmit0.setFont(UseTool.BASIC_FONT);
						JMenuItem fdmit1 = new JMenuItem("�鿴����");
						fdmit1.setOpaque(false);
						fdmit1.setFont(UseTool.BASIC_FONT);
						// �鿴����
						fdmit1.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {

							}
						});
						JMenu fdmit2 = new JMenu("�ƶ�������");
						fdmit2.setOpaque(false);
						fdmit2.setFont(UseTool.BASIC_FONT);
						// �ƶ�����ѡ���
						String selsname = ((subgroupNode) selfdnode.getParent()).getName();
						for (Subgroup sp : dm.sglist) {
							if (!sp.sname.equals(selsname)) { // ��������ǰ��
								JMenuItem fdmit21 = new JMenuItem(sp.sname);
								fdmit21.setOpaque(false);
								fdmit21.setFont(UseTool.BASIC_FONT);
								fdmit2.add(fdmit21);
								fdmit21.addActionListener(new ActionListener() {
									subgroupNode sbg =(subgroupNode) top.getChildAt(sp.sno);
									@Override
									public void actionPerformed(ActionEvent e) {
										 model.removeNodeFromParent(selfdnode);
										 model.insertNodeInto(selfdnode,sbg,sbg.getChildCount());
										 selfdnode.setFdsfsno(sp.sno);
										 //�����޸ĺ�����Ϣ
										 dm.sentChageFriendsfsno(selfdnode.getFriends());
										 
										 
									}
								});
							}
						} // ������������
						//ɾ������
						JMenuItem fdmit3 = new JMenuItem("ɾ������");
						fdmit3.setOpaque(false);
						fdmit3.setFont(UseTool.BASIC_FONT);
						fdmit3.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								 model.removeNodeFromParent(selfdnode);
								 //����ɾ��������Ϣ
								 dm.sentDeleteFriendsfsno(selfdnode.getFriends().fID);
								 
							}
						});

						fdpm.add(fdmit0);
						fdpm.add(fdmit1);
						fdpm.add(fdmit2);
						fdpm.add(fdmit3);
						fdpm.show(tree, e.getX(), e.getY());
					}

				}

			}// mousePressed

		});

		this.add(scrollPane);
		this.setBorder(null);
		this.setBounds(0, 200, 300, 450);

	}
	public void addFriendNode(Friends f){
		if(f==null){
			JOptionPane.showMessageDialog(null, "��Ӻ���" + f.fID + "ʧ��");
		}
		FriendsNode fNode = new FriendsNode(f);
		model.insertNodeInto(fNode,sbgArrnode[0],sbgArrnode[0].getChildCount());	
	}
	public void addlogonFriendNode(Friends f){
		if(f==null){
			JOptionPane.showMessageDialog(null, "��Ӻ���" + f.fID + "ʧ��");
		}
		FriendsNode fNode = new FriendsNode(f);
		model.insertNodeInto(fNode,sbgArrnode[f.fsno],sbgArrnode[f.fsno].getChildCount());	
		//JOptionPane.showMessageDialog(null, "����" + f.fID +f.fnickname + "����");
	}
	public void removeFnode (int fID){
		Friends f =dm.searchfriend(fID);
		if(f==null){
			return;
		}
		int n = sbgArrnode[f.fsno].getChildCount() ;
		for(int i=0;i<n;i++){
		FriendsNode fnode = (FriendsNode)sbgArrnode[f.fsno].getChildAt(i);
		if(fnode.getFriends().fID ==fID){
		model.removeNodeFromParent(fnode);
		break;
		}
		}
		dm.fslist.remove(f);
		//JOptionPane.showMessageDialog(null, "����" + f.fID +f.fnickname + "����");
	}
	

}
