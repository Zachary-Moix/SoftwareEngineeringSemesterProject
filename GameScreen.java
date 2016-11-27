package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.OverlayLayout;

public class GameScreen extends JPanel
{
	//each player and the dealer has a bank of 12 jlabels... 
	/*private JLabel 	p11, p12, p13, p14, p15, p16, p17, p18, p19, p110, p111, p112, 
	 				p21, p22, p23, p24, p25, p26, p27, p28, p29, p210, p211, p212,
	 				p31, p32, p33, p34, p35, p36, p37, p38, p39, p310, p311, p312,
	 				p41, p42, p43, p44, p45, p46, p47, p48, p49, p410, p411, p412,
	 				p51, p52, p53, p54, p55, p56, p57, p58, p59, p510, p511, p512,
	 				d11, d12, d13, d14, d15, d16, d17, d18, d19, d110, d111, d112;
	*/
	//these are for the side nametags/main screen...
	private JPanel p1, p2, p3, p4, p5, dealer, players, game;
	private JLabel ptag1, ptag2, ptag3, ptag4, ptag5;
	//these are for the game screen, each panel will have 12 labels asscoiated with it...
	private DrawPanel player1, player2, player3, player4, player5, d;
	
	//betting 
	private JPanel betting; 
	private JComboBox bets;
	private JButton hit, stand, doubleDown, bet; 
	private JLabel setBet, message, balance;
	private String[] betAmounts = {"$5", "$10", "$20", "$50", "$100"};
	
	private Image test; 
	
