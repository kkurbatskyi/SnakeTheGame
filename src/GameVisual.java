import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class GameVisual extends JComponent
{
	private SnakeGame game;
	private int width;
	private int height;
	private Point cellSize;
	private Timer timer;
	
	private final int TICK = 1000/60;
	
	public GameVisual(int width, int height)
	{
		setFocusable(true);
		
		game = new SnakeGame();
		this.width = width;
		this.height = height;
		this.cellSize = new Point(width / game.map().getScale().x, height / game.map().getScale().y);
		
		addKeyListener(new snakeMoveListener());
		
		timer = new Timer(TICK, (a) -> {repaint();});
	}
	
	public void start(){
		game.start();
		timer.start();
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintComponent(g2);
	}
	
	public void paintComponent(Graphics2D g2){
		int currentX = 1;
		int currentY = 1;
		//g2.clearRect(0, 0, this.getWidth(), this.getHeight());
		for(ArrayList<Cell> list : game.map().returnTable()){
			currentX = 1;
			for(Cell c : list){
				if(c.getColor() != null){
					g2.setColor(c.getColor());
					g2.fillRect(currentX, currentY, cellSize.x, cellSize.y);
				}
				else g2.drawImage(c.getSprite(),currentX, currentY, cellSize.x, cellSize.y, null);
				currentX += cellSize.x + 1;
			}
			currentY += cellSize.y + 1;
		}
	}
	
	
	private class snakeMoveListener extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP -> game.snake().setDirection(new Point(-1, 0));
				case KeyEvent.VK_RIGHT -> game.snake().setDirection(new Point(0, 1));
				case KeyEvent.VK_DOWN -> game.snake().setDirection(new Point(1, 0));
				case KeyEvent.VK_LEFT -> game.snake().setDirection(new Point(0, -1));
			}
		}
	}
	
}