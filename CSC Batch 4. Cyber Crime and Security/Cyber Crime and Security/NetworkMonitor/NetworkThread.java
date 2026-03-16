package com;
public class NetworkThread extends Thread{
	NetworkMonitor server;
public NetworkThread(NetworkMonitor server){
	this.server=server;
	start();
}
public void run(){
	server.start();
}
}