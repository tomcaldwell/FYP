package org.gt4j.annas.graph.classifier;

import java.util.ArrayList;
import java.util.List;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.SimpleUndirectedGraph;

/**
 * Implementation of Cai's FPT algorithm for recognizing the parameterized class
 * C+kv. Note the class C should have a finite forbidden set, if you wish the
 * algorithm to have a "good" running time.
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class AlmostC<V, E extends EdgeInterface<V>> implements Classifier<V, E> {

	private int k;

	/**
	 * This recognizes the base class, the class should have a finite forbidden
	 * set.
	 */
	private Classifier<V, E> recogniser;

	/**
	 * Default constructor
	 * 
	 * @param recogniser recogniser for the base class
	 * @param k max number of vertices to remove
	 */
	public AlmostC(Classifier<V, E> recogniser, int k) {
		super();
		this.k = k;
		this.recogniser = recogniser;
	}

	@Override
	public boolean classify(GraphInterface<V, E> graph) {
		try {
			return this.recognise((SimpleUndirectedGraph<V, E>) graph, this.k);
		} catch (ClassCastException e) {
			return false;
		}
	}

	private boolean recognise(SimpleUndirectedGraph<V, E> graph, int k) {
		if (this.recogniser.classify(graph)) {
			return true;
		} else if (k == 0) {
			return false;
		}
		SimpleUndirectedGraph<V, E> tmp = this.copyGraph(graph);
		SimpleUndirectedGraph<V, E> tmp1;

		List<V> Vs = new ArrayList<V>();

		/*
		 * find a minimal forbidden graph contains within the input
		 */
		for (V v : graph.getVertices()) {
			tmp1 = this.copyGraph(tmp);
			tmp.removeVertex(v);
			if (this.recogniser.classify(tmp)) {
				Vs.add(v);
				tmp = tmp1;
			}
		}

		for (V u : Vs) {
			tmp = this.copyGraph(graph);
			tmp.removeVertex(u);
			if (this.recognise(tmp, (k - 1))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Makes a copy of the graph uses the same vertices and edge objects but
	 * makes a copy of the structure.
	 * 
	 * @param graph
	 * @return a copy of the given graph
	 */
	private SimpleUndirectedGraph<V, E> copyGraph(
			SimpleUndirectedGraph<V, E> graph) {
		SimpleUndirectedGraph<V, E> retval = new SimpleUndirectedGraph<V, E>(
				graph.getEdgeFactory());

		retval.addVertices(graph.getVertices());
		for (E e : graph.getEdges()) {
			retval.addEdge(e.getTail(), e.getHead());
		}
		return retval;

	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}
}
