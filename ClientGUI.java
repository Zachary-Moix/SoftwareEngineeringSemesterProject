package main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame {
	private JPanel view1, view2, view3, view4, view5, view6;
	private Client client;
	private CardLayout cl = new CardLayout();
	private JPanel container;
	private JFrame frame;
	
	public ClientGUI() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame = this;
		
		client = new Client();
		client.setHost("localhost");
		client.setPort(8300);
		client.setFrame(this);
		
		try {
			client.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		container = new JPanel(cl);
		client.setContainer(container);
		client.setCardLayout(cl);
		client.setFrame(this);
		
		view1 = new MainScreen(cl,container,this);
		view2 = new LoginScreen(cl,container,client,this);
		view3 = new CreateAccountScreen(cl,container,client,this);
		view4 = new LobbyScreen(cl,container,client,this);
		view5 = new GameScreen(cl,container,client,this);
		
		container.add(view1,"1");
		container.add(view2,"2");
		container.add(view3,"3");
		container.add(view4,"4");
		container.add(view5,"5");
		
		cl.show(container, "1");
		
		this.add(container,BorderLayout.CENTER);
		
		
		this.setVisible(true);
	}
	
	public void remoteSetSize(int x, int y) {
		frame.setSize(x, y);
	}
	
	public void remotePack() {
		frame.pack();
	}
	
	public static void main(String[] args) {
		new ClientGUI();
	}
}