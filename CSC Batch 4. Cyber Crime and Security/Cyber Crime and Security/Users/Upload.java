package com;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
public class Upload extends Thread{
	Simulation sim;
	String user,file;
	byte data[];
	JComboBox cb;
public Upload(String user,String file,byte data[],Simulation sim,JComboBox cb){
	this.user = user;
	this.file = file;
	this.sim = sim;
	this.data = data;
	this.cb = cb;
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
		Network.l3.setText("User : "+sender.getNode()+" uploading file "+file);
		Socket socket = new Socket("localhost",4444);
        ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
        Object req[]={"upload",file,data};
        out.writeObject(req);
        out.flush();
        Object res[]=(Object[])in.readObject();
		String msg = (String)res[0];
		for(int k=0;k<6;k++){
			sim.setSender(sender);
			sim.option=1;
			sim.repaint();
			sleep(80);
			sim.option=0;
			sim.repaint();
			sleep(40);
		}
		Network.l3.setText(msg);
		if(msg.equals(file+" saved at server"))
			cb.addItem(file);
		JOptionPane.showMessageDialog(null,msg);
	}catch(Exception e){
		e.printStackTrace();
	}
}
}