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
	
	public MainScreen(CardLayout cl, JPanel container, Client client, JFrame frame)
	{
		frame.setSize(300,450);
		
		MainScreenControl msc = new MainScreenControl(cl, container, client, frame);
		this.setLayout(new BorderLayout());
		title = new JLabel("Blackjack!");
		//serif
		title.setFont(new Font("Bradley Hand ITC", Font.BOLD, 40));
		create = new JButton("Create Account");
		create.addActionListener(msc);
		login =  new JButton("Login");
		login.addActionListener(msc);
		connect = new JButton("Connect");
		connect.addActionListener(msc);
		
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
		buttons.add(connect);
		buttons.add(create);
		buttons.add(login);

		JMenuBar jmb = new JMenuBar();
		JMenu jm = new JMenu("File");
		jmb.add(jm);
		JMenuItem jmi = new JMenuItem("Configure");
		jmi.addActionListener(msc);
		jm.add(jmi);
		
		frame.setJMenuBar(jmb);
		this.add(t, BorderLayout.NORTH);
		this.add(img, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.SOUTH);

	}
	
	public void attemptConnect() {
		
	}
}