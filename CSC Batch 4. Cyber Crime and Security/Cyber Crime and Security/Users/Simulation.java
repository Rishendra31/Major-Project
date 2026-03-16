package com;
import java.awt.Dimension;
import javax.swing.JComponent;
import java.awt.geom.Rectangle2D;
import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
public class Simulation extends JComponent{
	public int option=0;
	public ArrayList<Users> users = new ArrayList<Users>();
	float dash1[] = {10.0f};
	BasicStroke dashed = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f, dash1, 0.0f);
	BasicStroke rect=new BasicStroke(1f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1f,new float[] {2f},0f);
	Dimension dim;
	Users source;
	boolean flag;
	
public void setSender(Users source){
	this.source = source;
}

public Simulation() {
	super.setBackground(new Color(255,0,255));
	this.setBackground(new Color(255,0,255));
}
public ArrayList<Users> getList(){
	return users;
}
public void removeAll(){
	option=0;
	users.clear();
	repaint();
}
public void paintComponent(Graphics g1){
	super.paintComponent(g1);
	GradientPaint gradient = new GradientPaint(0, 0, Color.green, 175, 175, Color.yellow,true); 
	Graphics2D g = (Graphics2D)g1;
	g.setPaint(gradient);
	g.setStroke(rect);
	Rectangle2D rectangle = new Rectangle2D.Double(400,10,200,40);
	g.setStroke(rect);
	g.draw(rectangle);
	g.drawString("Server",480,40);
	rectangle = new Rectangle2D.Double(430,80,150,30);
	g.setStroke(rect);
	g.draw(rectangle);
	g.drawString("Network Monitor",470,100);
	if(option == 0){
		for(int i=0;i<users.size();i++){
			Users user = users.get(i);
			user.draw(g,"fill");
			g.drawString(user.getNode(),user.x+10,user.y+50);
		}
	}
	
	if(option == 1){
		for(int i=0;i<users.size();i++){
			Users user = users.get(i);
			user.draw(g,"fill");
			g.drawString(user.getNode(),user.x+10,user.y+50);
		}
		g.setStroke(dashed);
		g.drawLine(source.x+10,source.y+10,470,110);
		g.drawLine(500,80,500,50);
		
	}
}	
}