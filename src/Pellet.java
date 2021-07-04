import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Pellet extends Food
{
	private final String PELLET_ICON = "pellet.jpg";
	
	public Pellet(Point position)
	{super(1, 1, position);   icon = null; color = Color.ORANGE;
		try {
			icon = ImageIO.read(new File(PELLET_ICON));
		} catch (IOException e) {
		}
	}
}