package com;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.io.FileOutputStream;
public class Download extends Thread{
	Simulation sim;
	String user,file;
	byte data[];
	
public Download(String user,String file,Simulation sim){
	this.user = user;
	this.file = file;
	this.sim = sim;
	
}

public void run(){
	try{
		Users sender = null;
		for(int i=0;i<sim.users.size();i++){
			Users node = sim.users.get(i);
			if(node.getNode().equals(user)){
				sender = node;
				break;
			}
		}
		Network.l3.setText("User : "+sender.getNode()+" requesting file "+file);
		Socket socket = new Socket("localhost",3333);
        ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
        Object req[]={"download",file,user};
        out.writeObject(req);
        out.flush();
        Object res[]=(Object[])in.readObject();
		byte data[] = (byte[])res[0];
		FileOutputStream fout = new FileOutputStream("C:/Download/"+file);
		fout.write(data,0,data.length);
		fout.close();
		for(int k=0;k<6;k++){
			sim.setSender(sender);
			sim.option=1;
			sim.repaint();
			sleep(80);
			sim.option=0;
			sim.repaint();
			sleep(40);
		}
		Network.l3.setText(file+" downloaded to C:/Download directory");
		JOptionPane.showMessageDialog(null,file+" downloaded to E directory");
	}catch(Exception e){
		e.printStackTrace();
	}
}
}