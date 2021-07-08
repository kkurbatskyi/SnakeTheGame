import javax.imageio.ImageIO;
import java.awt.Point;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Snake
{
	private final int DEFAULT_LENGTH = 2;
	//private final Point[] START_POSITION = {new Point(0, 0), new Point(0, 1)};
	private final Color COLOR = Color.GREEN;
	private final String HEAD_ICON = "/head.png";
	private final String BODY_ICON = "/body.png";
	
	private int length; //initial 2
	private ArrayList<Cell> body;
	private Point direction;
	private BufferedImage head_icon, body_icon;
	
	//Do I need you?
	private int speed;
	
	public Snake(){
		length = DEFAULT_LENGTH;
		speed = 1;
		direction = new Point(0, 0);
		body = new ArrayList<Cell>();
		try{
		head_icon = ImageIO.read(getClass().getResourceAsStream(HEAD_ICON));
		body_icon = ImageIO.read(getClass().getResourceAsStream(BODY_ICON));
		} catch (IOException e) { head_icon = null; body_icon = null; }
	
		body.add(new Cell(new Point(0, 1), COLOR, head_icon));
		body.add(new Cell(new Point(0, 0), COLOR, body_icon));
	};
	
	public void die(){
		this.direction = new Point(0, 0);
		this.speed = 0;
	};
	
	public void consume(Food food){
		for(int i = food.getNutrition(); i > 0; i--){
			this.length++;
			body.add(new Cell(body.get(body.size() - 1).getPosition(), COLOR, body_icon));
		}
	};
	
	public void move(){
		//if the snake is not moving, dont do the moving actions
		if(direction.x == direction.y && direction.y == 0)return;
		for(int i = length() - 1; i > 0; i--){
			body.get(i).setPosition(body.get(i - 1).getPosition());
		}
		try {
			head().move(direction);
		}
		//bashed into the walls
		catch (IndexOutOfBoundsException e){
			die();
		}
		//see if the snake had ate itself
		for(Cell c : body){
			if (c != head() && (c.getPosition().x == head().getPosition().x && c.getPosition().y == head().getPosition().y))die();
		}
	};
	
	public Cell head(){
		return body.get(0);
	}
	
	//GETTERS
	public int length()
	{
		return length;
	}
	
	public ArrayList<Cell> body()
	{
		return body;
	}
	
	public Point direction()
	{
		return direction;
	}
	
	public int speed()
	{
		return speed;
	}
	
	//SETTERS
	public void setLength(int length)
	{
		this.length = length;
	}
	
	public void setBody(ArrayList<Cell> body)
	{
		this.body = body;
	}
	
	public void setDirection(Point direction)
	{
		this.direction = direction;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
}
