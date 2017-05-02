package org.gt4j.annas.graph.classifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.gt4j.annas.graph.DefaultEdge;
import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.IntegerEdge;
import org.gt4j.annas.graph.SimpleUndirectedGraph;
import org.gt4j.annas.graph.VertexFactory;
import org.gt4j.annas.graph.generate.DefaultVertexFactory;
import org.gt4j.annas.graph.util.Utilities;

public class LineGraphClassifier<V, E extends EdgeInterface<V>> implements Classifier<V, E> {

	private SimpleUndirectedGraph<Integer, IntegerEdge> rootgraph;

	private Map<V, IntegerEdge> mapping;

	private Set<V> includedVertices;

	private VertexFactory<Integer> vFactory;

	public LineGraphClassifier() {
		super();
	}

	private void init() {
		this.rootgraph = new SimpleUndirectedGraph<>(IntegerEdge.class);
		this.includedVertices = new LinkedHashSet<V>();
		this.vFactory = new DefaultVertexFactory();
		this.mapping = new HashMap<>();
	}

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		init();
		V v = nextVertex(graph);

		while (v != null) {
			this.includedVertices.add(v);
			if (this.rootgraph.getOrder() < 4) {
				if(!getRootGraphForSmallGraph(graph)){
					return false;
				}
			} else {

				Set<IntegerEdge> subgraph = getSubGraphVertices(graph, v);
				Set<Integer> vc = getVertexCover(subgraph);
				if (vc.size() == 1) {
					Integer nw = vFactory.createVertex();
					this.rootgraph.addVertex(nw);
					Integer v1 = vc.iterator().next();
					this.rootgraph.addEdge(v1, nw);
					this.mapping.put(v, this.rootgraph.getEdges(v1, nw).iterator().next());
				} else if (vc.size() == 2) {
					Iterator<Integer> it = vc.iterator();
					Integer v1 = it.next();
					Integer v2 = it.next();
					this.rootgraph.addEdge(v1, v2);
					this.mapping.put(v, this.rootgraph.getEdges(v1, v2).iterator().next());
				} else {
					return false;
				}
			}
			v = nextVertex(graph);
		}

