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
public class XF_5<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, EdgeInterface<V>> {

	private int n;

	/**
	 * Constructs a XF_5 graph generator
	 * 
	 * @param n
	 *            See class documentation
	 */
	public XF_5(int n) {
		super();
		if (n >= 0) {
			this.n = n;
		} else {
			throw new IllegalArgumentException("n must be >= 0");
		}
	}

	@Override
	public void generateGraph(GraphInterface<V, EdgeInterface<V>> target,
			VertexFactory<V> factory, Map<String, ?> Additionalinfo) {
		
		V a = factory.createVertex();
		V c = factory.createVertex();
		V u = factory.createVertex();
		V v = factory.createVertex();

		target.addVertex(a);
		target.addVertex(c);
		target.addVertex(u);
		target.addVertex(v);

		target.addEdge(a, u);
		target.addEdge(c, v);

		V p_1 = factory.createVertex();
		V p_n1 = factory.createVertex();

		target.addEdge(u, p_1);
		target.addEdge(v, p_n1);

		target.addEdge(p_1, a);
		target.addEdge(p_1, c);
		target.addEdge(p_n1, a);
		target.addEdge(p_n1, c);

		V tmp = p_1;
		for (int i = 0; i < n - 2; i++) {
			V p = factory.createVertex();
			
			target.addVertex(p);

			target.addEdge(tmp, p);

			target.addEdge(p, a);
			target.addEdge(p, c);

			tmp = p;
		}
		target.addEdge(tmp, p_n1);
	}

}
