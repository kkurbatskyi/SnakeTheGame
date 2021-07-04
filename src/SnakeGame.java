import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame
{
	private Map map;
	private Snake snake;
	private ArrayList<Food> food;
	private int score;
	
	//the tick of the game
	private Timer timer;
	private final int TICK = 1000 / 6;

	public SnakeGame(){
		map = new Map();
		snake = new Snake();
		food = new ArrayList<>();
		spawnFood();
		score = 0;
		
		timer = new Timer(TICK, (a) -> this.play());
	}
	
	private void play(){
		snake.move();
		//if the snake is dead - lose
		if(snake.speed() == 0)lose();
		//if there is no more room left on the map - win
		if(snake.length() == map.getScale().x * map.getScale().y)win();
		//check if snake moved over any food
		for(Food f : food) {
			if (snake.head().getPosition().x == f.getPosition().x && snake.head().getPosition().y == f.getPosition().y){
				snake.consume(f);
				score += f.getScore();
				food.remove(f);
				spawnFood();
			}
		}
		fillMap();
	}
	
	private void fillMap()
	{
		map.reset();
		for(Cell c : snake.body()){
			map.setCellAt(c.getPosition(), c);
		}
		for(Food c : food){
			map.setCellAt(c.getPosition(), new Cell(c.getPosition(), c.getColor(), c.getIcon()));
		}
	}
	
	private void spawnFood()
	{
		Random random = new Random();
		boolean found = false;
		while(!found){
			int x = Math.abs(random.nextInt() % map.getScale().x);
			int y = Math.abs(random.nextInt() % map.getScale().y);
			if(map.getCellAt(x, y).isFree()){found = true; food.add(selectFood(x, y));}
		}
	}
	
	private Food selectFood(int x, int y)
	{
		Random rand = new Random();
		int luck = Math.abs(rand.nextInt() % 100);
		System.out.println(luck);
		if(luck < 10)return new Superfood(new Point(x, y));
		else return new Pellet(new Point(x, y));
	}
	
	public void win(){
		System.out.println("You won! Score:" + score);
		timer.stop();
	}
	public void lose(){
		System.out.println("You lost! Score:" + score);
		timer.stop();
	}
	
	public Map map(){
		return map;
	}
	public Snake snake(){
		return snake;
	}
	
	public void start()
	{
		timer.start();
	}
}
