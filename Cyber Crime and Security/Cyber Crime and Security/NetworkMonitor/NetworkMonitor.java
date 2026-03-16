package com;
import java.net.Socket;
import java.net.ServerSocket;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.net.InetAddress;
import java.awt.Dimension;
import com.jd.swing.custom.component.panel.SimpleGlossyPanel;
import com.jd.swing.util.PanelType;
import com.jd.swing.util.Theme;
import org.jfree.ui.RefineryUtilities;
public class NetworkMonitor extends JFrame{	
	PacketReceiverThread thread;
	JPanel p1,p2,p3,p4;
	JLabel l1,l2;
	
	JScrollPane jsp;
	JTextArea area;
	Font f1,f2;
	ServerSocket server;
	Socket socket;
	static String status;
	JButton b1;
	static int normal,attack;
public void start(){
	try{
		server = new ServerSocket(4444);
		area.append("Network Monitor MapReduce Server Started\n\n");
		while(true){
			socket = server.accept();
			socket.setKeepAlive(true);
			InetAddress address=socket.getInetAddress();
			String ipadd=address.toString();
			area.append("Connected Computers :"+ipadd.substring(1,ipadd.length())+"\n");
			thread=new PacketReceiverThread(socket,area);
			thread.start();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

public NetworkMonitor(){
	setTitle("Network Monitor MapReduce Server Started");
	f1 = new Font("Castellar", 1, 16);
    p1 = new SimpleGlossyPanel(Theme.GLOSSY_MULTIBLUECOLOR_THEME,PanelType.PANEL_ROUNDED_RECTANGLUR);
	l1 = new JLabel("<html><body><center>Network Packet Monitoring MapReduce Server Started".toUpperCase());
	l1.setFont(this.f1);
    l1.setForeground(Color.white);
    p1.add(l1);
	
    f2 = new Font("Courier New", 1, 13);
    p2 = new JPanel();
    p2.setLayout(new BorderLayout());
    area = new JTextArea();
    area.setFont(f2);
    jsp = new JScrollPane(area);
	jsp.setPreferredSize(new Dimension(400,500));
    area.setEditable(false);
    p2.add(jsp);

	p3 = new JPanel();
	b1 = new JButton("Normal & attack Packets Comparison Graph");
	p3.add(b1);
	b1.setFont(f1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Chart chart1 = new Chart("Normal & attack Packets Comparison Graph");
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
		}
	});
	
    getContentPane().add(p1, "North");
    getContentPane().add(p2, "Center");
	getContentPane().add(p3, "South");
	
    addWindowListener(new WindowAdapter(){
            @Override
        public void windowClosing(WindowEvent we){
            try{
				if(socket != null){
					socket.close();
				}
             server.close();
            }catch(Exception e){
                //e.printStackTrace();
            }
        }
    });
}
public static void main(String a[])throws Exception{
	NetworkMonitor cs = new NetworkMonitor();
	cs.setVisible(true);
	cs.setSize(700,600);
	new NetworkThread(cs);
}
}