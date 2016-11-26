package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MainScreen extends JFrame
{
	private JLabel title, img;
	private JButton create, connect, login;
	private JPanel t, buttons, image;
	private Image im;
	
	public MainScreen()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		title = new JLabel("21");
		create = new JButton("Create Account");
		connect = new JButton("Connect");
		login =  new JButton("Login");
		
		t = new JPanel();
		t.add(title);
		
		try 
		{
			im = ImageIO.read(new File("GUI/Test.png"));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ImageIcon ico = new ImageIcon(im); 
		
		img = new JLabel(); 
		img.setIcon(ico);
		
		image = new JPanel(); 
		
		image.add(img); 
		
		
		buttons = new JPanel();
		buttons.add(create);
		buttons.add(login);
		buttons.add(connect);
	
		this.add(t, BorderLayout.NORTH);
		this.add(image, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.SOUTH);
		this.setVisible(true);
		this.pack();
	}
	
	public static void main(String[] args)
	{
		new MainScreen(); 
	}
}
