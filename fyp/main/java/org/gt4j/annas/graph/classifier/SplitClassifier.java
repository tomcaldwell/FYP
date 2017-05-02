package org.gt4j.annas.graph.classifier;

import java.util.Arrays;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.util.Utilities;

/**
 * Classifies split graphs using the degree sequence characteristic.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class SplitClassifier<V, E extends EdgeInterface<V>> implements
		Classifier<V, E> {

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		if (graph.getOrder() == 0) {
			return true;
		}
		long[] ds = Utilities.getDegreeSequence(graph);
		Arrays.sort(ds);
		reverse(ds);

		int m = 0;
		for (int i = 1; i <= ds.length; i++) {
			if (ds[i - 1] >= i) {
				m = i - 1;
			} else {
				break;
			}
		}
		long sum = sum(ds, 0, m + 1);
		long f = m * (m + 1);
		long l = sum(ds, m + 1, ds.length);
		return sum == f + l;
	}

	private long sum(long[] d, int start, int end) {
		long retval = 0;
		for (int i = start; i < end; i++) {
			retval += d[i];
		}
		return retval;
	}

	private void reverse(long[] d) {

		for (int i = 0; i < d.length / 2; i++) {
			long tmp = d[i];
			d[i] = d[d.length - i - 1];
			d[d.length - i - 1] = tmp;
		}
	}

}
