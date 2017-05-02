package org.gt4j.annas.graph;

import org.gt4j.annas.exception.NotInstantiableException;

/**
 * Factory for edges given a class.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class ClassEdgeFactory<V, E extends EdgeInterface<V>> implements
		EdgeFactory<V, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6701330234981622797L;
	
	private final Class<? extends E> edgeClass;

	/**
	 * Constructs an EdgeFactory
	 * 
	 * @param edgeClass
	 *            edge class
	 */
	public ClassEdgeFactory(Class<? extends E> edgeClass) {
		if (edgeClass == null) {
			throw new NullPointerException();
		}
		this.edgeClass = edgeClass;
	}

	@Override
	public E create(V source, V target) {
		try {
			E e = this.edgeClass.newInstance();
			e.setTail(source);
			e.setHead(target);
			return e;
		} catch (Exception ex) {
			throw new NotInstantiableException("Cant get new instance of edge type", ex);
		}
	}

	@Override
	public Class<?> getEdgeClass() {
		return this.edgeClass;
	}
}
