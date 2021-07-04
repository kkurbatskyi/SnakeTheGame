import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Cell
{
	private Color color;
	private Point position;
	private BufferedImage sprite;
	
	public Cell(Point p, Color c)
	{
		position = p;
		color = c;
		sprite = null;
	}
	public Cell(Point p, BufferedImage icon){
		position = p;
		color = null;
		sprite = icon;
	}
	public Cell(Point p, Color c, BufferedImage icon){
		position = p;
		color = c;
		sprite = icon;
	}
	public Cell(Point p){
		position = p;
		color = Color.WHITE;
		sprite = null;
	}
	
	public Cell(int x, int y){
		position = new Point(x, y);
		color = Color.WHITE;
		sprite = null;
	}
	
	public void move(Point direction)throws IndexOutOfBoundsException{
		Point newPosition = new Point(position.x + direction.x, position.y + direction.y);
		if(newPosition.x < 0 || newPosition.y < 0)throw new IndexOutOfBoundsException();
		else position = newPosition;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Point getPosition()
	{
		return position;
	}
	
	public void setPosition(Point position)
	{
		this.position = position;
	}
	
	public BufferedImage getSprite()
	{
		return sprite;
	}
	
	public void setSprite(BufferedImage sprite)
	{
		this.sprite = sprite;
	}
	
	public boolean isFree()
	{
		return (color == Color.WHITE && sprite == null);
	}
}
