import java.awt.*;
import java.awt.image.BufferedImage;

public class Food
{
	protected BufferedImage icon;
	private int nutrition;
	private int scoreValue;
	private Point position;
	protected Color color;
	protected Food(int nut, int score, Point position){this.nutrition = nut; this.scoreValue = score; this.position = position; icon = null; color = null;};
	public int getNutrition(){return nutrition;};
	public Point getPosition(){return position;}
	public int getScore(){return scoreValue;}
	public BufferedImage getIcon() { return icon; }
	public Color getColor() { return color; }
}
