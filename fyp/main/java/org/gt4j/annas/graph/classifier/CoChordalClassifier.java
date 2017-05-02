package org.gt4j.annas.graph.classifier;

import java.util.ArrayList;
import java.util.List;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.util.traverse.LexBFSComplement;

/**
 * Classifies if a graph is co-chordal
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class CoChordalClassifier<V, E extends EdgeInterface<V>> implements Classifier<V, E> {

	private boolean check(GraphInterface<V, E> graph) {
		if (graph.getOrder() < 3) {
			return true;
		}
		LexBFSComplement<V> lex = new LexBFSComplement<V>(graph);

		List<V> out = lex.getOrder();

		for (int i = 1; i < out.size(); i++) {
			V nearest = getNearestNeighbour(graph, out, out.get(i));
			if (nearest != null) {
				
				List<V> v_n = this.neighboursOf(graph, out, out.get(i));
				v_n.remove(nearest);
				List<V> w_n = this.neighboursOf(graph, out, nearest);

				if (!w_n.containsAll(v_n) && !v_n.isEmpty() && !w_n.isEmpty()) {
				
					return false;
				}
			}
		}

		return true;
	}

	private List<V> neighboursOf(GraphInterface<V, E> graph, List<V> out, V v) {
		ArrayList<V> l = new ArrayList<V>();
		int i = out.indexOf(v);

		for (int j = i - 1; j >= 0; j--) {
			V ret = out.get(j);
			if (graph.getEdges(v, ret).size() == 0) {
				l.add(ret);
			}
		}
		return l;
	}

	private V getNearestNeighbour(GraphInterface<V, E> graph, List<V> out, V n) {
		int i = out.indexOf(n);

		for (int j = i-1; j >= 0; j--) {
			V ret = out.get(j);
			if (graph.getEdges(n, ret).size() == 0) {
				return ret;
			}
		}
		return null;
	}

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		return check(graph);
	}

}
