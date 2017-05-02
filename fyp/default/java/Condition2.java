import java.util.Collection;
import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.util.Utilities;

public class Condition2 <V, E extends EdgeInterface<V>> extends Utilities {

	private GraphInterface<V, E> graph;

	public Condition2(final GraphInterface<V, E> graph) {
		this.graph = graph;

	}

	public boolean Satisfies2(GraphInterface<V, E> graph) {
		GraphInterface<V, E> comp = Utilities.getComplement(graph);
		Collection<Collection<V>> conn = Utilities.getConnectedComponents(comp);
		
		for (Collection<V> V : conn) {
			if(V.size()>2){
				return false;
			}
		}
		graph = Utilities.getComplement(comp);
		return true;
	}	
}