package jp.co.worksap.global;

public class Cell {

	boolean processed=false;
	
	char element;
	int distancevalue;
	int x;
	int y;
	
	public Cell(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setValue(char value){
		element = value;
	}
	
	public void setProcessed(boolean processed){
		this.processed = processed;
	}
	
	public char getValue(){
		return element;
	}
	
	public void setDistancevalue(int value){
		distancevalue = value;
	}
	public int getDistancevalue(){
		return distancevalue;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
