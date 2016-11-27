package main;

import java.awt.*;
import javax.swing.*;

public class DrawPanel extends JPanel
{
	private ImageIcon[] list;
	private int numImgs;
	
	public DrawPanel() {
		numImgs = 0;
		list = new ImageIcon[12];
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int x = 0;
		
		for(int i = 0; i < numImgs; i++) {
			list[i].paintIcon(this,  g, x, 0);
			x += 60;
		}
	}
	
	public void addImage(String file) {
		//System.out.println(file);
		if(numImgs < 12) {
			list[numImgs] = new ImageIcon(file);
			numImgs++;
		}
	}
	
	public void addImage(ImageIcon image) {
		if(numImgs < 12) {
			list[numImgs] = image;
			numImgs++;
		}
	}
	
	public void reset() {
		numImgs = 0;
	}
}
