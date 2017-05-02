package org.gt4j.annas.graph.generate;

import org.gt4j.annas.exception.NoMoreIntegersException;
import org.gt4j.annas.graph.VertexFactory;

/**
 * Default implementation of a {@link org.gt4j.annas.graph.VertexFactory}, each vertex is
 * an integer.
 * 
 * @author Sam Wilson
 * 
 */
public class DefaultVertexFactory implements VertexFactory<Integer> {

	/**
	 * Last vertex generated
	 */
	private int count;

	/**
	 * Default constructor
	 */
	public DefaultVertexFactory() {
		super();
		this.count = -1;
	}

	@Override
	public Integer createVertex() {
		if(this.count== Integer.MAX_VALUE){
			throw new NoMoreIntegersException("Run out of integer for vertices");
		}
		this.count++;
		return Integer.valueOf(this.count);
	}

}
