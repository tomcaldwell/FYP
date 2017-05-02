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
public class XF_9<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, EdgeInterface<V>> {

	private int n;

	/**
	 * Constructs a XF_9 graph generator
	 * 
	 * @param n
	 *            See class documentation
	 */
	public XF_9(int n) {
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

		V a = factory.createVertex();
		V b = factory.createVertex();
		V c = factory.createVertex();
		V d = factory.createVertex();

		target.addVertex(a);
		target.addVertex(b);
		target.addVertex(c);
		target.addVertex(d);

		target.addEdge(a, b);
		target.addEdge(b, c);
		target.addEdge(c, d);
		target.addEdge(d, a);

		V p_1 = factory.createVertex();
		V p_n = factory.createVertex();

		target.addVertex(p_1);
		target.addVertex(p_n);

		V tmp = p_1;
		for (int i = 0; i < n; i++) {
			V v = factory.createVertex();

			target.addVertex(v);
			target.addEdge(tmp, v);

			if (i % 2 == 0) {
				target.addEdge(a, v);
			} else {
				target.addEdge(b, v);
			}

			tmp = v;
		}
		target.addEdge(tmp, p_n);

	}

}
