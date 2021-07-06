import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Superfood extends Food
{
	private final String SUPERFOOD_ICON = "/superfood.png";
	
	public Superfood(Point position)
	{super(3, 10, position); icon = null; color = Color.RED;
		try {
			icon = ImageIO.read(getClass().getResourceAsStream(SUPERFOOD_ICON));
		} catch (IOException e) {
		}}
}
