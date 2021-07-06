import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

class GameVisual extends JComponent
{
	private SnakeGame game;
	private int width;
	private int height;
	private Point cellSize;
	private Timer timer;
	
	private final int TICK = 1000/60;
	private final String BACKGROUND_ICON = "/grass.png";
	
	public GameVisual(int width, int height)
	{
		setFocusable(true);
		
		game = new SnakeGame();
		this.width = width;
		this.height = height;
		this.cellSize = new Point(height / game.map().getScale().x, width / game.map().getScale().y);
		System.out.println(cellSize);
		
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
		int currentX = 0;
		int currentY = 0;
		try {
			g2.drawImage(ImageIO.read(getClass().getResourceAsStream(BACKGROUND_ICON)), currentX, currentY, null);
		} catch (IOException e) {
			for(ArrayList<Cell> list : game.map().returnTable()){
				currentX = 0;
				for(Cell c : list){
					if(c.getColor() != null){
						g2.setColor(c.getColor());
						g2.fillRect(currentX, currentY, cellSize.x, cellSize.y);
					}
					else g2.drawImage(c.getSprite(),currentX, currentY, cellSize.x, cellSize.y, null);
					currentX += cellSize.x;
				}
				currentY += cellSize.y;
			}
		}
		
		currentX = 0;
		currentY = 0;
		for(ArrayList<Cell> list : game.map().returnTable()){
			currentX = 0;
			for(Cell c : list){
				//only draw a Cell if it is NOT empty
				if(c.getColor() != Color.WHITE) {
					if (c.getSprite() == null) {
						g2.setColor(c.getColor());
						g2.fillRect(currentX, currentY, cellSize.x, cellSize.y);
					} else g2.drawImage(c.getSprite(), currentX, currentY, cellSize.x, cellSize.y, null);
				}
				currentX += cellSize.x;
			}
			currentY += cellSize.y;
		}
	}
	
	
	private class snakeMoveListener extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP -> {
					Point newDir = new Point(-1, 0);
					//check if the snake is trying to go backwards
					if(!(game.snake().direction().x + newDir.x == 0 && game.snake().direction().y + newDir.y == 0))
						game.snake().setDirection(newDir);
				}
				case KeyEvent.VK_RIGHT ->{
						Point newDir = new Point(0, 1);
						//check if the snake is trying to go backwards
						if(!(game.snake().direction().x + newDir.x == 0 && game.snake().direction().y + newDir.y == 0))
							game.snake().setDirection(newDir);
				}
				case KeyEvent.VK_DOWN ->{
					Point newDir = new Point(1, 0);
					//check if the snake is trying to go backwards
					if(!(game.snake().direction().x + newDir.x == 0 && game.snake().direction().y + newDir.y == 0))
						game.snake().setDirection(newDir);
				}
				case KeyEvent.VK_LEFT ->{
					Point newDir = new Point(0, -1);
					//check if the snake is trying to go backwards
					if(!(game.snake().direction().x + newDir.x == 0 && game.snake().direction().y + newDir.y == 0))
						game.snake().setDirection(newDir);
				}
			}

		}
	}
	
}