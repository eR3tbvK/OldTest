import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;


public class AddKeyListener extends JPanel {
	private static final long serialVersionUID = 1L;
	public PlayerMob player;

	public AddKeyListener() {
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_LEFT)	player.keyReleasedLeft();
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.keyReleasedRight();
				if(e.getKeyCode() == KeyEvent.VK_UP)	player.keyReleasedUp();
				if(e.getKeyCode() == KeyEvent.VK_DOWN)	player.keyReleasedDown();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_UP)	player.keyPressedUp();
				if(e.getKeyCode() == KeyEvent.VK_DOWN)	player.keyPressedDown();
				if(e.getKeyCode() == KeyEvent.VK_LEFT)	player.keyPressedLeft();
				if(e.getKeyCode() == KeyEvent.VK_RIGHT)	player.keyPressedRight();
			}
		});
		setFocusable(true);
		
	}
	
	public void setPlayer(PlayerMob usr){
		player = usr;
	}
		
}
