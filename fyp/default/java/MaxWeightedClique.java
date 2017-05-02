import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.util.Utilities;

public class MaxWeightedClique <V, E extends EdgeInterface<V>> extends Utilities{
	
	private GraphInterface<V, E> graph;
	

	public MaxWeightedClique(GraphInterface<V, E> graph) {
		super();
		this.graph = graph;
	}
	
	public List<List<V>> findCliques(GraphInterface<V, E> graph){
		List<V> vertices = new ArrayList<>(graph.getVertices());
		
		List<List<V>> Cliques = new ArrayList<>();
		
		for(V v : vertices){
			List<V> Clique = new ArrayList<>();
			Cliques.add(getClique(vertices, v, Clique));
		}
		
		List<List<V>> dupes = new ArrayList<>();
		
		for(int i=0; i<Cliques.size()-1; i++){
			for(int j=i+1; j<Cliques.size(); j++){
				if(Cliques.get(i).containsAll(Cliques.get(j)) && Cliques.get(j).containsAll(Cliques.get(i))){
					dupes.add(Cliques.get(j));
				}
			}
		}
		Cliques.removeAll(dupes);
		return Cliques;
	}
	
	public List<V> getClique(List<V> vertices, V v, List<V> Clique){
		
		List<V> neighbours = allNeighbours(this.graph, v);
		List<V> nibs = new ArrayList<>(neighbours);
		nibs.removeAll(vertices);
		neighbours.removeAll(nibs);
		Clique.add(v);
		if(neighbours.isEmpty() && isClique(Clique)){
			return Clique;
		}
		
		
		for(V n : neighbours){
			if(inClique(neighbours, n, Clique)){
				return Clique;
			}
		}
		return null;
	}
	
	public boolean inClique(List<V> vertices, V v, List<V> Clique){
		
		List<V> neighbours = allNeighbours(this.graph, v);
		List<V> nibs = new ArrayList<>(neighbours);
		nibs.removeAll(vertices);
		neighbours.removeAll(nibs);
		Clique.add(v);
		if(neighbours.isEmpty() && isClique(Clique)){
			return true;
		}
		
		
		for(V n : neighbours){
			if(inClique(neighbours, n, Clique)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isClique(List<V> clique) {

		for (V v : clique) {
			for (V u : clique) {
				if (!v.equals(u) && !this.graph.containsEdge(v, u)) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	private List<V> allNeighbours(GraphInterface<V, E> graph, V v) {
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
