import java.util.ArrayList;
import java.util.List;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;

public class MinVertexColour <V, E extends EdgeInterface<V>>{
	
	private GraphInterface<V, E> graph;
	
	public MinVertexColour(GraphInterface<V, E> graph) {
		super();
		this.graph = graph;
	}
	
	public int getNumber(){
		List<List<V>> colours = colours(this.graph);
		int stop=0;
		for(int i=0; i<colours.size(); i++){
			if(colours.get(i).isEmpty()){
				stop = i;
				break;
			}
		}
		List<List<V>> rems = new ArrayList<List<V>>();
		for(int j=stop; j<colours.size(); j++){
			rems.add(colours.get(j));
		}
		colours.removeAll(rems);
		int num = colours.size();
		return num;
	}
	
	public List<List<V>> colours(GraphInterface<V, E> graph){
		List<List<V>> colours = new ArrayList<List<V>>();
		List<V> vertices = new ArrayList<>(graph.getVertices());
		List<V> coloured = new ArrayList<>();
		List<V> Red = new ArrayList<>();
		List<V> Blue = new ArrayList<>();
		List<V> Green = new ArrayList<>();
		List<V> Yellow = new ArrayList<>();
		List<V> Orange = new ArrayList<>();
		List<V> Purple = new ArrayList<>();
		List<V> Pink = new ArrayList<>();
		List<V> Red1 = new ArrayList<>();
		List<V> Blue1 = new ArrayList<>();
		List<V> Green1 = new ArrayList<>();
		List<V> Yellow1 = new ArrayList<>();
		List<V> Orange1 = new ArrayList<>();
		List<V> Purple1 = new ArrayList<>();
		List<V> Pink1 = new ArrayList<>();
		
		colours.add(Red);
		colours.add(Blue);
		colours.add(Green);
		colours.add(Yellow);
		colours.add(Orange);
		colours.add(Purple);
		colours.add(Pink);
		colours.add(Red1);
		colours.add(Blue1);
		colours.add(Green1);
		colours.add(Yellow1);
		colours.add(Orange1);
		colours.add(Purple1);
		colours.add(Pink1);
		
		for(V v : vertices){
			for(List<V> Colour : colours){
				if(!coloured.contains(v)){
					if(inColour(v, Colour)){
						setColour(v,Colour);
						coloured.add(v);
					}
				}
			}

		}
		return colours;
	}
	
	public List<V> setColour(V v, List<V> colourSet){
		if(inColour(v,colourSet)){
			colourSet.add(v);
		}
		return colourSet;
	}
	
	public boolean inColour(V v, List<V> coloured){
		for(V u : coloured){
			if(this.graph.containsEdge(u,v)){
				return false;
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
