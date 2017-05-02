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
public class XF_2<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, EdgeInterface<V>> {

	private int n;

	/**
	 * Constructs a XF_2 graph generator
	 * 
	 * @param n
	 *            See class documentation
	 */
	public XF_2(int n) {
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
		V fan = factory.createVertex();
		target.addVertex(fan);
		V pend1 = factory.createVertex();
		V pend2 = factory.createVertex();
		V pend3 = factory.createVertex();
		target.addVertex(pend1);
		target.addVertex(pend2);
		target.addVertex(pend3);

		target.addEdge(pend3, fan);

		V newnode = factory.createVertex();
		V tmp;
		target.addVertex(newnode);
		target.addEdge(pend1, newnode);
		target.addEdge(fan, newnode);

		tmp = newnode;
		for (int i = 0; i < this.n - 1; i++) {
			newnode = factory.createVertex();
			target.addVertex(newnode);
			target.addEdge(tmp, newnode);
			target.addEdge(fan, newnode);
			tmp = newnode;
		}

		target.addEdge(tmp, pend2);

	}

}
