import java.awt.Point;
import java.util.ArrayList;

public class Map
{
	private Point scale; //scale of the map in squares. default - 20/20 squares
	private ArrayList<ArrayList<Cell>> table;
	
	//Do I need it?
	//private ArrayList<Cell> cells;
	
	public Map(){
		scale = new Point(20, 20);
		table = new ArrayList<>();
		for(int i = 0; i < scale.x; i++){
			table.add(new ArrayList<Cell>());
			for(int j = 0; j < scale.y; j++){
				table.get(i).add(new Cell(i, j));
			}
		}
	}
	
	public Map(int x, int y){
		scale = new Point(x, y);
		table = new ArrayList<>();
		for(int i = 0; i < scale.x; i++){
			table.add(new ArrayList<Cell>());
			for(int j = 0; j < scale.y; j++){
				table.get(i).add(new Cell(i, j));
			}
		}
	}
	public Point getScale(){
		return scale;
	};
	
	//GETTERS
	public Cell getCellAt(Point p){
		return table.get((int)p.getX()).get((int)p.getY());
	};
	public Cell getCellAt(int x, int y){
		return table.get(x).get(y);
	};
	//SETTERS
	public void setCellAt(Point p, Cell cell)throws IndexOutOfBoundsException{
		if(p.x > scale.x || p.y > scale.y) throw new IndexOutOfBoundsException();
		table.get((int)p.getX()).set((int)p.getY(), cell);
	};
	public void setCellAt(int x, int y, Cell cell){
		table.get(x).set(y, cell);
	};
	
	public ArrayList<ArrayList<Cell>> returnTable(){
		return table;
	}
	
	public void reset(){
		for(int i = 0; i < scale.x; i++){
			for(int j = 0; j < scale.y; j++){
				table.get(i).set(j, new Cell(i, j));
			}
		}
	}
}
