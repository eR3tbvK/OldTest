import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;


public class LoginPage {

	private JTextField inputUsername;
	private JFrame frame;
	private ChatClient networkStartup;
	
	public void login(){
		
		frame = new JFrame("Rebirth of Martial Arts");				//Set title

		JPanel mainPanel = new JPanel();
		JPanel linePanel = new JPanel();
		JLabel inputUsernameLabel = new JLabel("inputUsername:");	//Make userinput label 
		
		inputUsername = new JTextField(10);							//Make userinput field 

		JButton loginButton = new JButton("login"); 				//Make button
		loginButton.addActionListener(new LoginButtonListener());	//Listener for the login button
		
		// Listener for ENTER key
		inputUsername.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	networkStartup.LoginPageLoginButtonListener(inputUsername.getText(),frame);
            }
        });

		mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS ) );	//Set layout
		
		//Add the interface
		linePanel.add(inputUsernameLabel);
		linePanel.add(inputUsername);
		linePanel.add(loginButton);
		mainPanel.add(linePanel);

		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(600, 400);
		frame.setVisible(true);		//Visible the interface
	
	}
	
	public void setNetObject(ChatClient netObj){
		networkStartup = netObj;		//Set networkStartup to the original ChatClient object
	}
	
	public class LoginButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			networkStartup.LoginPageLoginButtonListener(inputUsername.getText(),frame);		//Calling LoginPageLoginButtonListener including the userinput and the frame
		}
	}
}