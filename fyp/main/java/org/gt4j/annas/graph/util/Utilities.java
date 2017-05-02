package org.gt4j.annas.graph.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.gt4j.annas.graph.DirectedGraph;
import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.SimpleDirectedGraph;
import org.gt4j.annas.graph.UndirectedGraph;
import org.gt4j.annas.math.MathUtil;
import org.gt4j.annas.math.Matrix;
import org.gt4j.annas.math.combinatorics.CombinatoricUtil;

/**
 * 
 * @author Sam Wilson
 * 
 */
public class Utilities {

	/**
	 * Computes the complement of the graph, the vertices are reused.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return the complement of the given graph
	 */
	@SuppressWarnings("unchecked")
	public static <V, E extends EdgeInterface<V>> GraphInterface<V, E> getComplement(GraphInterface<V, E> graph) {
		GraphInterface<V, E> retval = null;

		try {
			retval = graph.getClass().newInstance();
		} catch (Exception e) {
			retval = new UndirectedGraph<>(graph.getEdgeFactory());
		}
		retval.addVertices(graph.getVertices());
		for (V v : graph.getVertices()) {
			for (V u : graph.getVertices()) {
				if (graph.getEdges(v, u).size() == 0) {
					retval.addEdge(v, u);
				}
			}
		}

		return retval;
	}

	/**
	 * Makes a copy of the graph, the copy reuses the vertices but new edges are
	 * created.
	 * 
	 * @param graph
	 *            graph to copy
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @return a copy of the provided graph
	 */
	@SuppressWarnings("unchecked")
	public static <V, E extends EdgeInterface<V>> GraphInterface<V, E> getCopy(GraphInterface<V, E> graph) {
		GraphInterface<V, E> retval = null;

		try {
			retval = graph.getClass().newInstance();
		} catch (Exception e) {
			retval = new UndirectedGraph<>(graph.getEdgeFactory());
		}
		retval.addVertices(graph.getVertices());
		for (EdgeInterface<V> e : graph.getEdges()) {
			retval.addEdge(e.getTail(), e.getHead());
		}

		return retval;
	}

	/**
	 * Gets an undirected representation of the given graph
	 * 
	 * @param graph
	 * @return an undirected graph
	 */
	public static <V, E extends EdgeInterface<V>> GraphInterface<V, E> getUndirected(DirectedGraph<V, E> graph) {
		GraphInterface<V, E> retval = new UndirectedGraph<>(graph.getEdgeFactory());

		retval.addVertices(graph.getVertices());
		for (EdgeInterface<V> e : graph.getEdges()) {
			retval.addEdge(e.getTail(), e.getHead());
		}

		return retval;
	}

	/**
	 * Gets the adjacency matrix of the given graph.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            graph to get adjacency matrix of
	 * @return the adjacency matrix of the given graph
	 */
	public static <V, E extends EdgeInterface<V>> Matrix getAdjacencyMatrix(GraphInterface<V, ?> graph) {
		int order = graph.getOrder();
		double[][] adj = new double[order][order];

		Iterator<V> outer = graph.getVertices().iterator();
		Iterator<V> inner = null;
		for (int i = 0; outer.hasNext(); i++) {
			V vertex = outer.next();
			inner = graph.getVertices().iterator();
			for (int j = 0; inner.hasNext(); j++) {
				if (graph.getEdges(vertex, inner.next()).size() != 0) {
					adj[i][j] = 1;
				} else {
					adj[i][j] = 0;
				}
			}
		}

		return new Matrix(adj);
	}

