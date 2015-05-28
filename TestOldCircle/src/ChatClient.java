import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;


public class ChatClient {
	
	//Make private variables, private means only methods in the class can access them
	private JTextArea incoming;
	private JTextField outgoing;
	private String username;
	private Socket sock;
	private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ChatObject myChat;
    private LoginPage loginPage;
    private InGameChat gameChat;
    private PlayerMob player;
    private ArrayList<PlayerMob> players;
    private ArrayList<String> usernames;
    private ChatClient networkStartup;
    private int userIndex = 0;

	public void startUp(ChatClient netStart){
		networkStartup = netStart;
		loginPage = new LoginPage(); 			//Make new object loginPage
		loginPage.setNetObject(netStart); 		//Send the ChatClient object to loginPage
		myChat = new ChatObject();				//Make new object called myChat
		player = new PlayerMob(netStart);
		players = new ArrayList<PlayerMob>();	//Make an array list to hold all other players from server
		usernames = new ArrayList<String>();
		gameChat = new InGameChat(player);		//Make new object gameChat
		gameChat.setNetObject(netStart);		//Send the ChatClient object to gameChat
		setUpNetworking();						//call startUpNetworking
		loginPage.login();
	}

	private void setUpNetworking(){
		//create a socket connection to the server on port 5000, and outputstream, inputstream 
		//and have a thread call the IncomingReader method
		try {
			sock = new Socket("127.0.0.1",5000);
			outputStream = new ObjectOutputStream(sock.getOutputStream());
			inputStream = new ObjectInputStream(sock.getInputStream());
			Thread remote = new Thread(new IncomingReader());
			remote.start();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public void LoginPageLoginButtonListener (String usr, JFrame frm){
		username = usr;
		try{
			if(username.length() > 0) {
				myChat.setUsername(username);
				myChat.setNewPlayer(true);
				outputStream.writeUnshared(myChat);		//Sending the ChatObject to the server	
				outputStream.flush();
				frm.getContentPane().removeAll();
				gameChat.chat(frm);						//Sending the frame to InGameChat 
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void InGameChatInitialize(JTextField out,JTextArea in){
		incoming = in;
		outgoing = out;
		try{
			myChat.setMessage(username + " has joined!");	
			outputStream.writeUnshared(myChat);
			outputStream.flush();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void InGameChatSendButtonListener (JTextField out,JTextArea in){
		outgoing = out;
		incoming = in;

		try{
			myChat.setMessage(username + ": " + outgoing.getText());			
			outputStream.writeUnshared(myChat);
			outputStream.flush();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}

		outgoing.setText("");
		outgoing.requestFocus();
	}

	public void keyPressed(Boolean vertMove,int yMove,int yCoordinate){
		try{
			myChat.setYMove(yMove);
			if(players.get(userIndex) != null) myChat.setYCoordinate(players.get(userIndex).getYCoordinate());
			myChat.setMessage(null);
			outputStream.writeUnshared(myChat);
			outputStream.flush();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void keyPressed(int xMove,Boolean horMove,int xCoordinate){
		try{
			myChat.setXMove(xMove);
			if(players.get(userIndex) != null) myChat.setXCoordinate(players.get(userIndex).getXCoordinate());
			myChat.setMessage(null);
			outputStream.writeUnshared(myChat);
			outputStream.flush();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void keyReleased(Boolean vertMove, int yMove,int yCoordinate){	
		try{
			myChat.setYMove(yMove);
			if(players.get(userIndex) != null) myChat.setYCoordinate(players.get(userIndex).getYCoordinate());
			myChat.setMessage(null);
			myChat.setRefreshCoordinates(false);
			outputStream.writeUnshared(myChat);
			outputStream.flush();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void keyReleased(int xMove,Boolean horMove, int xCoordinate){

		try{
			myChat.setXMove(xMove);
			userIndex = usernames.indexOf(myChat.getUsername());
			if(players.get(userIndex) != null) myChat.setXCoordinate(players.get(userIndex).getXCoordinate());
			myChat.setMessage(null);
			myChat.setRefreshCoordinates(false);
			outputStream.writeUnshared(myChat);
			outputStream.flush();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	

	
	public class IncomingReader implements Runnable{
		//reads object from server through inputStream if the object is read, we cast it as a ChatObject
		//then we append message inside chat object to the text area called incoming

		public void run(){
			
			Object obj = null;

			try{
				//synchronized is needed so that we do not try to make more than one socket connection at a time.
				synchronized(inputStream){
					while((obj=inputStream.readUnshared()) != null ) {
						ChatObject info = (ChatObject) obj;
						if(incoming != null  && info.getMessage() != null) incoming.append(info.getMessage() + "\n");
					
						
						if(((usernames.indexOf(info.getUsername()) < 0 || usernames.indexOf(info.getUsername()) >= usernames.size())) && info.getUsername() != "undefined"){
							usernames.add(info.getUsername());
							PlayerMob aPlayer = new PlayerMob(networkStartup);
							players.add(aPlayer);
							//System.out.println(aPlayer.xCoordinate + " " + aPlayer.yCoordinate + " " + players.size() + "\n\n\n");
							
							gameChat.drawFromServer(aPlayer);
						}
						else{
							int infoIndex = info.getArrayList().indexOf(info.getUsername());
							try{
								players.get(infoIndex).readMove(info,infoIndex);;
							}
							catch(IndexOutOfBoundsException e){
								//Do Nothing
							}
						}
						
						gameChat.setPlayers(players);
					}
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}