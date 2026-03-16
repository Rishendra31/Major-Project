package com;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import java.util.HashMap;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class Network extends JFrame{
	Simulation node;
	JPanel p1,p2;
	JButton b1,b2,b3;
	static JLabel l3;
	JLabel l1,l2;
	JComboBox c1,c2;
	Font f1;
	int size;
	int port;
	JFileChooser chooser;
public Network(int sz,int p){
	super("Cyber Security");
	size = sz;
	port = p;
	f1 = new Font("Courier New",Font.BOLD,14);
	node = new Simulation();
	p1 = new JPanel();
	p1.setLayout(new BorderLayout());
	p1.add(node,BorderLayout.CENTER);
	p1.setBackground(new Color(119,69,0));
	getContentPane().add(p1,BorderLayout.CENTER);
	p2 = new JPanel();
	p2.setLayout(new MigLayout("wrap 1")); 

	chooser = new JFileChooser();
	
	l1 = new JLabel("Users ID");
	l1.setFont(f1);
	p2.add(l1,"span,split 5");
	c1 = new JComboBox();
	c1.setFont(f1);
	for(int i=1;i<=size;i++){
		c1.addItem("U"+Integer.toString(i));
	}
	p2.add(c1);

	l2 = new JLabel("Filename");
	l2.setFont(f1);
	p2.add(l2);
	c2 = new JComboBox();
	c2.setFont(f1);
	p2.add(c2);

	l3 = new JLabel();
	l3.setFont(f1);
	p2.add(l3);

	b1 = new JButton("Upload File to Server");
	p2.add(b1,"span,split 4");
	b1.setFont(f1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Runnable r = new Runnable(){
				public void run(){
					upload();
				}
			};
			new Thread(r).start();
		}
	});

	b2 = new JButton("Download File from Server");
	p2.add(b2);
	b2.setFont(f1);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Runnable r = new Runnable(){
				public void run(){
					download();
				}
			};
			new Thread(r).start();
		}
	});

	b3 = new JButton("Exit Simulation");
	p2.add(b3);
	b3.setFont(f1);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			System.exit(0);
		}
	});
	
	getContentPane().add(p2,BorderLayout.SOUTH);
	Node.randomNodes(size,800,600,node,400);
	node.option = 0;
	node.repaint();
}
public void getFiles(){
	try{
		c2.removeAllItems();
		Socket soc = new Socket("localhost",3333);
		ObjectOutputStream oos = new ObjectOutputStream(soc.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(soc.getInputStream());
		Object res[] = {"filelist"};
		oos.writeObject(res);
		oos.flush();
		Object obj[] = (Object[])ois.readObject();
		String files[] = (String[])obj[0];
		if(!files[0].equals("nofiles")) {
			for(int i=0;i<files.length;i++){
				c2.addItem(files[i]);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void upload(){
	try{
		String user = c1.getSelectedItem().toString();
		int option = chooser.showOpenDialog(this);
		if(option == chooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			FileInputStream fin = new FileInputStream(file);
			byte b[] = new byte[fin.available()];
			fin.read(b,0,b.length);
			fin.close();
			Upload up = new Upload(user,file.getName(),b,node,c2);
			up.start();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void download() {
	try{
		String user = c1.getSelectedItem().toString();
		String file = c2.getSelectedItem().toString();
		Download dl = new Download(user,file,node);
		dl.start();
	}catch(Exception e){
		e.printStackTrace();
	}
}
}