import javax.swing.JFrame;

class GameWindow extends JFrame{
	
	private final GameVisual visual;
	public final static int HEIGHT = 640;
	public final static int WIDTH = 640;
	
	public GameWindow() {
		setResizable(true);
		visual = new GameVisual(WIDTH, HEIGHT);
		setContentPane(visual);
	}
	
	public static void main(String[]args){
		GameWindow window = new GameWindow();
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.visual.start();
	}
}
