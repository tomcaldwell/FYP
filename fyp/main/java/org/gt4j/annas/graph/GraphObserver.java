package org.gt4j.annas.graph;


/**
 * Any class wishing to observer a graph must implement this interface. The
 * parameters provided when a method is called is passed to the observer.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public interface GraphObserver<V, E extends EdgeInterface<V>> {

	/**
	 * Method called when an edge is added to the graph being observed.
	 * 
	 * @param graph
	 *            Graph being observed
	 * @param args
	 *            arguments passed to the method
	 */
	public void edgeAdded(GraphInterface<V, E> graph, Object[] args);

	/**
	 * Method called when an edge is removed from the graph being observed.
	 * 
	 * @param graph
	 *            Graph being observed
	 * @param args
	 *            arguments passed to the method
	 */
	public void edgeRemoved(GraphInterface<V, E> graph, Object[] args);

	/**
	 * Method called when a vertex is added to the graph being observed.
	 * 
	 * @param graph
	 *            Graph being observed
	 * @param args
	 *            arguments passed to the method
	 */
	public void vertexAdded(GraphInterface<V, E> graph, Object[] args);

	/**
	 * Method called when a vertex is removed from the graph being observed.
	 * 
	 * @param graph
	 *            Graph being observed
	 * @param args
	 *            arguments passed to the method
	 */
	public void vertexRemoved(GraphInterface<V, E> graph, Object[] args);

}
