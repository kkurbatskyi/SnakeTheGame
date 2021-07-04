import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;

public class Snake
{
	private final int DEFAULT_LENGTH = 2;
	//private final Point[] START_POSITION = {new Point(0, 0), new Point(0, 1)};
	private final Color COLOR = Color.GREEN;
	
	private int length; //initial 2
	private ArrayList<Cell> body;
	private Point direction;
	
	//Do I need you?
	private int speed;
	
	public Snake(){
		length = DEFAULT_LENGTH;
		speed = 1;
		direction = new Point(0, 0);
		body = new ArrayList<Cell>();
		/*for(int i = 0; i < length; i++){
			body.add(new Cell(START_POSITION[i], COLOR));
		}*/
		body.add(new Cell(new Point(0, 1), COLOR));
		body.add(new Cell(new Point(0, 0), COLOR));
	};
	
	public void die(){
		this.direction = new Point(0, 0);
		this.speed = 0;
	};
	
	public void consume(Food food){
		for(int i = food.getNutrition(); i > 0; i--){
			this.length++;
			body.add(new Cell(body.get(body.size() - 1).getPosition(), COLOR));
		}
	};
	
	public void move(){
		if(direction.x == direction.y && direction.y == 0)return;
		for(int i = length() - 1; i > 0; i--){
			body.get(i).setPosition(body.get(i - 1).getPosition());
		}
		try {
			head().move(direction);
		}
		catch (IndexOutOfBoundsException e){
			die();
		}
		for(Cell c : body){
			if (c != head() && c.getPosition() == head().getPosition())die();
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
