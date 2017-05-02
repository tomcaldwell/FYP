package org.gt4j.annas.graph.generate;

import java.util.Map;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.VertexFactory;

/**
 * Interface for all graph generator to implement.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public interface GraphGenerator<V, E extends EdgeInterface<V>> {

	/**
	 * Generates a graph
	 * 
	 * @param graph
	 *            Graph to be generated
	 * @param vertexFactory
	 *            Factory to use to get new vertices
	 * @param Additionalinfo
	 *            Map to pass additional information to the generator
	 */
	public void generateGraph(GraphInterface<V, E> graph,
			VertexFactory<V> vertexFactory, Map<String, ?> Additionalinfo);
}
