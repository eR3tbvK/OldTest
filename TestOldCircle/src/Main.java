public class Main {
	
	public static void main(String[] args){
		ChatClient networkStartup = new ChatClient(); 	//Make new ChatClient Object called networkStartup
		networkStartup.startUp(networkStartup);  		//call the startUp method and send the newly made object to it
	}
}