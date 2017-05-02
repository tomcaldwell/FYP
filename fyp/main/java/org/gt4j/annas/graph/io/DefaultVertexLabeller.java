package org.gt4j.annas.graph.io;

/**
 * Default implementation of a {@link org.gt4j.annas.graph.io.VertexLabeller}
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 */
public class DefaultVertexLabeller<V> implements VertexLabeller<V> {

	@Override
	public String getLabel(V v) {
		return v.toString();
	}

}
