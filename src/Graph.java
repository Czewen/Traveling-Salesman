package jp.co.worksap.global;

import java.util.ArrayList;
import java.util.Iterator;

public class Graph {
	ArrayList<Vertex> vertices;
	ArrayList<Edge> edges;
	int startx; int starty;
	int goalx; int goaly;
	
	public Graph(){
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
	}
	
	public void addStart(Vertex v){
		vertices.add(v);
		startx = v.getX();
		starty = v.getY();
	}
	
	public void addGoal(Vertex v){
		vertices.add(v);
		goalx = v.getX();
		goaly = v.getY();
	}
	
	public void addVertex(Vertex v){
		vertices.add(v);
	}
	
	public void addEdge(Edge e){
		edges.add(e);
	}
	
	public int getNumberOfEdges(){
		return edges.size();
	}
	
	public int getNumberOfVertices(){
		return vertices.size();
	}
	
	public Vertex searchForVertex(int x, int y){
		Iterator<Vertex> iterate = vertices.iterator();
		while(iterate.hasNext()){
			Vertex v = iterate.next();
			if(v.getX()==x && v.getY()==y)
				return v;
		}
		return null;
	}
	
	public Iterable<Vertex> getVertices(){
		return vertices;
	}
	
	public Iterable<Edge> getEdges(){
		return edges;
	}
}
