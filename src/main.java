import javax.swing.JFrame;
import java.awt.*;

class GameWindow extends JFrame{
	
	private final GameVisual visual;
	public final static int WIDTH = 640;
	//extra 20 allocated to provide space for score and notifications
	public final static int EXTRA_HEIGHT = 20;
	public final static int HEIGHT = 640;
	
	public GameWindow() {
		setResizable(false);
		setTitle("Homemade Snake");
		visual = new GameVisual(WIDTH, HEIGHT, EXTRA_HEIGHT);
		setContentPane(visual);
	}
	
	public static void main(String[]args){
		GameWindow window = new GameWindow();
		
		//to offset the usable window size to WIDTH and HEIGHT values add Insets to the window size
		window.pack();
		Insets insets = window.getInsets();
		window.setSize(WIDTH + insets.left + insets.right, HEIGHT + EXTRA_HEIGHT + insets.top + insets.bottom);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.visual.start();
	}
}
