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
public class XF_7<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, EdgeInterface<V>> {

	private int n;

	/**
	 * Constructs a XF_7 graph generator
	 * 
	 * @param n
	 *            See class documentation
	 */
	public XF_7(int n) {
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
		V e = factory.createVertex();
		V f = factory.createVertex();
		V g = factory.createVertex();
		V h = factory.createVertex();
		V i = factory.createVertex();

		target.addVertex(a);
		target.addVertex(b);
		target.addVertex(c);
		target.addVertex(d);
		target.addVertex(e);
		target.addVertex(f);
		target.addVertex(g);
		target.addVertex(h);
		target.addVertex(i);

		target.addEdge(a, b);
		target.addEdge(b, c);

		target.addEdge(d, e);
		target.addEdge(e, f);

		target.addEdge(g, h);
		target.addEdge(h, i);
		target.addEdge(f, c);
		target.addEdge(g, c);

		V l = f;

		for (int j = 0; j < n - 2; j++) {
			V v = factory.createVertex();
			V u = factory.createVertex();

			target.addVertex(v);
			target.addVertex(u);

			target.addEdge(c, v);
			target.addEdge(l, u);
			target.addEdge(u, v);

			l = f;
		}

		V u = factory.createVertex();
		target.addVertex(u);

		target.addEdge(l, u);
		target.addEdge(u, g);

	}

}
