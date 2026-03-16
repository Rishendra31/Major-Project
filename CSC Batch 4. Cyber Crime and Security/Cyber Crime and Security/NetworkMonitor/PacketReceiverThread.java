package com;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.File;
import javax.swing.JTextArea;
import java.io.FileWriter;
import java.util.ArrayList;
public class PacketReceiverThread extends Thread{
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
	JTextArea area;
	
public void deleteFiles(File path){
	if(path.exists()){
		File[] dir = path.listFiles();
		for(int d=0;d<dir.length;d++){
			if(dir[d].isFile()){
				dir[d].delete();
			}else if(dir[d].isDirectory()){
				deleteFiles(dir[d]);
			}
		}
		if(path.isDirectory()){
			path.delete();
		}
	}
}

public PacketReceiverThread(Socket soc,JTextArea area){
    socket=soc;
	this.area=area;
    try{
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }catch(Exception e){
        e.printStackTrace();
    }
}
@Override
public void run(){
    try{
		Object input[]=(Object[])in.readObject();
        String type=(String)input[0];
		if(type.equals("upload")){
			String file = (String)input[1];
			byte data[] = (byte[])input[2];
			deleteFiles(new File("output"));
			FileWriter fout = new FileWriter("packets.txt");
			fout.write(Integer.toString(data.length));
			fout.close();
			Hadoop.run("packets.txt");
			if(NetworkMonitor.status.equals("normal")) {
				Socket soc = new Socket("localhost",3333);
				ObjectOutputStream oos = new ObjectOutputStream(soc.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(soc.getInputStream());
				Object res[] = {"upload",file,data};
				oos.writeObject(res);
				oos.flush();
				Object obj[] = (Object[])ois.readObject();
				out.writeObject(obj);
				out.flush();
				area.append(file+" size under limit and sent to server for storage\n");
			} else {
				Object res[] = {file+" contains Malicious content"};
				out.writeObject(res);
				out.flush();
				area.append(file+"  contains Malicious content\n");
				Socket soc = new Socket("localhost",3333);
				ObjectOutputStream oos = new ObjectOutputStream(soc.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(soc.getInputStream());
				Object res1[] = {"attack",file};
				oos.writeObject(res1);
				oos.flush();
				Object obj1[] = (Object[])ois.readObject();
				String msg1 = (String)obj1[0];
				area.append("Server Response : "+msg1+"\n");
			}
		}
		
    }catch(Exception e){
        e.printStackTrace();
    }
}
}
