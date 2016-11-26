package main;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class ClientGUI extends JFrame implements ActionListener
{
	JTextArea text;
	JScrollPane scroll;
	JButton betButton, gameButton, choiceButton, loginButton, createButton, joinButton, checkButton;
	Client c;
	
	public ClientGUI() {
		
		c = new Client();
		
		loginButton = new JButton("Login");
		createButton = new JButton("Create");
		JPanel buffer = new JPanel();
		buffer.add(loginButton);
		loginButton.addActionListener(this);
		JPanel bigBuffer = new JPanel();
		bigBuffer.add(buffer);
		buffer = new JPanel();
		buffer.add(createButton);
		createButton.addActionListener(this);
		bigBuffer.add(buffer);
		this.add(bigBuffer, BorderLayout.NORTH);
		
		JPanel bigBuffer2 = new JPanel();
		joinButton = new JButton("Join Game");
		joinButton.addActionListener(this);
		buffer = new JPanel();
		buffer.add(joinButton);
		bigBuffer2.add(buffer);
		
		checkButton = new JButton("Check Balance");
		checkButton.addActionListener(this);
		buffer = new JPanel();
		buffer.add(checkButton);
		bigBuffer2.add(buffer);
		this.add(bigBuffer2, BorderLayout.EAST);
		
		text = new JTextArea();
		c.setLog(text);
		scroll = new JScrollPane(text);
		c.setScroll(scroll);
		scroll.setPreferredSize(new Dimension(350,300));
		betButton = new JButton("BetData");
		betButton.addActionListener(this);
		gameButton = new JButton("GameChoice: Hit");
		gameButton.addActionListener(this);
		choiceButton = new JButton("GameChoice: Stand");
		choiceButton.addActionListener(this);
		
		buffer = new JPanel();
		buffer.add(scroll);
		this.add(buffer, BorderLayout.CENTER);
		
		bigBuffer = new JPanel();
		buffer = new JPanel();
		buffer.add(betButton);
		bigBuffer.add(buffer);
		buffer = new JPanel();
		buffer.add(gameButton);
		bigBuffer.add(buffer);
		buffer = new JPanel();
		buffer.add(choiceButton);
		bigBuffer.add(buffer);
		this.add(bigBuffer, BorderLayout.SOUTH);
		
		try
		{
			c.openConnection();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setVisible(true);
		this.setSize(new Dimension(640,400));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Client");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ClientGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == betButton) {
			text.append("Bet Button Pressed... sending data to server.\n");
			BetData bd = new BetData(50, c.getGameNo(), c.getPlayerNo(), "???");
			//bd.print();
			try
			{
				c.sendToServer(bd);
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == gameButton) {
			text.append("Game Choice: Hit Button Pressed... sending data to server.\n");
			GameChoice gc = new GameChoice(1, c.getGameNo(), c.getPlayerNo());
			//gc.print();
			try
			{
				c.sendToServer(gc);
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == choiceButton) {
			text.append("Choice Button: Stand Pressed... sending data to server.\n");
			GameChoice gc = new GameChoice(0, c.getGameNo(), c.getPlayerNo());
			//gc.print();
			try
			
			{
				c.sendToServer(gc);
				//System.out.println("Should be sent...?");
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == loginButton) {
			LoginData ld = new LoginData(new JTextField("CovertWookiee"), new JPasswordField("abc123"));
			
			try
			{
				c.sendToServer(ld);
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == createButton) {
			LoginData ld = new LoginData(new JTextField("CovertWookiee"), new JPasswordField("abc123"));
			ld.setcreate(true);
			
			try
			{
				c.sendToServer(ld);
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == joinButton) {
			try
			{
				c.sendToServer("REQUEST JOIN GAME");
				text.append("Requesting game join...\n");
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == checkButton) {
			try
			{
				c.sendToServer("REQUEST CHECK BALANCE");
				text.append("Requesting balance check\n");
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
