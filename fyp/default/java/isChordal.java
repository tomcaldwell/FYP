import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.gt4j.annas.graph.DefaultEdge;
import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;

public class isChordal<V, E extends EdgeInterface<V>> {
	
	private GraphInterface<V, E> graph;
	
	public isChordal(GraphInterface<V, E> graph) {
		super();
		this.graph = graph;
	}
	
	public boolean isChordalYes(GraphInterface<V, E> graph){
		List<V> vertices = new ArrayList<>(graph.getVertices());
		HashSet<V> visited = new HashSet<>();
		Collection<V> C = new ArrayList<>();
		if(isClique(vertices)){
			return true;
		}
		for(V v : vertices){
			List<V> neighbours = allNeighbours(graph, v);
			if(!isClique(neighbours)){
				for(V m : neighbours){
					for(V n : neighbours){
						if(!m.equals(n)){
							if(!graph.containsEdge(m, n)){
								C.clear();
								C.add(v);
								if(!isCutset(vertices, C)){
									List<V> nibs = new ArrayList<>(neighbours);
									nibs.remove(n);
									nibs.remove(m);
									for(V x : nibs){
										C.add(x);
										if(isCutset(vertices, C)){
											return true;
										}
										C.remove(x);
									}
								}
							}
						}
					}
				}
			}else{
				return true;
			}
		}
		return false;
	}
	
	public boolean isCutset(List<V> order, Collection<V> C) {
		
		List<V> out = new ArrayList<>(order);
		out.removeAll(C);

		boolean iscutset = false;
		
		HashSet<V> visited = new HashSet<>();

		for (int i=0; i<out.size(); i++){
			for(int j=i+1; j<out.size(); j++){
				visited.clear();
				if(out.get(i)!=out.get(j) && !isPath(visited,out.get(j),out.get(i),this.graph,out)){
					iscutset=true;
					return iscutset;
				}
			}
		}
		return iscutset;
	}
	
	public boolean isPath(HashSet<V> visited, V v, V w, GraphInterface<V, E> graph, List<V> order) {
		if(order.indexOf(v)<order.indexOf(w)){
			V l = v;
			v=w;
			w=l;
		}
		
		List<V> allVerts = new ArrayList<>(graph.getVertices());
		allVerts.removeAll(order);
		visited.addAll(allVerts);
		
		if(visited.containsAll(graph.getVertices())){
			return false;
		}
		boolean ispath = false;
		visited.add(v);
		visited.add(w);
		List<V> vNeighbours = new ArrayList<>(allNeighbours(graph, v));
		List<V> wNeighbours = new ArrayList<>(allNeighbours(graph, w));
		
		if(graph.containsEdge(w, v) || v.equals(w)){
			ispath = true;
			return true;
		}
		
		vNeighbours.removeAll(visited);
		wNeighbours.removeAll(visited);
		
		if(vNeighbours.isEmpty() || wNeighbours.isEmpty()){
			ispath=false;
			return ispath;
		}
		List<V> visit = new ArrayList<>(visited);
		
		for(V g : vNeighbours){
			for(V h : wNeighbours){
				if(h.equals(g) || graph.containsEdge(h,g)){
					ispath=true;
					return ispath;
				}
			}
		}
		
		
		for(V k : vNeighbours){
			for(int r=0; r<visit.size()-1; r++){
				for(int s=r+1; s<visit.size(); s++){
					if(order.indexOf(k)>order.indexOf(visit.get(r)) && order.indexOf(k)>order.indexOf(visit.get(s)) && graph.containsEdge(k,visit.get(r)) && graph.containsEdge(k,visit.get(s))){
						visited.add(k);
						break;
					}break;
				}break;
			}
		}
		
		/*for(V l : wNeighbours){
			for(int r=0; r<visit.size()-1; r++){
				for(int s=r+1; s<visit.size(); s++){
					if(graph.containsEdge(l,visit.get(r)) && graph.containsEdge(l,visit.get(s))){
						visited.add(l);
					}
				}
			}
		}*/
		
		vNeighbours.removeAll(visited);
		wNeighbours.removeAll(visited);
		
		if(vNeighbours.isEmpty() || wNeighbours.isEmpty()){
			ispath=false;
			return ispath;
		}
		
		
		for(V n : vNeighbours){
			for(V m : wNeighbours){
				if(isPath(visited, n, m, graph, order)){
					
					ispath=true;
					return ispath;
				}
				else{
					
					visit.add(n);
					HashSet<V> vis = new HashSet<>(visit);
					if(isPath(vis, n, m, graph, order)){
						ispath=true;
						return ispath;
					}
				}
			}
		}
		
		
		return ispath;
		
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
