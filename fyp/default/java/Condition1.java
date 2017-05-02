import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.util.Utilities;

public class Condition1 <V, E extends EdgeInterface<V>> extends Utilities {

	private GraphInterface<V, E> graph;
	
	public Condition1(GraphInterface<V, E> graph) {
		super();
		this.graph = graph;
	}
	
	
	public boolean Satisfies1(GraphInterface<V, E> graph){
		List<V> Hole = new ArrayList<>(call(graph));
		List<V> Clique = new ArrayList<>(graph.getVertices());
		
		Clique.removeAll(Hole);
		
		if((isLongHole(Hole) || Hole.isEmpty()) && isClique(graph,Clique)){
			return true;
		}
		else{
			return false;
		}
		
	}	

	public HashSet<V> call(GraphInterface<V, E> graph) {
		List<V> VertexSet = new ArrayList<>(graph.getVertices());
				
		HashSet<V> Hole = new HashSet<>();
		HashSet<V> Clique = new HashSet<>(VertexSet);
		
		for(V v : VertexSet){
			for(V u : VertexSet){
				if(!v.equals(u)){
					if(!graph.containsEdge(v, u)){
						Hole.add(v);
						Hole.add(u);
					}
				}
			}
		}
		Clique.removeAll(Hole);
		return Hole;
	}
	
		
	public boolean isLongHole(List<V> hole) {
		
		if(hole.size()<5){
			return false;
		}
		List<V> Clique = new ArrayList<>(this.graph.getVertices());
		Clique.removeAll(hole);
		
		for(V v : hole){
			List<V> neighbours = new ArrayList<>(allNeighbours(this.graph, v));
			neighbours.removeAll(Clique);
			if(neighbours.size()!=2){
				return false;
			}
		}
		
		List<V> visited = new ArrayList<>();
		V v = hole.get(0);
		V endpoint = allNeighbours(this.graph, v).get(0);
		visited.add(endpoint);
		
		List<V> vis = checkVisited(visited,v,Clique);
		if(vis.containsAll(hole) && hole.containsAll(vis)){
			return true;
		}
		else{
			return false;
		}		
	}
	
	public List<V> checkVisited(List<V> visited, V vertex, List<V> Clique){
		visited.add(vertex);
		List<V> neighbours = allNeighbours(this.graph, vertex);
		neighbours.removeAll(Clique);
		for(V n : neighbours){
			if(!visited.contains(n)){
				checkVisited(visited, n, Clique);
			}
		}
		return visited;
	}
	
	public boolean isClique(GraphInterface<V, E> graph, List<V> C){
		
		if(C.size()==1 || C.size()==0){
			return true;
		}
		
		for(V v : C){
			for(V u : C){
				if(!v.equals(u)){
					if(!graph.containsEdge(v, u)){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public List<V> allNeighbours(GraphInterface<V, E> graph, V v){
		List<V> neighbours = new ArrayList<V>();
		List<V> out = new ArrayList<>(graph.getVertices());
		
		for (int j =out.size()-1; j >=0; j--) {
			V ret = out.get(j);
			if (!v.equals(ret) && graph.getEdges(v, ret).size() != 0 && !neighbours.contains(ret)) {
				neighbours.add(ret);
			}
		}
		return neighbours;
	}

}
