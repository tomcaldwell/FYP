package org.gt4j.annas.graph;

import org.gt4j.annas.util.EqualityChecker;

/**
 * Checks equality of directed edges
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class DirectedEdgeEqualityChecker<V, E extends EdgeInterface<V>>
		implements EqualityChecker<E> {

	@SuppressWarnings("unchecked")
	@Override
	public boolean check(Object a, Object b) {
		E e1 = (E) a;
		E e2 = (E) b;

		boolean oneway = e1.getHead().equals(e2.getHead())
				&& e1.getTail().equals(e2.getTail());

		boolean sameObject = e1 == e2;

		return (oneway) && sameObject;
	}

}
