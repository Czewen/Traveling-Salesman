package jp.co.worksap.global;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Orienteering {
	public static void main(String[] args){
		
		String[] input = {"6 6", 	"######" , 
									"#S.@.#" , 
									"#@####" , 
									"#...G#" ,
									"#.@..#" ,
									"######" };
		
		
		Graph g = createGraph(input);
		
		Iterable<Vertex> iterable =g.getVertices();
		Iterator<Vertex> iterate = iterable.iterator();
		Vertex curr = iterate.next();
		System.out.println(g.getNumberOfEdges());
		System.out.println(g.getNumberOfVertices());
	
		System.out.println(run(input));
		
	}
	
	public static int run(String[] input){
		String[] hw = input[0].split(" ");
		int width = Integer.parseInt(hw[0]);
		int height = Integer.parseInt(hw[1]);
		if(width>100 || width<1)
			return -1;
		if(height>100 || height<1)
			return -1;
		Graph g = createGraph(input);
		if(g.getNumberOfVertices()-2>18)
			return -1;
		return greedyshort(g, g.startx, g.starty, g.goalx, g.goaly);
	}
	
	
	public static Graph createGraph(String[] input){
		
		String[] hw = input[0].split(" ");
		int width = Integer.parseInt(hw[0]);
		int height = Integer.parseInt(hw[1]);
		Cell[][] cellMap = translateInput(width, height, input);
		Graph graph = new Graph();

		//find number of checkpoints
		for(int i=0; i<cellMap.length; i++){
			for(int j=0; j<cellMap[0].length; j++){
				
					
					if(Character.toLowerCase(cellMap[i][j].getValue())=='s')
						graph.addStart(new Vertex(i,j));
					else if(Character.toLowerCase(cellMap[i][j].getValue())=='g')
						graph.addGoal(new Vertex(i,j));
					else if(cellMap[i][j].getValue()=='@')
						graph.addVertex(new Vertex(i,j));
					
			}
		}
		Iterable<Vertex> vertices = graph.getVertices();
		Iterator<Vertex> iterate = vertices.iterator();
		while(iterate.hasNext()){
			Vertex current = iterate.next();
			Iterator<Vertex> iterate2 = vertices.iterator();
			while(iterate2.hasNext()){
				Vertex compare = iterate2.next();
				if(!compare.equals(current)){
					
					int distance = distance_twopoints(cellMap, current.getX(), current.getY(), compare.getX(), compare.getY());
					if(distance>0){	
						
						Edge e = new Edge(distance, current, compare);
						if(!graph.edges.contains(e)){
							current.addNeighbour(e);
							
							compare.addNeighbour(e);
							graph.addEdge(e);
						}
					}
				}
			}
		}
		return graph;
	}
	
	public static int distance_twopoints(Cell[][] cellMap, int x1, int y1, int x2, int y2){
		Queue<Cell> unprocessed = new LinkedList<Cell>();
		ArrayList<Cell> processed = new ArrayList<Cell>();
		cellMap[x1][y1].setDistancevalue(0);
		unprocessed.add(cellMap[x1][y1]);
		int final_distance = -1;
		int temp_x = x1; int temp_y = y1;
		
		while(!unprocessed.isEmpty()){
			Cell being_processed = unprocessed.remove();
			temp_x = being_processed.getX();
			temp_y = being_processed.getY();
			
			if(temp_x!=x2 || temp_y!=y2  ){
				
				int distance = being_processed.getDistancevalue();
				if(cellMap[temp_x+1][temp_y].getValue()!='#' 
						&& !processed.contains(cellMap[temp_x+1][temp_y])
						&& !unprocessed.contains(cellMap[temp_x+1][temp_y])){
					unprocessed.add(cellMap[temp_x+1][temp_y]);
					cellMap[temp_x+1][temp_y].setDistancevalue(distance+1);
				}
				if(cellMap[temp_x-1][temp_y].getValue()!='#' 
						&& !processed.contains(cellMap[temp_x-1][temp_y])
						&& !unprocessed.contains(cellMap[temp_x-1][temp_y])){
					unprocessed.add(cellMap[temp_x-1][temp_y]);
					cellMap[temp_x-1][temp_y].setDistancevalue(distance+1);
				}
				if(cellMap[temp_x][temp_y+1].getValue()!='#' 
						&& !processed.contains(cellMap[temp_x][temp_y+1])
						&& !unprocessed.contains(cellMap[temp_x][temp_y+1])){
					unprocessed.add(cellMap[temp_x][temp_y+1]);
					cellMap[temp_x][temp_y+1].setDistancevalue(distance+1);
				}
				if(cellMap[temp_x][temp_y-1].getValue()!='#' 
						&& !processed.contains(cellMap[temp_x][temp_y-1])
						&& !unprocessed.contains(cellMap[temp_x][temp_y-1])){
					unprocessed.add(cellMap[temp_x][temp_y-1]);
					cellMap[temp_x][temp_y-1].setDistancevalue(distance+1);
				}
			}
			processed.add(cellMap[temp_x][temp_y]);
			if(temp_x == x2 && temp_y == y2)
				final_distance = cellMap[temp_x][temp_y].getDistancevalue();
		}
		
		return final_distance;
	}
	
	public static Cell[][] translateInput(int width, int height, String[] input){
		Cell[][] cellMap = new Cell[height][width];
		//start translating from the second row
		int input_index=1;
		for(int i=0; i<cellMap.length; i++){
			for(int j=0; j<cellMap[0].length;j++){
				cellMap[i][j] = new Cell(i,j);
				cellMap[i][j].setValue(input[input_index].charAt(j));
			}
			input_index++;
		}
		return cellMap;
	}
	
	public static int greedyshort(Graph graph, int startx, int starty, int goalx, int goaly){
		int distance = 0;
		Vertex start = graph.searchForVertex(startx, starty);
		Vertex goal = graph.searchForVertex(goalx, goaly);
		Vertex curr = start;
		
		ArrayList<Vertex> visited = new ArrayList<Vertex>();
		int no_vertices = graph.getNumberOfVertices();
		
		//If there no checkpoints and the goal is reachable
		if(no_vertices==2 && goal.findNeighbours().size()!=0){
			Iterable<Edge> iterable = graph.getEdges();
			Iterator<Edge> iterate = iterable.iterator();
			Edge e = iterate.next();
			return e.getDistance();
		}
		
		if(goal.searchForEdge(start)!=null && goal.findNeighbours().size()==graph.getNumberOfVertices()-1){
			Edge min_edge;
			while(curr.getX()!=goalx || curr.getY()!=goaly){
				min_edge = new Edge((int)(Double.POSITIVE_INFINITY-500),start, goal);
				//Edge min_edge = curr.findNeighbours().get(0);
				//if(min_edge.getVertexByCoordinates(goalx, goaly)!=null || min_edge.getConnectedVertex(curr).visited==true)
					//min_edge = curr.findNeighbours().get(1);
				for(Edge e: curr.neighbours){
					if(visited.size()<no_vertices-2){
						//do not want edges that point directly to the goal point
						//because we haven't gone through all the checkpoints yet
						if(!e.getConnectedVertex(curr).equals(goal) && e.getConnectedVertex(curr).visited == false){
							if(min_edge.distance>e.distance){
								min_edge = e;
							}
						}		
					}
					//we're at the second last vertex so the only path left is to reach the goal
					else if(visited.size()==no_vertices-2){
						if(e.getConnectedVertex(curr).equals(goal)){
							min_edge =e;
						}
					}
						
				}
				
				distance+=min_edge.getDistance();
				System.out.println(min_edge.getConnectedVertex(curr).getX()+", "+min_edge.getConnectedVertex(curr).getY());
				System.out.println("distance: "+min_edge.getDistance());
				curr.visited = true;
				visited.add(curr);
				curr = min_edge.getConnectedVertex(curr);
				
				
			}
			Iterator<Vertex> iterate = visited.iterator();
			while(iterate.hasNext()){
				Vertex v = iterate.next();
				System.out.println("x: "+v.getX()+" y: "+v.getY());
			}
			return distance;
		}
		else{
			return -1;
		}
	}
}
