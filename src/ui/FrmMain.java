package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cal.Subway;

public class FrmMain extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();

	private JButton btnOk = new JButton("搜索");
	private JButton btnCancel = new JButton("取消");
	private JLabel Start = new JLabel("起点站");
	private JTextField edtStart = new JTextField(10);
	private JTextField edtEnd = new JTextField(10);
	private JLabel End = new JLabel("终点站");
	private JLabel[] p = new JLabel[1000];
	private JPanel PanelWest = new JPanel();
	private JPanel PanelEAST = new JPanel();

	public FrmMain() {
		PanelWest.setLayout(new FlowLayout(FlowLayout.CENTER));
		PanelWest.setBackground(Color.WHITE);
		PanelWest.setPreferredSize(new Dimension(1310, 0));
		ImageIcon image = new ImageIcon("C:\\Users\\ANARKHWQH\\Desktop\\subway_map.jpg");
		JLabel map = new JLabel();
		map.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
		image.setImage(image.getImage().getScaledInstance(1200, 758,Image.SCALE_SMOOTH ));
		map.setIcon(image);
		PanelWest.add(map);
		this.getContentPane().add(PanelWest, BorderLayout.WEST);

		PanelEAST.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		PanelEAST.setPreferredSize(new Dimension(0, 45));
		PanelEAST.setBackground(Color.WHITE);
		PanelEAST.add(Start);
		PanelEAST.add(edtStart);
		PanelEAST.add(End);
		PanelEAST.add(edtEnd);
		PanelEAST.add(btnOk);
		PanelEAST.add(btnCancel);
		Start.setFont(new Font("华文中宋", 0, 15));
		End.setFont(new Font("华文中宋", 0, 15));
		edtStart.setFont(new Font("华文中宋", 0, 15));
		edtEnd.setFont(new Font("华文中宋", 0, 15));
		btnOk.setFocusPainted(false);// 设置点击不出现边框
		btnOk.setBorderPainted(false);// 是否画边框
		btnOk.setBackground(Color.decode("#f96650"));
		btnOk.setForeground(Color.WHITE);
		btnOk.setFont(new Font("黑体", 0, 13));
		btnCancel.setFocusPainted(false);// 设置点击不出现边框
		btnCancel.setBorderPainted(false);// 是否画边框
		btnCancel.setBackground(Color.decode("#f96650"));
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("黑体", 0, 13));
		this.getContentPane().add(PanelEAST, BorderLayout.NORTH);
		JPanel PanelSouth = new JPanel();
		PanelSouth.setBackground(Color.WHITE);
		this.getContentPane().add(PanelSouth);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnCancel) {
			this.setVisible(false);
			return;
		} else if (e.getSource() == this.btnOk) {
			String start = this.edtStart.getText();
			String end = this.edtEnd.getText();
			try {
				String[] path = Subway.Main(start, end).split(",");
				for (int i = 0; i < path.length; i++) {
					System.out.print(path[i] + " ");
				}
				System.out.println("");
				JPanel PanelSouth = new JPanel();
				PanelSouth.setBackground(Color.WHITE);
				PanelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
				JPanel l = new JPanel();
				l.setBackground(Color.WHITE);
				for (int i = 0; i < path.length; i++) {
					p[i] = new JLabel(path[i]);
					p[i].setPreferredSize(new Dimension(150, 30));
					if (path[i].charAt(0) == '换' && path[i].charAt(1) == '乘') {
						p[i].setForeground(Color.RED);
						p[i].setFont(new Font("黑体", 0, 15));
					}
					l.add(p[i]);
				}
				JScrollPane ll = new JScrollPane(l);
				l.setPreferredSize(new Dimension(150, path.length * 37));
				ll.setBorder(null);
				ll.setPreferredSize(new Dimension(170, 700));
				PanelSouth.add(ll);
				this.getContentPane().add(PanelSouth);
				this.validate();
				this.setVisible(true);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

	}

}
