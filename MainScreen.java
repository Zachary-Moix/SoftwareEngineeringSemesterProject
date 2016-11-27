package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MainScreen extends JPanel
{
	private JLabel title; 
	private DrawPanel img;
	private JButton create, connect, login;
	private JPanel t, buttons, image;
	private Image im, im2;
	
	public MainScreen(CardLayout cl, JPanel container, JFrame frame)
	{
		frame.setSize(300,450);
		
		MainScreenControl msc = new MainScreenControl(cl, container, frame);
		this.setLayout(new BorderLayout());
		title = new JLabel("Blackjack!");
		//serif
		title.setFont(new Font("Bradley Hand ITC", Font.BOLD, 40));
		create = new JButton("Create Account");
		create.addActionListener(msc);
		login =  new JButton("Login");
		login.addActionListener(msc);
		
		t = new JPanel();
		t.add(title);
		
		try 
		{
			im = ImageIO.read(new File("main/1diamond.png"));
			im2 = ImageIO.read(new File("main/13club.png"));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(im == null || im2 == null) {
			System.out.println("Whahhh");
		}
		ImageIcon ico = new ImageIcon(im); 
		ImageIcon ico2 = new ImageIcon(im2);
		
		img = new DrawPanel(); 
		img.addImage(ico);
		img.addImage(ico2);
		
		image = new JPanel(); 
		image.setLayout(new FlowLayout(FlowLayout.CENTER));
		image.add(img); 
		
		
		buttons = new JPanel();
		buttons.add(create);
		buttons.add(login);
	
		this.add(t, BorderLayout.NORTH);
		this.add(img, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.SOUTH);

	}
}