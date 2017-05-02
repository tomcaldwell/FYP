package org.gt4j.annas.graph.io;

import java.io.InputStream;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;

/**
 * Provides an interfaces for classes that import graphs into this package.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public interface GraphImporter<V, E extends EdgeInterface<V>> {

	/**
	 * Imports a graph into this package from the given input stream
	 * 
	 * @param input
	 *            Source of the graph
	 * @param graph graph to import into
	 * @return imported graph
	 */
	public GraphInterface<V, E> importGraph(InputStream input,
			GraphInterface<V, E> graph);

	/**
	 * Imports a graph into this package from a file.
	 * 
	 * @param filename
	 *            Name of file containing the graph
	 * @param graph graph to import into
	 * @return imported graph
	 */
	public GraphInterface<V, E> importGraph(String filename,
			GraphInterface<V, E> graph);
}
