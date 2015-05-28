import java.io.Serializable;
import java.util.ArrayList;

public class ChatObject implements Serializable {
	private static final long serialVersionUID = 3;

	private int xCoordinate;
	private int yCoordinate;
	private int xMove;
	private int yMove;

	private ArrayList<String> usernames;
	private String message;
	private String username = "undefined";
	private Boolean newPlayer = false;
	private Boolean refreshCoordinates = false;
	
	public void setRefreshCoordinates(Boolean refreshCoordinates){
		this.refreshCoordinates = refreshCoordinates;
	}
	
	public Boolean getRefreshCoordinates(){
		return refreshCoordinates;
	}
	
	public void setXCoordinate(int xCoordinate){
		this.xCoordinate = xCoordinate;
	}
	
	public int getXCoordinate(){
		return xCoordinate;
	}
	
	public void setYCoordinate(int yCoordinate){
		this.yCoordinate = yCoordinate;
	}
	
	public int getYCoordinate(){
		return yCoordinate;
	}
	
	public void setNewPlayer(Boolean newPlayer){
		this.newPlayer = newPlayer;
	}
	
	public Boolean getNewPlayer(){
		return newPlayer;
	}
	
	public void setArrayList(ArrayList<String> usernames){
		this.usernames = usernames;
		
	}
	
	public void addArrayList(){
		if(username != "undefined" && usernames.indexOf(username) < 0){
			usernames.add(username);
		}
	}
	
	public ArrayList<String> getArrayList(){
		return usernames;
	}
	
	public void setXMove(int x){
		xMove = x;
	}
	
	public int getXMove(){
		return xMove;
	}
	
	public void setYMove(int y){
		yMove = y;
	}
	
	public int getYMove(){
		return yMove;
	}
	
	public void setUsername(String usr){
		username = usr;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setMessage(String msg){
		message = msg;
	}
	
	public String getMessage(){
		return message;
	}
	
}