	/**
	 * Gets the connected components of the graph.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return A collection of collections containing the vertices of the
	 *         connected components
	 */
	public static <V, E extends EdgeInterface<V>> Collection<Collection<V>> getConnectedComponents(
			GraphInterface<V, E> graph) {
		ArrayList<V> toVisit = new ArrayList<V>(graph.getVertices());

		ArrayList<Collection<V>> components = new ArrayList<Collection<V>>();

		Stack<V> stack = new Stack<V>();
		List<V> Visited = new ArrayList<V>();

		while (!toVisit.isEmpty()) {
			stack.push(toVisit.get(0));
			while (!stack.isEmpty()) {
				V current = stack.pop();
				Visited.add(current);
				toVisit.remove(current);

				Set<E> edges = graph.getEdges(current);

				for (E edge : edges) {
					V otherEnd = null;
					if (edge.getHead() == current) {
						otherEnd = edge.getTail();
					} else {
						otherEnd = edge.getHead();
					}
					if (!stack.contains(otherEnd) && toVisit.contains(otherEnd)) {
						stack.add(otherEnd);
					}
				}
			}
			components.add(Visited);
			Visited = new ArrayList<V>();
		}

		return components;
	}

	/**
	 * Gets the closed neighbourhood of a vertex. The closed neighbourhood is
	 * the set of neighbours including the given vertex.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @param vertex
	 *            vertex to get closed neighbourhood of
	 * @return The set of vertices adjacent to the given vertex (including the
	 *         given vertex).
	 */
	public static <V, E extends EdgeInterface<V>> Collection<V> getClosedNeighbourhood(GraphInterface<V, E> graph,
			V vertex) {
		Collection<V> vertices = Utilities.getOpenNeighbourhood(graph, vertex);
		vertices.add(vertex);

		return vertices;
	}

	/**
	 * Gets the closed neighbourhood of a given set of vertices. The closed
	 * neighbourhood is the set of neighbours including the given vertices.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @param vs
	 *            set of vertices to get closed neighbourhood of
	 * @return A collection of vertices adjacent to a given vertex set.
	 */
	public static <V, E extends EdgeInterface<V>> Collection<V> getClosedNeighbourhood(GraphInterface<V, E> graph,
			Collection<V> vs) {
		Collection<V> vertices = Utilities.getOpenNeighbourhood(graph, vs);
		vertices.addAll(vs);

		return vertices;
	}

	/**
	 * Gets the closed neighbourhood of a vertex up to n distance from the
	 * central vertex
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            Graph
	 * @param vertex
	 *            central vertex
	 * @param n
	 *            distance from central vertex
	 * @return Gets a collection of the vertices that are within a specified
	 *         distance from a central vertex including the central vertex.
	 */
	public static <V, E extends EdgeInterface<V>> Set<V> getClosedNNeighborhood(GraphInterface<V, E> graph, V vertex,
			int n) {
		Set<V> neighbors = new HashSet<V>(graph.getDegree(vertex));
		neighbors.add(vertex);
		for (int i = 0; i < n; i++) {
			Set<V> newneighbors = new HashSet<V>(graph.getVertices().size());
			for (V v : neighbors) {
				newneighbors.addAll(Utilities.getOpenNeighbourhood(graph, v));
			}
			neighbors.addAll(newneighbors);
		}
		return neighbors;
	}

	/**
	 * Gets the open neighbourhood of a given vertex.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @param vertex
	 *            set of vertices to get open neighbourhood of
	 * @return A collection of vertices adjacent to a given vertex.
	 */
	public static <V, E extends EdgeInterface<V>> Collection<V> getOpenNeighbourhood(GraphInterface<V, E> graph,
			V vertex) {
		Collection<V> vertices = new LinkedHashSet<V>();
		Set<E> edges = graph.getEdges(vertex);

		for (E edge : edges) {
			if (edge.getHead() == vertex) {
				vertices.add(edge.getTail());
			} else {
				vertices.add(edge.getHead());
			}
		}
		vertices.remove(vertex);
		return vertices;
	}

	/**
	 * Gets the open neighbourhood of a given set of vertices.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @param vs
	 *            set of vertices to get open neighbourhood of
	 * @return A collection of vertices adjacent to a given vertex set.
	 */
	public static <V, E extends EdgeInterface<V>> Collection<V> getOpenNeighbourhood(GraphInterface<V, E> graph,
			Collection<V> vs) {
		Collection<V> vertices = new LinkedHashSet<V>();

		for (V v : vs) {
			vertices.addAll(getOpenNeighbourhood(graph, v));
		}
		vertices.removeAll(vs);
		return vertices;
	}

