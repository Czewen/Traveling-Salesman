package jp.co.worksap.global;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class Vertex {
	boolean visited = false;
	int x;
	int y;
	
	ArrayList<Edge> neighbours = null;
	
	public Vertex(int x, int y){
		this.x = x;
		this.y = y;
		neighbours = new ArrayList<Edge>();
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public ArrayList<Edge> findNeighbours(){
		return neighbours;
	}
	
	public Edge addNeighbour(Edge e){
		boolean duplicate = neighbours.contains(e);
		if(duplicate == false)
			neighbours.add(e);
		if(duplicate == true)
			return null;
		else
			return e;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null)
			return false;
		if(getClass()!=obj.getClass())
			return false;
		final Vertex v = (Vertex) obj;
		
		if(v.getX()!=this.x){
			return false;
		}
		if(v.getY()!=this.y){
			return false;
		}
		return true;
	}
	
	
	
	public Edge searchForEdge(Vertex v){
		Iterator<Edge> iterate = neighbours.iterator();
		while(iterate.hasNext()){
			Edge e = iterate.next();
			if(e.getConnectedVertex(v)!=null)
				return e;
		}
		return null;
	}
}
