import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Superfood extends Food
{
	private final String SUPERFOOD_ICON = "strawberry.jpg";
	
	public Superfood(Point position)
	{super(3, 10, position); icon = null; color = Color.RED;
		try {
			icon = ImageIO.read(new File(SUPERFOOD_ICON));
		} catch (IOException e) {
		}}
}