	public GameScreen(CardLayout cl, JPanel container, Client client, JFrame frame)
	{	
		this.setLayout(new BorderLayout());
		GameScreenControl gcs = new GameScreenControl(cl, container, client, bets, frame);
		
		//this was just for testing...
		try 
		{
			test = ImageIO.read(new File("main/1diamond.png"));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*p11 = new JLabel(new ImageIcon(test)); 
		p12 = new JLabel(new ImageIcon(test));
		p13 = new JLabel(new ImageIcon(test));
		p21 = new JLabel(new ImageIcon(test)); 
		p31 = new JLabel(new ImageIcon(test));
		p41 = new JLabel(new ImageIcon(test));
		p51 = new JLabel(new ImageIcon(test));
		d11 = new JLabel(new ImageIcon(test));*/
		
		setBet = new JLabel("Bet:");
		hit = new JButton("Hit");
		hit.addActionListener(gcs);
		stand = new JButton("Stand");
		stand.addActionListener(gcs);
		doubleDown = new JButton("Double Down");
		doubleDown.addActionListener(gcs);
		bet = new JButton("Place Bet");
		bet.addActionListener(gcs);
		bets = new JComboBox(betAmounts); 
		bets.addActionListener(gcs);
		bets.setSelectedIndex(0);
		betting = new JPanel();
		
		//this can lock in the bet once they hit the button
		//bets.setEnabled(false);
		
		//adding the betting stuff to the panel 
		betting.add(setBet);
		betting.add(bets);
		betting.add(bet); 
		betting.add(new JLabel("")); 
		betting.add(hit);
		betting.add(stand);
		betting.add(doubleDown);
		
		//init of the player name panels
		p1 = new JPanel(); 
		p2 = new JPanel();
		p3 = new JPanel(); 
		p4 = new JPanel();
		p5 = new JPanel(); 
		dealer = new JPanel(); 
		players = new JPanel();
		
		//setting the player nametags on the left of the screen
		ptag1 = new JLabel("Player 1");
		ptag2 = new JLabel("Player 2");
		ptag3 = new JLabel("Player 3");
		ptag4 = new JLabel("Player 4");
		ptag5 = new JLabel("Player 5");
		p1.add(ptag1);
		p2.add(ptag2);
		p3.add(ptag3);
		p4.add(ptag4);
		p5.add(ptag5);
		dealer.add(new JLabel("Dealer")); 
		
		//adding the player nametags to the container
		players.setLayout(new GridLayout(6,1));
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		players.add(p5);
		players.add(dealer);
		
		//Seth... Look below this for what you need.
		
		game = new JPanel();
		game.setLayout(new GridLayout(6, 1));

		//panels that contain the labels for each player... 
		player1 = new DrawPanel();
		player2 = new DrawPanel();
		player3 = new DrawPanel();
		player4 = new DrawPanel();
		player5 = new DrawPanel();
		d       = new DrawPanel();

		//adding the jlabels into the player panels
		/*player1.addImage(new ImageIcon(test));
		player1.addImage(new ImageIcon(test));
		player1.addImage(new ImageIcon(test));
		player2.add(p21);
		player3.add(p31);
		player4.add(p41);
		player5.add(p51);
		d.add(d11);*/
		
		//adding the player panels into the game panel...
		game.add(player1);
		game.add(player2);
		game.add(player3);
		game.add(player4);
		game.add(player5);
		game.add(d);
		
		
		/*//More Test Buttons
		JButton p1Test = new JButton("1");
		JButton p2Test = new JButton("2");
		JButton p3Test = new JButton("3");
		JButton p4Test = new JButton("4");
		JButton p5Test = new JButton("5");
		JButton dTest  = new JButton("D");
		
		EventHandler eh = new EventHandler();
		p1Test.addActionListener(eh);
		p2Test.addActionListener(eh);
		p3Test.addActionListener(eh);
		p4Test.addActionListener(eh);
		p5Test.addActionListener(eh);
		dTest.addActionListener(eh);

		JPanel cardsTest = new JPanel(new GridLayout(5,1));
		cardsTest.add(p1Test);
		cardsTest.add(p2Test);
		cardsTest.add(p3Test);
		cardsTest.add(p4Test);
		cardsTest.add(p5Test);
		cardsTest.add(dTest);
		this.add(cardsTest, BorderLayout.EAST);*/
		
		message = new JLabel("Welcome!");
		message.setFont(new Font("Console", Font.PLAIN, 26));
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jp.add(message);
		this.add(jp,BorderLayout.NORTH);
		
		balance = new JLabel("Balance: ");
		jp = new JPanel(new FlowLayout(FlowLayout.LEADING));
		jp.add(balance);
		this.add(jp,BorderLayout.EAST);
		
		this.add(game, BorderLayout.CENTER); 
		this.add(players, BorderLayout.WEST); 
		this.add(betting, BorderLayout.SOUTH); 
		this.setVisible(true);
		//this.pack();
		this.setSize(900,800);
	}
	
	public void updateBalance(String bal) {
		balance.setText("Balance: $" + bal);
	}
	
	public void setMessage(String msg) {
		message.setText(msg);
	}
	
	public JLabel getPlayerLabel(int i) {
		switch(i) {
			case 1:
				return ptag1;
			case 2:
				return ptag2;
			case 3:
				return ptag3;
			case 4:
				return ptag4;
			case 5:
				return ptag5;
		}
		return null;
	}
	public void setPlayerCard(int i, String file) { 
		file = "main/" + file;
		//System.out.println("Setting card");
		switch(i) {
			case 0: 
				player1.addImage(file);
				player1.revalidate();
				player1.repaint();
				break;
			case 1:
				player2.addImage(file);
				player2.revalidate();
				player2.repaint();
				break;
			case 2:
				player3.addImage(file);
				player3.revalidate();
				player3.repaint();
				break;
			case 3: 
				player4.addImage(file);
				player4.revalidate();
				player4.repaint();
				break;
			case 4:
				player5.addImage(file);
				player5.revalidate();
				player5.repaint();
				break;
			case -1:
				d.addImage(file);
				d.revalidate();
				d.repaint();
				break;
		}
	}
	
	public void resetCards() {
		player1.reset();
		player1.repaint();
		player2.reset();
		player2.repaint();
		player3.reset();
		player3.repaint();
		player4.reset();
		player4.repaint();
		player5.reset();
		player5.repaint();
		d.reset();
		d.repaint();
	}
	
	class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JButton b = (JButton)arg0.getSource();
			
			if(b.getText().equals("1")) {
				player1.addImage("main/1diamond.png");
				System.out.println("Added.");
				player1.repaint();
			}
			else if(b.getText().equals("2")) {
				player2.addImage(new ImageIcon("main/1diamond.png"));
				System.out.println("Added.");
				player2.repaint();
			}
			else if(b.getText().equals("3")) {
				player3.addImage(new ImageIcon("main/1diamond.png"));
				System.out.println("Added.");
				player3.repaint();
			}
			else if(b.getText().equals("4")) {
				player4.addImage(new ImageIcon("main/1diamond.png"));
				System.out.println("Added.");
				player4.repaint();
			}
			else if(b.getText().equals("5")) {
				player5.addImage("main/1diamond.png");
				System.out.println("Added.");
				player5.repaint();
			}
			else if(b.getText().equals("D")) {
				d.addImage(new ImageIcon("main/1diamond.png"));
				System.out.println("Added.");
				d.repaint();
			}
		}
		
	}
}