	/**
	 * Gets the closed neighborhood of a vertex up to n distance from the
	 * central vertex
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            Graph
	 * @param vertex
	 *            central vertex
	 * @param n
	 *            distance from central vertex
	 * @return Gets a collection of the vertices that are within a specified
	 *         distance from a central vertex.
	 */
	public static <V, E extends EdgeInterface<V>> Set<V> getOpenNNeighborhood(GraphInterface<V, E> graph, V vertex,
			int n) {
		Set<V> neighbors = new HashSet<V>(graph.getDegree(vertex));
		neighbors.add(vertex);
		for (int i = 0; i < n; i++) {
			Set<V> newneighbors = new HashSet<V>(graph.getVertices().size());
			for (V v : neighbors) {
				newneighbors.addAll(Utilities.getOpenNeighbourhood(graph, v));
			}
			neighbors.addAll(newneighbors);
		}
		neighbors.remove(vertex);
		return neighbors;
	}

	/**
	 * Gets the vertices of even degree.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return A collection of vertices of even degree.
	 */
	public static <V, E extends EdgeInterface<V>> Collection<V> getEvenDegreeVertices(GraphInterface<V, E> graph) {
		return graph.getVertices().stream().filter(p -> graph.getDegree(p) % 2 == 0)
				.collect(Collectors.toCollection(ArrayList<V>::new));
	}

	/**
	 * Gets the vertices of odd degree.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return A collection of vertices of odd degree.
	 */
	public static <V, E extends EdgeInterface<V>> Collection<V> getOddDegreeVertices(GraphInterface<V, E> graph) {

		return graph.getVertices().stream().filter(p -> graph.getDegree(p) % 2 == 1)
				.collect(Collectors.toCollection(ArrayList<V>::new));
	}

	/**
	 * Gets the vertices of a specified degree.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @param degree
	 *            specified degree.
	 * @return A collection of vertices with a specified degree
	 */
	public static <V, E extends EdgeInterface<V>> Collection<V> getVerticesOfDegree(GraphInterface<V, E> graph,
			int degree) {
		return graph.getVertices().stream().filter(p -> graph.getDegree(p) == degree)
				.collect(Collectors.toCollection(ArrayList<V>::new));
	}

	/**
	 * Gets the minimum degree
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return minimum degree
	 */
	public static <V, E extends EdgeInterface<V>> int getMinimumDegree(GraphInterface<V, E> graph) {

		return graph.getVertices().stream().map(p -> graph.getDegree(p)).min(Double::compare).get();
	}

	/**
	 * Gets the maximum degree
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return maximum degree
	 */
	public static <V, E extends EdgeInterface<V>> int getMaximumDegree(GraphInterface<V, E> graph) {

		return graph.getVertices().stream().map(p -> graph.getDegree(p)).max(Double::compare).get();
	}

