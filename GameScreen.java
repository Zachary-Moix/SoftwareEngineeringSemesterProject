package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*; 
import java.io.*; 

public class GameScreen extends JFrame
{
	//each player and the dealer has a bank of 12 jlabels... 
	private JLabel 	p11, p12, p13, p14, p15, p16, p17, p18, p19, p110, p111, p112, 
	 				p21, p22, p23, p24, p25, p26, p27, p28, p29, p210, p211, p212,
	 				p31, p32, p33, p34, p35, p36, p37, p38, p39, p310, p311, p312,
	 				p41, p42, p43, p44, p45, p46, p47, p48, p49, p410, p411, p412,
	 				p51, p52, p53, p54, p55, p56, p57, p58, p59, p510, p511, p512,
	 				d11, d12, d13, d14, d15, d16, d17, d18, d19, d110, d111, d112;
	
	//these are for the side nametags/main screen...
	private JPanel p1, p2, p3, p4, p5, dealer, players, game;
	
	//these are for the game screen, each panel will have 12 labels asscoiated with it...
	private JPanel player1, player2, player3, player4, player5, d;
	
	//betting 
	private JPanel betting; 
	private JComboBox bets;
	private JButton hit, stand, doubleDown, bet; 
	private JLabel setBet; 
	
	private String[] betAmounts = {"$5", "$10", "$20", "$50", "$100"};
	
	private Image test; 
	
	public GameScreen()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//this was just for testing...
		try 
		{
			test = ImageIO.read(new File("GUI/Test.png"));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		p11 = new JLabel(new ImageIcon(test)); 
		p21 = new JLabel(new ImageIcon(test)); 
		p31 = new JLabel(new ImageIcon(test));
		p41 = new JLabel(new ImageIcon(test));
		p51 = new JLabel(new ImageIcon(test));
		d11 = new JLabel(new ImageIcon(test));
		
		setBet = new JLabel("Bet:");
		hit = new JButton("Hit");
		stand = new JButton("Stand");
		doubleDown = new JButton("Double Down");
		bet = new JButton("Place Bet");
		bets = new JComboBox(betAmounts); 
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
		
		p1.add(new JLabel("Player 1"));
		p2.add(new JLabel("Player 2"));
		p3.add(new JLabel("Player 3"));
		p4.add(new JLabel("Player 4"));
		p5.add(new JLabel("Player 5"));
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
		player1 = new JPanel();
		player2 = new JPanel();
		player3 = new JPanel();
		player4 = new JPanel();
		player5 = new JPanel();
		d       = new JPanel();

		//adding the jlabels into the player panels
		player1.add(p11); 
		player2.add(p21);
		player3.add(p31);
		player4.add(p41);
		player5.add(p51);
		d.add(d11);
		
		//adding the player panels into the game panel...
		game.add(player1);
		game.add(player2);
		game.add(player3);
		game.add(player4);
		game.add(player5);
		game.add(d);
		
		this.add(game, BorderLayout.CENTER); 
		this.add(players, BorderLayout.WEST); 
		this.add(betting, BorderLayout.SOUTH); 
		this.setVisible(true);
		this.pack();
	}
	
	public static void main(String[] args)
	{
		new GameScreen();
	}
}
