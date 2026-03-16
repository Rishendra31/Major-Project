package com;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.FileInputStream;
import java.io.File;
import javax.swing.JTextArea;
import java.io.FileOutputStream;
import java.util.ArrayList;
public class ProcessThread extends Thread{
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
	JTextArea area;
	
public ProcessThread(Socket soc,JTextArea area){
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
		if(type.equals("download")){
			String file = (String)input[1];
			String user = (String)input[2];
			FileInputStream fin = new FileInputStream("UploadFiles/"+file);
			byte b[] = new byte[fin.available()];
			fin.read(b,0,b.length);
			fin.close();
			Object res[] = {b};
			out.writeObject(res);
			out.flush();
			area.append(file+" File sent to user "+user+"\n");
		}
		if(type.equals("upload")){
			String file = (String)input[1];
			byte data[] = (byte[])input[2];
			FileOutputStream fout = new FileOutputStream("UploadFiles/"+file);
			fout.write(data,0,data.length);
			fout.close();
			Object res[] = {file+" saved at server"};
			out.writeObject(res);
			out.flush();
			area.append(file+" saved at server\n");
		}
		if(type.equals("attack")){
			String file = (String)input[1];
			Object res[] = {file+" ignore this file. Malicious Content detected"};
			out.writeObject(res);
			out.flush();
			area.append(file+" ignoring this file. Malicious Content  detected\n");
		}
		if(type.equals("filelist")){
			StringBuilder sb = new StringBuilder();
			File file = new File("UploadFiles");
			File list[] = file.listFiles();
			for(int i=0;i<list.length;i++){
				sb.append(list[i].getName()+",");
			}
			if(sb.length() > 0){
				sb.deleteCharAt(sb.length()-1);
			}
			if(sb.length() > 0){
				String arr[] = sb.toString().trim().split(",");
				Object res[] = {arr};
				out.writeObject(res);
				out.flush();
			} else {
				String arr[] = {"nofiles"};
				Object res[] = {arr};
				out.writeObject(res);
				out.flush();
			}
		}
    }catch(Exception e){
        e.printStackTrace();
    }
}
}
