import java.awt.*;
import java.io.Serializable;
import javax.swing.*;

public class PlayerMob extends JPanel implements Serializable {
	private static final long serialVersionUID = 2;
	private int xCoordinate = 150;
	private int yCoordinate = 50;
	private int xMove = 0;
	private int yMove = 0;
	private int realXMove = 0;
	private int realYMove = 0;
	private Boolean pressedLeft = false;
	private Boolean pressedRight = false;
	private Boolean pressedUp = false;
	private Boolean pressedDown = false;
	private ChatClient networkStartup;
	private Boolean horVert = true;


	public PlayerMob(ChatClient netStartup) {
		networkStartup = netStartup;
		this.setOpaque(false);
        this.setBounds(xCoordinate, yCoordinate, 160, 120);
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

	public void keyPressedUp(){
		if (!pressedUp){
			yMove = -1;
			pressedUp = true;
			networkStartup.keyPressed(horVert, yMove,yCoordinate);
		}
	}

	public void keyPressedDown(){
		if (!pressedDown){
			yMove = 1;
			pressedDown = true;
			networkStartup.keyPressed(horVert, yMove,yCoordinate);
		}
	}

	public void keyPressedRight(){
		if (!pressedRight){
			xMove = 1;
			pressedRight = true;
			networkStartup.keyPressed(xMove, horVert,xCoordinate);
		}
	}

	public void keyPressedLeft(){
		if (!pressedLeft){
			xMove = -1;
			pressedLeft = true;
			networkStartup.keyPressed(xMove, horVert,xCoordinate);
		}
	}

	public void keyReleasedUp(){
		if (pressedUp  && pressedDown){
			pressedUp = false;
		}
		else if(pressedUp){
			yMove = 0;
			pressedUp = false;
			networkStartup.keyReleased(horVert, yMove,yCoordinate);
		}
		if(pressedDown){
			yMove = 1;
			networkStartup.keyReleased(horVert, yMove,yCoordinate);
		}
	}

	public void keyReleasedDown(){
		if (pressedDown && pressedUp){
			pressedDown = false;
		}
		else if(pressedDown){
			yMove = 0;
			pressedDown = false;
			networkStartup.keyReleased(horVert,yMove,yCoordinate);
		}
		if(pressedUp){
			yMove = -1;
			networkStartup.keyReleased(horVert,yMove,yCoordinate);
		}
	}

	public void keyReleasedRight(){
		if(pressedRight && pressedLeft){
			pressedRight = false;
		}
		else if(pressedRight){
			xMove = 0;
			pressedRight = false;
			networkStartup.keyReleased(xMove, horVert,xCoordinate);
		}
		if(pressedLeft){
			xMove = -1;
			networkStartup.keyReleased(xMove, horVert,xCoordinate);
		}
	}

	public void keyReleasedLeft(){

		if (pressedLeft && pressedRight){
			pressedLeft = false;
		}
		else if(pressedLeft){
			xMove = 0;
			pressedLeft = false;
			networkStartup.keyReleased(xMove, horVert,xCoordinate);
		}
		if(pressedRight){
			xMove = 1;
			networkStartup.keyReleased(xMove, horVert,xCoordinate);
		}
	}

	public void readMove(ChatObject servObj, int index){

		if(!servObj.getArrayList().isEmpty()){
			//Makes it so only a user with the specific username can move the circle
			if(servObj.getUsername().equals(servObj.getArrayList().get(index))){  
				realXMove = servObj.getXMove();
				realYMove = servObj.getYMove();
			}
		}
		//System.out.println(servObj.getRefreshCoordinates());
		if(servObj.getRefreshCoordinates()){
			xCoordinate = servObj.getXCoordinate();
			yCoordinate = servObj.getYCoordinate();
		}
	}

	public void move(){
		xCoordinate += realXMove;
		yCoordinate += realYMove;
	}

	public void paintComponent(Graphics g){
		g.setColor(Color.ORANGE);
		g.fillOval(0, 0, 100, 100);
		this.setBounds(xCoordinate, yCoordinate, 160, 120);
	}
	
}