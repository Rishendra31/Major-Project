package com;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
public class Main extends JFrame{
	JPanel p1;
	JPanel p2;
	JLabel l1,l2,l3,l4;
	JTextField tf1;
	JComboBox c1;
	JButton b1;
	Font f1;
	Network network;
	JScrollPane jsp;
	static JTextArea area;
public Main(){
	super("Cyber Security Simulation");
	
	p1 = new JPanel();
	p1.setLayout(null);
	
	f1 = new Font("Courier New",Font.BOLD,12);
	p2 = new JPanel();
	p2.setBackground(Color.black);
	l1 = new JLabel("<HTML><BODY><CENTER>Cyber Crime & Security Simulation</CENTER></BODY></HTML>".toUpperCase());
	l1.setForeground(Color.white);
	l1.setFont(new Font("Courier New",Font.PLAIN,17));
	p2.add(l1);

	l2 = new JLabel("Network Users Simulation Configuration Screen");
	l2.setFont(new Font("Courier New",Font.BOLD,13));
	l2.setBounds(180,20,400,30);
	p1.add(l2);

	l3 = new JLabel("Users Size");
	l3.setBounds(190,70,170,30);
	l3.setFont(f1);
	p1.add(l3);

	tf1 = new JTextField();
	tf1.setFont(f1);
	p1.add(tf1);
	tf1.setBounds(370,70,100,30);

	l4 = new JLabel("Port No");
	l4.setBounds(190,120,140,30);
	l4.setFont(f1);
	p1.add(l4);

	c1 = new JComboBox();
	c1.setFont(f1);
	p1.add(c1);
	c1.setBounds(370,120,100,30);
	c1.addItem("3333");
	
	b1 = new JButton("Show Network");
	b1.setFont(f1);
	b1.setBounds(230,170,130,30);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int size = Integer.parseInt(tf1.getText().trim());
			int port = Integer.parseInt(c1.getSelectedItem().toString().trim());
			network = new Network(size,port);
			network.setVisible(true);
			network.setExtendedState(JFrame.MAXIMIZED_BOTH);
			network.getFiles();
		}
	});

	area = new JTextArea();
	area.setFont(f1);
	area.setEditable(false);
	area.setLineWrap(true);
	jsp = new JScrollPane(area);
	jsp.setBounds(10,220,600,300);
	p1.add(jsp);

	getContentPane().add(p1,BorderLayout.CENTER);
	getContentPane().add(p2,BorderLayout.NORTH);
}
public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	Main main = new Main();
	main.setVisible(true);
	main.setSize(800,660);
	main.setLocationRelativeTo(null);
	main.setResizable(false);
}
}