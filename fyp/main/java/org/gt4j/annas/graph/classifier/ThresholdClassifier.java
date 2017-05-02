package org.gt4j.annas.graph.classifier;

import java.util.Arrays;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.util.Utilities;

/**
 * Classifies if a graph is a threshold graph
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class ThresholdClassifier<V, E extends EdgeInterface<V>> implements
		Classifier<V, E> {

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		long[] ds = Utilities.getDegreeSequence(graph);
		Arrays.sort(ds);

		while (ds.length > 0) {
			if (ds[0] <= 0) {
				ds = Arrays.copyOfRange(ds, 1, ds.length);
				continue;
			}
			long n = ds.length;
			if (ds[ds.length - 1] != n - 1) {
				return false;
			} else {
				ds = Arrays.copyOfRange(ds, 0, ds.length - 1);
				this.decrementBy(ds, 1);

			}

		}
		return true;
	}

	private void decrementBy(long[] ds, long decrementBy) {
		for (int i = 0; i < ds.length; i++) {
			ds[i] = ds[i] - decrementBy;
		}
	}
}
