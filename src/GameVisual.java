import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

class GameVisual extends JComponent
{
	private SnakeGame game;
	private int width;
	private int height;
	private int infoBarHeight;
	private Point cellSize;
	private Timer timer;
	
	//repaint frequency
	private final int TICK = 1000/100;
	private final String BACKGROUND_ICON = "/grass.png";
	
	private long timeOfStart;
	//current time
	private long time;
	
	public GameVisual(int width, int height, int infoBarHeight)
	{
		setFocusable(true);
		
		game = new SnakeGame();
		this.width = width;
		this.height = height;
		this.infoBarHeight = infoBarHeight;
		this.cellSize = new Point(height / game.map().getScale().x, width / game.map().getScale().y);
		System.out.println(cellSize);
		
		addKeyListener(new snakeMoveListener());
		
		timer = new Timer(TICK, (a) -> {repaint();});
	}
	
	public void start(){
		game.start();
		timer.start();
		timeOfStart = ZonedDateTime.now().toInstant().toEpochMilli();
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintComponent(g2);
	}
	
	public void paintComponent(Graphics2D g2){
		
		drawInfoBar(g2);
		
		g2.translate(0, infoBarHeight);
		
		drawTheGame(g2);
		
		if(game.lost() || game.won())drawFinalScreen(g2);
	}
	
	private void drawFinalScreen(Graphics2D g2)
	{
		g2.translate((height / 2) - (height / 8),
			(width / 2) - (width / 6));
		g2.setColor(Color.BLACK);
		g2.clearRect(0,
			         0,
				  height - (height / 2) - (height / 8),
				  width - (width / 2) - (width / 6));
		g2.setColor(Color.WHITE);
		g2.drawRect(3,
				    3,
				height - (height / 2) - (height / 8),
				width - (width / 2) - (width / 6));;
		
		g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		g2.drawString(game.won()?"WINNER":"LOSER", 5, (height - (height / 2) - (height / 6)) / 4);
		g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		g2.drawString("Score: " + game.score(), 20, (height - (height / 2) - (height / 6)) / 2);
		g2.drawString("Press R to Restart", 5, (int)((height - (height / 2) - (height / 6)) * 0.875));
	}
	
	private void drawTheGame(Graphics2D g2)
	{
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
	
	private void drawInfoBar(Graphics2D g2)
	{
		g2.setBackground(Color.BLACK);
		g2.clearRect(0, 0, width, infoBarHeight);
		g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g2.setColor(Color.WHITE);
		
		//outputting the time of the game
		if(!(game.lost() || game.won())) {
			time = ZonedDateTime.now().toInstant().toEpochMilli() - timeOfStart;
		}
		g2.drawString((time / (1000 * 60)) + ":" + (((time / 1000) % 60) > 9 ? ((time / 1000) % 60): "0" + ((time / 1000) % 60)),
							width - 50, infoBarHeight - infoBarHeight / 10);
		g2.drawString("Score: " + game.score(),0,infoBarHeight - infoBarHeight / 10);
		g2.drawString("Press R for Restart", (int)(width * 0.35) ,infoBarHeight - infoBarHeight / 10);
		
	}
	
	
	private class snakeMoveListener extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP, KeyEvent.VK_W -> {
					Point newDir = new Point(-1, 0);
					//check if the snake is trying to go backwards
					if(!(game.snake().direction().x + newDir.x == 0 && game.snake().direction().y + newDir.y == 0))
						game.snake().setDirection(newDir);
				}
				case KeyEvent.VK_RIGHT, KeyEvent.VK_D ->{
						Point newDir = new Point(0, 1);
						//check if the snake is trying to go backwards
						if(!(game.snake().direction().x + newDir.x == 0 && game.snake().direction().y + newDir.y == 0))
							game.snake().setDirection(newDir);
				}
				case KeyEvent.VK_DOWN, KeyEvent.VK_S ->{
					Point newDir = new Point(1, 0);
					//check if the snake is trying to go backwards
					if(!(game.snake().direction().x + newDir.x == 0 && game.snake().direction().y + newDir.y == 0))
						game.snake().setDirection(newDir);
				}
				case KeyEvent.VK_LEFT, KeyEvent.VK_A ->{
					Point newDir = new Point(0, -1);
					//check if the snake is trying to go backwards
					if(!(game.snake().direction().x + newDir.x == 0 && game.snake().direction().y + newDir.y == 0))
						game.snake().setDirection(newDir);
				}
				case KeyEvent.VK_R ->{
					game = new SnakeGame();
					game.start();
					timeOfStart = ZonedDateTime.now().toInstant().toEpochMilli();
				}
			}

		}
	}
	
}