		return true;
	}

	/**
	 * Given a line graph this method will produce a root graph.
	 * 
	 * @param graph
	 */
	private boolean getRootGraphForSmallGraph(GraphInterface<V, E> graph) {
		long[] ds = new long[4];
		GraphInterface<V, E> tmp = new SimpleUndirectedGraph<>(graph.getEdgeFactory());
		tmp.addVertices(this.includedVertices);
		for (V v : tmp.getVertices()) {
			for (V u : tmp.getVertices()) {
				if (graph.containsEdge(v, u)) {
					tmp.addEdge(v, u);
				}
			}
		}
		ds = Utilities.getDegreeSequenceAscending(tmp);

		this.mapping.clear();
		if (Arrays.equals(ds, new long[] { 0 })) {
			// a single vertex
			Integer v1, v2;
			v1 = this.vFactory.createVertex();
			v2 = this.vFactory.createVertex();
			this.rootgraph.addVertices(v1, v2);
			this.rootgraph.addEdge(v1, v2);

			// Sets mapping
			this.mapping.put(this.includedVertices.iterator().next(),
					this.rootgraph.getEdges(v1, v2).iterator().next());
		} else if (Arrays.equals(ds, new long[] { 1, 1 })) {
			// P_2
			this.rootgraph.resetEdges();
			Set<Integer> verts = new TreeSet<Integer>(this.rootgraph.getVertices());
			this.rootgraph.removeVertices(verts);
			this.vFactory = new DefaultVertexFactory();
			Integer v1, v2, v3;
			v1 = this.vFactory.createVertex();
			v2 = this.vFactory.createVertex();
			v3 = this.vFactory.createVertex();
			this.rootgraph.addVertices(v1, v2, v3);
			this.rootgraph.addEdge(v1, v2);
			this.rootgraph.addEdge(v2, v3);

			// Sets mapping
			Iterator<V> it = this.includedVertices.iterator();
			this.mapping.put(it.next(), this.rootgraph.getEdges(v1, v2).iterator().next());
			this.mapping.put(it.next(), this.rootgraph.getEdges(v2, v3).iterator().next());

		} else if (Arrays.equals(ds, new long[] { 2, 2, 2 })) {
			// K_3
			this.rootgraph.resetEdges();
			Set<Integer> verts = new TreeSet<Integer>(this.rootgraph.getVertices());
			this.rootgraph.removeVertices(verts);
			this.vFactory = new DefaultVertexFactory();
			Integer v1, v2, v3;
			v1 = this.vFactory.createVertex();
			v2 = this.vFactory.createVertex();
			v3 = this.vFactory.createVertex();
			this.rootgraph.addVertices(v1, v2, v3);
			this.rootgraph.addEdge(v1, v2);
			this.rootgraph.addEdge(v2, v3);
			this.rootgraph.addEdge(v1, v3);

			// Sets mapping
			Iterator<V> it = this.includedVertices.iterator();
			this.mapping.put(it.next(), this.rootgraph.getEdges(v1, v2).iterator().next());
			this.mapping.put(it.next(), this.rootgraph.getEdges(v2, v3).iterator().next());
			this.mapping.put(it.next(), this.rootgraph.getEdges(v1, v3).iterator().next());

		} else if (Arrays.equals(ds, new long[] { 1, 1, 2 })) {
			// P_3
			this.rootgraph.resetEdges();
			Set<Integer> verts = new TreeSet<Integer>(this.rootgraph.getVertices());
			this.rootgraph.removeVertices(verts);
			this.vFactory = new DefaultVertexFactory();
			Integer v1, v2, v3, v4;
			v1 = this.vFactory.createVertex();
			v2 = this.vFactory.createVertex();
			v3 = this.vFactory.createVertex();
			v4 = this.vFactory.createVertex();
			this.rootgraph.addVertices(v1, v2, v3, v4);
			this.rootgraph.addEdge(v1, v2);
			this.rootgraph.addEdge(v2, v3);
			this.rootgraph.addEdge(v3, v4);

			// Sets mapping
			Iterator<V> it = Utilities.getNonDescendingDegreeOrder(tmp).iterator();

			this.mapping.put(it.next(), this.rootgraph.getEdges(v1, v2).iterator().next());
			this.mapping.put(it.next(), this.rootgraph.getEdges(v3, v4).iterator().next());
			this.mapping.put(it.next(), this.rootgraph.getEdges(v2, v3).iterator().next());

		} else if (Arrays.equals(ds, new long[] { 2, 2, 3, 3 })) {
			// Diamond
			this.rootgraph.resetEdges();
			Set<Integer> verts = new TreeSet<Integer>(this.rootgraph.getVertices());
			this.rootgraph.removeVertices(verts);
			this.vFactory = new DefaultVertexFactory();
			Integer v1, v2, v3, v4;
			v1 = this.vFactory.createVertex();
			v2 = this.vFactory.createVertex();
			v3 = this.vFactory.createVertex();
			v4 = this.vFactory.createVertex();
			this.rootgraph.addVertices(v1, v2, v3, v4);
			this.rootgraph.addEdge(v1, v2);
			this.rootgraph.addEdge(v2, v3);
			this.rootgraph.addEdge(v3, v1);
			this.rootgraph.addEdge(v4, v1);

			// Sets mapping
			Iterator<V> it = Utilities.getNonDescendingDegreeOrder(tmp).iterator();

			this.mapping.put(it.next(), this.rootgraph.getEdges(v4, v1).iterator().next());
			this.mapping.put(it.next(), this.rootgraph.getEdges(v2, v3).iterator().next());

			this.mapping.put(it.next(), this.rootgraph.getEdges(v1, v2).iterator().next());
			this.mapping.put(it.next(), this.rootgraph.getEdges(v3, v1).iterator().next());

		} else if (Arrays.equals(ds, new long[] { 1, 1, 1, 3 })) {
			// K_1,3
			// ##################################################
			// YOU CANT GET HERE THIS GRAPH IS NEVER A LINE GRAPH
			// ##################################################
			return false;
		} else if (Arrays.equals(ds, new long[] { 1, 1, 2, 2 })) {
			// P_4
			this.rootgraph.resetEdges();
			Set<Integer> verts = new TreeSet<Integer>(this.rootgraph.getVertices());
			this.rootgraph.removeVertices(verts);
			this.vFactory = new DefaultVertexFactory();
			Integer v1, v2, v3, v4, v5;
			v1 = this.vFactory.createVertex();
			v2 = this.vFactory.createVertex();
			v3 = this.vFactory.createVertex();
			v4 = this.vFactory.createVertex();
			v5 = this.vFactory.createVertex();
			this.rootgraph.addVertices(v1, v2, v3, v4, v5);
			this.rootgraph.addEdge(v1, v2);
			this.rootgraph.addEdge(v2, v3);
			this.rootgraph.addEdge(v3, v4);
			this.rootgraph.addEdge(v4, v5);

			// Sets mapping
			Iterator<V> it = Utilities.getNonDescendingDegreeOrder(tmp).iterator();

			this.mapping.put(it.next(), this.rootgraph.getEdges(v1, v2).iterator().next());
			V x = it.next();
			this.mapping.put(x, this.rootgraph.getEdges(v4, v5).iterator().next());

			V u = it.next();
			if (tmp.containsEdge(u, x)) {
				this.mapping.put(u, this.rootgraph.getEdges(v3, v4).iterator().next());
				this.mapping.put(it.next(), this.rootgraph.getEdges(v2, v3).iterator().next());
			} else {
				this.mapping.put(it.next(), this.rootgraph.getEdges(v3, v4).iterator().next());
				this.mapping.put(u, this.rootgraph.getEdges(v2, v3).iterator().next());
			}

		} else if (Arrays.equals(ds, new long[] { 2, 2, 2, 2 })) {
			// C_4
			this.rootgraph.resetEdges();
			Set<Integer> verts = new TreeSet<Integer>(this.rootgraph.getVertices());
			this.rootgraph.removeVertices(verts);
			this.vFactory = new DefaultVertexFactory();
			Integer v1, v2, v3, v4;
			v1 = this.vFactory.createVertex();
			v2 = this.vFactory.createVertex();
			v3 = this.vFactory.createVertex();
			v4 = this.vFactory.createVertex();
			this.rootgraph.addVertices(v1, v2, v3, v4);
			this.rootgraph.addEdge(v1, v2);
			this.rootgraph.addEdge(v2, v3);
			this.rootgraph.addEdge(v3, v4);
			this.rootgraph.addEdge(v4, v1);

			// Sets mapping
			Iterator<V> it = Utilities.getNonDescendingDegreeOrder(tmp).iterator();
			V c1 = it.next();
			Iterator<E> edges = graph.getEdges(c1).iterator();

			V c2 = edges.next().getHead();
			V c4 = edges.next().getHead();
			Set<V> vs = new TreeSet<>(graph.getVertices());
			vs.remove(c1);
			vs.remove(c2);
			vs.remove(c4);
			V c3 = vs.iterator().next();

			this.mapping.put(c1, this.rootgraph.getEdges(v1, v2).iterator().next());
			this.mapping.put(c2, this.rootgraph.getEdges(v2, v3).iterator().next());
			this.mapping.put(c3, this.rootgraph.getEdges(v3, v4).iterator().next());
			this.mapping.put(c4, this.rootgraph.getEdges(v4, v1).iterator().next());

		} else if (Arrays.equals(ds, new long[] { 1, 2, 2, 3 })) {
			// K_1,3 + 1e
			this.rootgraph.resetEdges();
			Set<Integer> verts = new TreeSet<Integer>(this.rootgraph.getVertices());
			this.rootgraph.removeVertices(verts);
			this.vFactory = new DefaultVertexFactory();
			Integer v1, v2, v3, v4, v5;
			v1 = this.vFactory.createVertex();
			v2 = this.vFactory.createVertex();
			v3 = this.vFactory.createVertex();
			v4 = this.vFactory.createVertex();
			v5 = this.vFactory.createVertex();
			this.rootgraph.addVertices(v1, v2, v3, v4, v5);
			this.rootgraph.addEdge(v1, v2);
			this.rootgraph.addEdge(v1, v3);
			this.rootgraph.addEdge(v1, v4);
			this.rootgraph.addEdge(v4, v5);

			// Sets mapping
			Iterator<V> it = Utilities.getNonDescendingDegreeOrder(tmp).iterator();
			V c1 = it.next();
			V c2 = it.next();
			V c3 = it.next();
			V c4 = it.next();

			this.mapping.put(c1, this.rootgraph.getEdges(v4, v5).iterator().next());
			this.mapping.put(c2, this.rootgraph.getEdges(v1, v3).iterator().next());

			this.mapping.put(c3, this.rootgraph.getEdges(v1, v2).iterator().next());
			this.mapping.put(c4, this.rootgraph.getEdges(v1, v4).iterator().next());

		} else if (Arrays.equals(ds, new long[] { 3, 3, 3, 3 })) {
			// K_4
			this.rootgraph.resetEdges();
			Set<Integer> verts = new TreeSet<Integer>(this.rootgraph.getVertices());
			this.rootgraph.removeVertices(verts);
			this.vFactory = new DefaultVertexFactory();
			Integer v1, v2, v3, v4, v5;
			v1 = this.vFactory.createVertex();
			v2 = this.vFactory.createVertex();
			v3 = this.vFactory.createVertex();
			v4 = this.vFactory.createVertex();
			v5 = this.vFactory.createVertex();
			this.rootgraph.addVertices(v1, v2, v3, v4, v5);
			this.rootgraph.addEdge(v1, v2);
			this.rootgraph.addEdge(v1, v3);
			this.rootgraph.addEdge(v1, v4);
			this.rootgraph.addEdge(v1, v5);

			// Sets mapping
			Iterator<V> it = Utilities.getNonDescendingDegreeOrder(tmp).iterator();

			this.mapping.put(it.next(), this.rootgraph.getEdges(v1, v2).iterator().next());
			this.mapping.put(it.next(), this.rootgraph.getEdges(v1, v3).iterator().next());
			this.mapping.put(it.next(), this.rootgraph.getEdges(v1, v4).iterator().next());
			this.mapping.put(it.next(), this.rootgraph.getEdges(v1, v5).iterator().next());

		} else {
			return false;
		}
		return true;
	}

	/**
	 * Returns the next vertex of the line graph to be included
	 * 
	 * @return
	 */
	private V nextVertex(GraphInterface<V, E> graph) {
		if (this.includedVertices.size() == 0) {
			return graph.getVertices().iterator().next();
		} else {
			return Utilities.getOpenNeighbourhood(graph, this.includedVertices).stream().findAny().orElse(null);
		}
	}

	/**
	 * gets the vertices of G that are end-points of the edges that correspond
	 * to the neighbours of v in L.
	 * 
	 * @param vertex
	 * @return
	 */
	private Set<IntegerEdge> getSubGraphVertices(GraphInterface<V, E> graph, V vertex) {
		Set<V> vs = Utilities.getOpenNeighbourhood(graph, vertex).stream()
				.filter(v -> this.includedVertices.contains(v)).collect(Collectors.toSet());
		vs.add(vertex);
		return vs.stream().map(n -> this.mapping.get(n)).collect(Collectors.toSet());
	}

	/**
	 * Returns a vertex cover of the subgraph S of G formed by the edges that
	 * correspond to the neighbours of v in L.
	 * 
	 * @return
	 */
	private Set<Integer> getVertexCover(Set<IntegerEdge> edges) {
		Set<Integer> vc = new LinkedHashSet<Integer>();
		vcRecurse(edges, vc);

		return vc;
	}

	private boolean vcRecurse(Set<IntegerEdge> edges, Set<Integer> vc) {

		if (vc.size() < 3) {
			IntegerEdge e = getUncoveredEdge(edges, vc);
			if (e == null) {
				return true;
			}

			vc.add(e.getHead());
			if (vcRecurse(edges, vc)) {
				return true;
			} else {
				vc.remove(e.getHead());
				vc.add(e.getTail());
				if (vcRecurse(edges, vc)) {
					return true;
				} else {
					vc.remove(e.getTail());
					return false;
				}
			}
		} else {
			return false;
		}

	}

	private IntegerEdge getUncoveredEdge(Set<IntegerEdge> edges, Set<Integer> vc) {
		return edges.stream().filter(e -> !vc.contains(e.getHead()) && !vc.contains(e.getTail())).findFirst()
				.orElse(null);
	}

}
