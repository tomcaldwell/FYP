package org.gt4j.annas.graph;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Base interface for all Graphs
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public interface GraphInterface<V, E extends EdgeInterface<V>>  {

	/**
	 * Adds a vertex to the graph
	 * 
	 * @param vertex
	 *            to add to the graph
	 * 
	 * @return true is vertex was successfully added.
	 */
	public boolean addVertex(V vertex);

	/**
	 * Adds all vertices in the collection
	 * 
	 * @param vertices vertices to add
	 * 
	 * @return true is all vertices were successfully added.
	 */
	public boolean addVertices(Collection<? extends V> vertices);

	/**
	 * adds all vertices in the array.
	 * 
	 * @param vs
	 *            Vertices to add
	 * @return true is all vertices were successfully added.
	 */
	@SuppressWarnings("unchecked")
	public boolean addVertices(V... vs);

	/**
	 * Removes a vertex from the graph
	 * 
	 * @param vertex
	 *            to remove from the graph
	 * @return true if the vertex was successfully removed from the graph
	 */
	public boolean removeVertex(V vertex);

	/**
	 * Remove a set of vertices
	 * 
	 * @param vertices
	 *            Set of vertices to remove
	 * 
	 * @return true if all vertices were successfully removed
	 */
	public boolean removeVertices(Collection<? extends V> vertices);

	/**
	 * Gets the set of vertices of the graph
	 * 
	 * @return set of vertices in the graph
	 */
	public Set<V> getVertices();

	/**
	 * Adds an edge to the graph
	 * 
	 * @param tail
	 *            Tail of the edge
	 * @param head
	 *            Head of the edge
	 * @return the new edge
	 */
	public E addEdge(V tail, V head);

	/**
	 * Gets all edge incident to a given vertex
	 * 
	 * @param tail
	 *            tail of the edge
	 * @return Set of all edge incident to a given vertex
	 */
	public Set<E> getEdges(V tail);

	/**
	 * Gets all edges from the tail to the head
	 * 
	 * @param tail
	 *            tail of the edge
	 * @param head
	 *            head of the edge
	 * @return Set of all edge between tail and head.
	 */
	public Set<E> getEdges(V tail, V head);

	/**
	 * Get the Edges of the graph
	 * 
	 * @return Set of edges
	 */
	public Set<E> getEdges();
	
	/**
	 * Performs the given action for each element of
	 * the Iterable until all elements have been
	 * processed or the action throws an exception. 
	 * 
	 * @param comsumer 
	 */
	public void forEachVertex(Consumer<V> comsumer);
	
	/**
	 * Performs the given action for each element of 
	 * the Iterable until all elements have been 
	 * processed or the action throws an exception. 
	 * 
	 * @param consumer
	 */
	public void forEachEdge(Consumer<E> consumer);

	/**
	 * Removes a set of edges
	 * 
	 * @param edges
	 *            Set of edges to remove
	 * @return true if all edges were removed successfully
	 */
	public boolean removeEdges(Collection<? extends E> edges);

	/**
	 * Removes an edge from the graph
	 * 
	 * @param edge
	 *            to remove
	 * @return true if the edge was successfully removed.
	 */
	public boolean removeEdge(E edge);

	/**
	 * Removes all edge from the graph between tail and head
	 * 
	 * @param tail
	 *            tail of the edge to remove
	 * @param head
	 *            head of the edge to remove
	 * 
	 * @return true if the edge was successfully removed.
	 */
	public boolean removeEdge(V tail, V head);

	/**
	 * Removes all edge incident with a vertex
	 * 
	 * @param tail
	 *            tail of the edge
	 * 
	 * @return true if all edges were deleted incident to a vertex
	 */
	public boolean removeEdge(V tail);

	/**
	 * Removes all edges from the graph
	 * 
	 * @return true if all edges were removed
	 */
	public boolean resetEdges();

	/**
	 * Gets the {@link org.gt4j.annas.graph.EdgeFactory} used by the graph
	 * 
	 * @return EdgeFactory used by the graph
	 */
	public EdgeFactory<V, E> getEdgeFactory();

	/**
	 * Checks if the graph contains an edge between two vertices
	 * 
	 * @param head head of edge
	 * @param tail tail of edge
	 * @return true if the edge is in the graph
	 */
	public boolean containsEdge(V head, V tail);

	/**
	 * Checks if a vertex is in the graph
	 * 
	 * @param edge edge 
	 * @return true if the edge is in the graph
	 */
	public boolean containsEdge(E edge);

	/**
	 * Checks if a vertex is in the graph
	 * 
	 * @param vertex vertex to check
	 * @return true if the vertex is in the graph
	 */
	public boolean containsVertex(V vertex);

	/**
	 * Number of edges contained within the graph
	 * 
	 * @return number of edges in the graph
	 */
	public int getSize();

	/**
	 * Number of vertices contained within the graph
	 * 
	 * @return number of vertices in the graph
	 */
	public int getOrder();

	/**
	 * Degree of vertex (Out degree for directed graphs)
	 * 
	 * @param vertex vertex to get degree of
	 * @return degree of the given vertex
	 */
	public int getDegree(V vertex);

	/**
	 * Get current {@link org.gt4j.annas.graph.GraphObserver}
	 * 
	 * @return GraphObserver
	 */
	public GraphObserver<V, E> getObserver();

	/**
	 * Set the {@link org.gt4j.annas.graph.GraphObserver}
	 * 
	 * @param GO graph observer
	 */
	public void setObserver(GraphObserver<V, E> GO);

	/**
	 * Gets edge class
	 * 
	 * @return edge class
	 */
	public Class<?> getEdgeClass();

	/**
	 * Returns if the graph is directed
	 * 
	 * @return true if the graph is directed
	 */
	public boolean isDirected();
}
