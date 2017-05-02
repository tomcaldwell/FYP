package org.gt4j.annas.graph.util;

import java.util.Comparator;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.WeightedEdgeInterface;

public abstract class AbstractMinimumSpanningTree<V, E extends EdgeInterface<V>>
		implements MinimumSpanningTreeInterface<V, E> {

	protected static final double DEFAULT_EDGE_WEIGHT = 1d;

	@Override
	public double getWeight() {
		GraphInterface<?, E> graph = null;
		try {
			graph = this.call();
		} catch (Exception e) {

			e.printStackTrace();
		}
		double d = 0;
		for (E edge : graph.getEdges()) {
			if (edge instanceof WeightedEdgeInterface<?>) {
				d += ((WeightedEdgeInterface<?>) edge).getWeight();
			} else {
				d += 1;
			}
		}

		return d;
	}

	class DoubleFieldComparator implements Comparator<E> {

		@Override
		public int compare(E arg0, E arg1) {
			Double d = DEFAULT_EDGE_WEIGHT;
			Double e = DEFAULT_EDGE_WEIGHT;
			if (arg0 instanceof WeightedEdgeInterface<?>) {
				d = ((WeightedEdgeInterface<?>) arg0).getWeight();
			}

			if (arg1 instanceof WeightedEdgeInterface<?>) {
				e = ((WeightedEdgeInterface<?>) arg1).getWeight();
			}
			return d.compareTo(e);
		}
	}
}
