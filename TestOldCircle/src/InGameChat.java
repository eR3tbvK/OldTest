import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class InGameChat extends JFrame {

	private static final long serialVersionUID = 5;

	private JTextArea incoming;
	private JTextField outgoing;
	private JFrame frame;
	private ChatClient networkStartup;
	private PlayerMob player;
	private JPanel chatPanel;
	private JPanel chatPanelSection;
	private JLayeredPane layeredPane;
	private JLayeredPane keyListenerLayer;
	private ArrayList<PlayerMob> players;


	public InGameChat(PlayerMob plyr){
		player = plyr;
		layeredPane = new JLayeredPane();
	}

	public void chat(JFrame frm){
		frame = frm;

		AddKeyListener keyListener =new AddKeyListener();
		keyListener.setPlayer(player);

		chatPanel = new JPanel();
		chatPanelSection = new JPanel();

		keyListenerLayer = new JLayeredPane();

		keyListenerLayer.add(keyListener, 10);
		
		incoming = new JTextArea(5,49);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		DefaultCaret caret = (DefaultCaret)incoming.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing = new JTextField(45);
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());

		frame.add(BorderLayout.CENTER, keyListenerLayer);
		frame.add(BorderLayout.CENTER, layeredPane);

		keyListener.setFocusable(true);
		keyListener.requestFocusInWindow();

		chatPanel.setLayout( new BoxLayout( chatPanel, BoxLayout.Y_AXIS ) );

		chatPanel.add(qScroller);
			chatPanelSection.add(outgoing);
			chatPanelSection.add(sendButton);
		chatPanel.add(chatPanelSection);

		frame.add(BorderLayout.SOUTH, chatPanel);
		frame.setSize(600, 400);
		frame.setVisible(true);
		frame.repaint();

		//initialize
		networkStartup.InGameChatInitialize(outgoing,incoming);

		frame.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				keyListener.setFocusable(true);
				keyListener.requestFocusInWindow();
			}
		});
		
		outgoing.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	networkStartup.InGameChatSendButtonListener(outgoing,incoming);
        }});



	}
	
	public void startDrawingPanelThread(){
		try{
			Thread chatThread = new Thread(new DrawingPanel());
			chatThread.start();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void drawFromServer(PlayerMob plyr){
		layeredPane.add(plyr, 100);

	}

	public void setPlayers(ArrayList<PlayerMob> allPlayers){
		players = allPlayers;

	}

	public class DrawingPanel implements Runnable{
			
		public void run(){
			try{
				while(true){
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					frame.remove(layeredPane);
					Iterator<PlayerMob> allPlayers = players.iterator();
					PlayerMob aPlayer = null;
					while(allPlayers.hasNext()){
						aPlayer = (PlayerMob) allPlayers.next();
						//System.out.println("INTHELOOP:info.getUsername =" + info.getUsername() + " myChat.getUsername =" + myChat.getUsername());
						aPlayer.move();
					}

					frame.add(layeredPane,BorderLayout.CENTER);
					frame.repaint();

				}
			}catch (NullPointerException ed){
				System.err.println("for loop null catch");
				startDrawingPanelThread();
			}catch(Exception ev){}
		}
	}

	public void setNetObject(ChatClient netObj){
		networkStartup = netObj;
	}
	
	public class SendButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			networkStartup.InGameChatSendButtonListener(outgoing,incoming);
		}
	}
}