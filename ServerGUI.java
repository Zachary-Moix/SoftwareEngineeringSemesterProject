package main;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class ServerGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JLabel status; //Initialized to “Not Connected”
	private String[] labels = {"Port #", "Timeout"};
	private JTextField[] textFields = new JTextField[labels.length];
	private JTextArea log;
	private JScrollPane jsp;
	private JButton listen, close, stop, quit;
	private JComboBox playersList;
	private Server server;
	
	public ServerGUI()
	{
		
		//North
		status = new JLabel("Not Connected");
		status.setForeground(Color.RED);
		JPanel north = new JPanel();
		north.add(new JLabel("Status:"));
		north.add(status);
		this.add(north, BorderLayout.NORTH);
		
		//Center
		JPanel center = new JPanel();
		BoxLayout box = new BoxLayout(center, BoxLayout.Y_AXIS);
		center.setLayout(box);
		
		JPanel group = new JPanel();
		GroupLayout layout = new GroupLayout(group);
		group.setLayout(layout);
		
		textFields[0] = new JTextField(20);
		textFields[0].setText("8300");
		textFields[1] = new JTextField(10);
		textFields[1].setText("500");

		FlowLayout right = new FlowLayout(FlowLayout.TRAILING);
		FlowLayout left = new FlowLayout(FlowLayout.LEADING);
		JPanel buffer1, buffer2, buffer3, buffer4;
		buffer1 = new JPanel(right);
		buffer2 = new JPanel(right);
		buffer3 = new JPanel(left);
		buffer4 = new JPanel(left);
		buffer1.add(new JLabel(labels[0]));
		buffer2.add(new JLabel(labels[1]));
		buffer3.add(textFields[0]);
		buffer4.add(textFields[1]);
		
		
		//creates parallel group of labels and corresponding text fields
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		hGroup.addGroup(layout.createParallelGroup().addComponent(buffer1).addComponent(buffer2));
		hGroup.addGroup(layout.createParallelGroup().addComponent(buffer3).addComponent(buffer4));
		layout.setHorizontalGroup(hGroup);
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(buffer1).addComponent(buffer3));
		vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(buffer2).addComponent(buffer4));
		layout.setVerticalGroup(vGroup);
		buffer1 = new JPanel();
		buffer1.add(group);
		center.add(buffer1);
		
		JPanel serverLog = new JPanel();
		BoxLayout box2 = new BoxLayout(serverLog, BoxLayout.PAGE_AXIS);
		serverLog.setLayout(box2);
		
		JLabel label = new JLabel("Server Log Below");
		buffer1 = new JPanel();
		buffer1.setPreferredSize(new Dimension(100,20));
		buffer1.add(label);
		serverLog.add(buffer1);
		Dimension d = new Dimension(450,200);
		log = new JTextArea();
		jsp = new JScrollPane(log);
		jsp.setPreferredSize(d);
		buffer1 = new JPanel();
		buffer1.setPreferredSize(new Dimension(460,230));
		buffer1.add(jsp);
		serverLog.add(buffer1);
		center.add(serverLog);
		
		EventHandler eh = new EventHandler();
		listen = new JButton("Listen");
		listen.addActionListener(eh);
		close = new JButton("Close");
		close.addActionListener(eh);
		stop = new JButton("Stop");
		stop.addActionListener(eh);
		quit = new JButton("Quit");
		quit.addActionListener(eh);
		JPanel buttons = new JPanel();
		buttons.add(listen);
		buttons.add(close);
		buttons.add(stop);
		buttons.add(quit);
		this.add(buttons, BorderLayout.SOUTH);
		
		JLabel lab = new JLabel("Players Per Game: ");
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout(FlowLayout.TRAILING));
		jp.add(lab);
		JPanel bigPanel = new JPanel();
		bigPanel.add(jp);
		
		String[] ppg = {"2", "3", "4", "5"};
		playersList = new JComboBox(ppg);
		playersList.setSelectedIndex(0);
		playersList.addActionListener(eh);
		jp = new JPanel();
		jp.setLayout(new FlowLayout(FlowLayout.LEADING));
		jp.add(playersList);
		bigPanel.add(jp);
		center.add(bigPanel);
		
		server = new Server();
		server.setLog(log);
		server.setStatus(status);
		server.setPane(jsp);
		this.add(center);
		this.setSize(500,600);
		this.setResizable(false);
		this.setTitle("Server");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public JTextField getTextFieldAt(int index)
	{
		return textFields[index];
	}
	
	public JLabel getStatus()
	{
		return status;
	}
	
	public JTextArea getLog()
	{
		return log;
	}
	
	class EventHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource() instanceof JButton) {
				JButton b = (JButton)e.getSource();
				if(b == listen)
				{
					if(textFields[0].getText().isEmpty() || textFields[1].getText().isEmpty())
					{
						log.setText(log.getText() + "Port Number and/or timeout not entered before pressing listen.\n");
					}
					else
					{
						if(server.isListening())
							log.append("The server is already listening.\n");
						else
						{
							server.setPort(Integer.parseInt(textFields[0].getText()));
							server.setTimeout(Integer.parseInt(textFields[1].getText()));
							try
							{
								server.listen();
							}
							catch(IOException ioe)
							{
								log.append(ioe.getMessage() + "\n");
							}
						}
					}
				}
				else if(b == close)
				{
					if(!server.isListening())
					{
						log.setText(log.getText() + "Server not currently started.\n");
					}
					else
					{
						try
						{
							server.close();
						}
						catch(IOException ioe)
						{
							ioe.printStackTrace();
						}
					}
				}
				else if(b == stop)
				{
					if(!server.isListening())
					{
						log.setText(log.getText() + "Server not currently started.\n");
					}
					else
					{
						server.stopListening();
					}
				}
				else if(b == quit)
				{
					try
					{
						server.close();
					}
					catch(IOException ioe)
					{
						ioe.printStackTrace();
					}
					System.exit(0);
				}
			}
			else if(e.getSource() instanceof JComboBox) {
				JComboBox jb = (JComboBox)e.getSource();
				server.setPlayersPerGame(Integer.parseInt((String)jb.getSelectedItem()));
			}
		}
	}
	
	public static void main(String[] args)
	{
		new ServerGUI();
	}
}
