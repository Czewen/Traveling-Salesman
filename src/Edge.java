package jp.co.worksap.global;

public class Edge {
   
	Vertex v1;
	Vertex v2;
	int distance;
	
	public Edge(int distance, Vertex v1, Vertex v2){
		this.v1 = v1;
		this.v2 = v2;
		this.distance = distance;
	}
	
	public int getDistance(){
		return distance;
	}
	
	public Vertex getV1(){
		return v1;
	}
	
	public Vertex getConnectedVertex(Vertex v){
		if(v.equals(v1))
			return v2;
		if(v.equals(v2))
			return v1;
		return null;
	}
	
	public Vertex getV2(){
		return v2;
	}
	
	public Vertex getVertexByCoordinates(int x, int y){
		if(v1.getX()==x && v1.getY()==y)
			return v1;
		
		else if(v2.getX()==x && v2.getY()==y)
			return v2;
		
		return null;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}
		if (getClass() != obj.getClass()) {
		    return false;
		}
		final Edge e = (Edge)obj;
		if(e.getV1().equals(v1) && e.getV2().equals(v2) && e.getDistance()==distance)
			return true;
		else if(e.getV2().equals(v1) && e.getV1().equals(v2) && e.getDistance()==distance)
			return true;
		return false;
	}
}
