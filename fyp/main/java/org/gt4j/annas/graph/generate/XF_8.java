/*
 * annas : Graph implementation and algorithm package
 * 
 * Project Info: http://annas.googlecode.com Project Creator: Sam Wilson
 * 
 * Copyright (C) 2008-2014 Sam Wilson
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
public class XF_8<V, E extends EdgeInterface<V>> implements
		GraphGenerator<V, EdgeInterface<V>> {

	private int n;

	/**
	 * Constructs a XF_8 graph generator
	 * 
	 * @param n
	 *            See class documentation
	 */
	public XF_8(int n) {
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
		target.addEdge(b, d);

		V v_1 = factory.createVertex();
		V v_n = factory.createVertex();

		target.addVertex(v_1);
		target.addVertex(v_n);

		target.addEdge(c, v_1);
		target.addEdge(d, v_n);

		V l = v_1;
		for (int i = 0; i < n - 2; i++) {
			V v = factory.createVertex();
			V u = factory.createVertex();

			target.addVertex(v);
			target.addVertex(u);

			target.addEdge(u, l);
			target.addEdge(u, v);

			target.addEdge(v, c);
			target.addEdge(v, d);
		}

		V u = factory.createVertex();
		target.addVertex(u);

		target.addEdge(u, v_n);
		target.addEdge(u, l);

	}

}