	/**
	 * Gets the diameter of the graph
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return diameter of the given graph
	 */
	public static <V, E extends EdgeInterface<V>> int getDiameter(GraphInterface<V, E> graph) {
		Floyd<V, E> f = new Floyd<V, E>(graph);
		double[][] d = f.getD();
		int max = 0;
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < i; j++) {
				if (d[i][j] > max) {
					max = (int) d[i][j];
				}
			}
		}
		return max;

	}

	/**
	 * Gets the the graph has no edges.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return true if the graph is empty, false otherwise.
	 */
	public static <V, E extends EdgeInterface<V>> boolean isEmpty(GraphInterface<V, E> graph) {
		return graph.getEdges().size() == 0;
	}

	/**
	 * Constructs a graph from the given graph. Two vertices are adjacent in the
	 * new graphs if and only if their is a path of length at most n between the
	 * vertices.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            Original graph
	 * @param n
	 *            Distance
	 * @return Constructed graph
	 */
	public static <V, E extends EdgeInterface<V>> GraphInterface<V, E> constructPowerGraph(GraphInterface<V, E> graph,
			int n) {

		UndirectedGraph<V, E> retval = new UndirectedGraph<V, E>(graph.getEdgeFactory());
		for (V v : graph.getVertices()) {
			retval.addVertex(v);
		}

		for (V v : graph.getVertices()) {
			Set<V> neigs = Utilities.getOpenNNeighborhood(graph, v, n);
			for (V u : neigs) {
				if (!retval.containsEdge(v, u)) {
					retval.addEdge(v, u);
				}
			}
		}

		return retval;
	}

	/**
	 * Gets the density of the graph. The density of a graph is the number of
	 * edge over the maximum number of edges in a graph of the same number of
	 * vertices.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return density of a given graph
	 */
	public static <V, E extends EdgeInterface<V>> double getDensity(GraphInterface<V, E> graph) {

		if (graph instanceof DirectedGraph) {
			if (((DirectedGraph<V, E>) graph).allowMultipleEdges()) {
				throw new IllegalArgumentException("Graph allows multiple edge");
			}
			return graph.getSize() / Math.pow(2, CombinatoricUtil.nChooseK(graph.getOrder(), 2));
		}

		if (graph instanceof UndirectedGraph) {
			return graph.getSize() / graph.getOrder() * (graph.getOrder() - 1) / 2;
		}

		return -1;

	}

	/**
	 * Gets the average degree of the graph.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return the mean degree of the graph
	 */
	public static <V, E extends EdgeInterface<V>> double getAverageDegree(GraphInterface<V, E> graph) {
		return graph.getVertices().stream().mapToInt(p -> graph.getDegree(p)).average().orElse(0);

	}

	/**
	 * Gets a histogram of the degrees of the graph.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return array of longs where the degree is the index.
	 */
	public static <V, E extends EdgeInterface<V>> long[] getDegreeHistograph(GraphInterface<V, E> graph) {
		long[] degrees = new long[Utilities.getMaximumDegree(graph) + 1];
		graph.getVertices().stream().collect(Collectors.groupingBy(p -> graph.getDegree(p)))
				.forEach((p, l) -> degrees[p] = l.size());

		return degrees;

	}

	/**
	 * Gets the degree sequence of the graph
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return an unsorted degree sequence.
	 */
	public static <V, E extends EdgeInterface<V>> long[] getDegreeSequence(GraphInterface<V, E> graph) {

		return graph.getVertices().stream().mapToLong(p -> graph.getDegree(p)).toArray();
	}

	/**
	 * Gets the degree sequence of the graph in ascending order
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return an unsorted degree sequence.
	 */
	public static <V, E extends EdgeInterface<V>> long[] getDegreeSequenceAscending(GraphInterface<V, E> graph) {

		return graph.getVertices().stream().mapToLong(p -> graph.getDegree(p)).sorted().toArray();
	}

	/**
	 * Computes the transitive closure of the given graphs
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            graph to calculate transitive closure
	 * 
	 * @return transitive closure
	 */
	public static <V, E extends EdgeInterface<V>> SimpleDirectedGraph<V, E> getTransitiveClosure(
			DirectedGraph<V, E> graph) {

		SimpleDirectedGraph<V, E> retval = new SimpleDirectedGraph<>(graph.getEdgeFactory());

		Set<V> vertices = graph.getVertices();
		retval.addVertices(vertices);

		for (E e : graph.getEdges()) {
			retval.addEdge(e.getTail(), e.getHead());
		}

		Set<V> newEdge = new TreeSet<V>();

		int bound = (int) (Math.floor(MathUtil.log(retval.getOrder(), 2)) + 1);

		for (int i = 0; i < bound; i++) {

			for (V u : vertices) {
				newEdge.clear();

				for (E e1 : retval.getOutEdges(u)) {
					V v = e1.getOtherEndpoint(u);
					for (E e2 : retval.getOutEdges(v)) {
						V w = e2.getOtherEndpoint(v);

						if (retval.getEdges(u, w).size() != 0 || u.equals(w)) {
							// We get here if the edge isn't in the original
							// graph OR adding the edge would introduce a loop
							continue;
						}

						newEdge.add(w);

					}
				}

				for (V v3 : newEdge) {
					retval.addEdge(u, v3);
				}
			}
		}

		return retval;
	}

	/**
	 * Checks if a set of vertices is an independent set.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @param vertices
	 *            set of vertices to check
	 * @return true if the set is independent, false otherwise.
	 */
	public static <V, E extends EdgeInterface<V>> boolean isIndependentSet(GraphInterface<V, E> graph,
			Collection<V> vertices) {

		for (V v1 : vertices) {
			for (V v2 : vertices) {
				if (v1 != v2) {
					if (graph.containsEdge(v1, v2)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Checks if a set of vertices is an independent set.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @param vertices
	 *            set of vertices to check
	 * @return true if the set is independent, false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public static <V, E extends EdgeInterface<V>> boolean isIndependentSet(GraphInterface<V, E> graph, V... vertices) {

		for (V v1 : vertices) {
			for (V v2 : vertices) {
				if (v1 != v2) {
					if (graph.containsEdge(v1, v2)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Checks if a set of vertices is a clique.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @param vertices
	 *            set of vertices to check
	 * @return true if the set is a clique, false otherwise.
	 */
	public static <V, E extends EdgeInterface<V>> boolean isClique(GraphInterface<V, E> graph, Collection<V> vertices) {

		for (V v1 : vertices) {
			for (V v2 : vertices) {
				if (v1 != v2) {
					if (!graph.containsEdge(v1, v2)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Checks if a set of vertices is a clique.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @param vertices
	 *            set of vertices to check
	 * @return true if the set is a clique, false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public static <V, E extends EdgeInterface<V>> boolean isClique(GraphInterface<V, E> graph, V... vertices) {

		for (V v1 : vertices) {
			for (V v2 : vertices) {
				if (v1 != v2) {
					if (!graph.containsEdge(v1, v2)) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Checks if a set of vertices is a clique.
	 * 
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return true if the set is a clique, false otherwise.
	 */
	public static <V, E extends EdgeInterface<V>> boolean isClique(GraphInterface<V, E> graph) {
		return Utilities.isClique(graph, graph.getVertices());
	}

	/**
	 * Returns an iterator of the vertices of the graph ordered by degree.
	 * Minimum degree first.
	 * 
	 * @param <V>
	 *            Vertex type
	 * 
	 * @param <E>
	 *            Edge type
	 * @param graph
	 *            input graph
	 * @return iterator of vertices in non decreasing order
	 */
	public static <V, E extends EdgeInterface<V>> Iterable<V> getNonDescendingDegreeOrder(
			final GraphInterface<V, E> graph) {
		return Utilities.getVerticesInNonDescendingDegreeOrder(graph);
	}

	/**
	 * Returns an iterator of the vertices of the graph ordered by degree.
	 * Minimum degree first.
	 *
	 * @param <V>
	 *            Vertex type
	 * @param <E>
	 *            Edge type
	 * 
	 * @param graph
	 *            input graph
	 * @return iterator of vertices in non decreasing order
	 */
	public static <V, E extends EdgeInterface<V>> Iterable<V> getNonAscendingDegreeOrder(
			final GraphInterface<V, E> graph) {
		List<V> ret = Utilities.getVerticesInNonDescendingDegreeOrder(graph);
		Collections.reverse(ret);
		return ret;

	}

	private static <V, E extends EdgeInterface<V>> List<V> getVerticesInNonDescendingDegreeOrder(
			final GraphInterface<V, E> graph) {

		return graph.getVertices().stream().sorted((o1, o2) -> graph.getDegree(o1) - graph.getDegree(o2))
				.collect(Collectors.toList());
		/*
		 * List<V> ret = new LinkedList<V>(); ret.addAll(graph.getVertices());
		 * Collections.sort(ret, new Comparator<V>() {
		 * 
		 * @Override public int compare(V o1, V o2) { return graph.getDegree(o1)
		 * - graph.getDegree(o2); } }); return ret;
		 */
	}
}
