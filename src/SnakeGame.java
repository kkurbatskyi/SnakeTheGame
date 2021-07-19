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
	private boolean lost;
	private boolean won;
	
	
	//the tick of the game
	private Timer timer;
	public final int TICK = 1000 / 6; //1000/6 - default

	public SnakeGame(){
		map = new Map(20, 20);
		snake = new Snake();
		food = new ArrayList<>();
		spawnFood();
		score = 0;
		lost = false; won = false;
		
		timer = new Timer(TICK, (a) -> this.play());
	}
	
	//lose is checked before filling the map to prevent head from moving over the body
	//win is checked after filling the map to ensure eating of the last piece of food
	private void play(){
		snake.move();
		//make snake go through walls coming out from the other side
		//if the snake is dead - lose
		if(snake.speed() == 0){ lose(); return; }
		Point position = snake.head().getPosition();
		snake.head().setPosition(new Point((position.x + map.getScale().x) % map.getScale().x, (position.y + map.getScale().y) % map.getScale().y));
		
		//check if snake moved over any food
		for(int i = 0; i < food.size(); i++) {
			Food f = food.get(i);
			if (snake.head().getPosition().x == f.getPosition().x && snake.head().getPosition().y == f.getPosition().y){
				snake.consume(f);
				score += f.getScore();
				food.remove(f);
				//unless snake has already won, create new food
				spawnFood();
			}
		}
		fillMap();
		
		//no food left and the length of the snake is the area of the map - win
		if(snake.length() > map.getScale().x * map.getScale().y && food.isEmpty()){ win(); }
	}
	
	private boolean mapIsFull(){
		boolean full = true;
		for(int i = 0; i < map.getScale().x && full; i++){
			for(int j = 0; j < map.getScale().y; j++){
				if(map.getCellAt(i, j).isFree()){ full = false; break; }
			}
		}
		return full;
	}
	
	private void fillMap()
	{
		map.reset();
		for(Food c : food){
			map.setCellAt(c.getPosition(), new Cell(c.getPosition(), c.getColor(), c.getIcon()));
		}
		for(Cell c : snake.body()){
			map.setCellAt(c.getPosition(), c);
		}
	}
	
	private void spawnFood()
	{
		if(mapIsFull())return;
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
		if(luck < 10)return new Superfood(new Point(x, y));
		else return new Pellet(new Point(x, y));
	}
	
	public void win(){
		//draw the map one last time to "consume" the last piece of food on the map
		fillMap();
		won = true;
		timer.stop();
	}
	public void lose(){
		lost = true;
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
	
	public int score()
	{
		return score;
	}
	
	public boolean lost()
	{
		return lost;
	}
	
	public boolean won()
	{
		return won;
	}
	
}
