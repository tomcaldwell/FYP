package org.gt4j.annas.graph.generate;

import java.util.Map;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.VertexFactory;


/**
 * See Graph classes for description of class <a
 * href="http://www.graphclasses.org/smallgraphs.html#families_XF">Graph
 * classes</a>
 * 
 * @author Sam
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class XF_10<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, EdgeInterface<V>> {

	private int n;

	/**
	 * Constructs a XF_10 graph generator
	 * 
	 * @param n
	 *            See class documentation
	 */
	public XF_10(int n) {
		super();
		if (n >= 2) {
			this.n = n;
		} else {
			throw new IllegalArgumentException("n must be >= 2");
		}
	}

	@Override
	public void generateGraph(GraphInterface<V, EdgeInterface<V>> target,
			VertexFactory<V> factory, Map<String, ?> Additionalinfo) {

		V t = factory.createVertex();
		V b = factory.createVertex();

		target.addVertex(t);
		target.addVertex(b);

		for (int i = 0; i < n; i++) {
			V ct = factory.createVertex();
			V cb = factory.createVertex();

			target.addVertex(ct);
			target.addVertex(cb);

			target.addEdge(ct, cb);
			target.addEdge(t, ct);
			target.addEdge(b, cb);

			t = ct;
			b = cb;
		}
		V u = factory.createVertex();
		V v = factory.createVertex();

		target.addVertex(u);
		target.addVertex(v);

		target.addEdge(t, u);
		target.addEdge(b, v);
	}

}
