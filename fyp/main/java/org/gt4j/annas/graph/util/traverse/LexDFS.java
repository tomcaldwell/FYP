package org.gt4j.annas.graph.util.traverse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.gt4j.annas.graph.GraphInterface;

public class LexDFS<V> implements Iterable<V> {

	private GraphInterface<V, ?> graph;

	protected List<V> output;

	/**
	 * Constructor given a graph
	 * 
	 * @param graph
	 *            input graph
	 */
	public LexDFS(GraphInterface<V, ?> graph) {
		super();
		if (graph == null) {
			throw new NullPointerException("Graph can not be null");
		}
		this.graph = graph;
	}

	/**
	 * method that computes the LexDFS the result is sorted in the instance
	 * variable output.
	 */
	protected void run() {
		this.output = new ArrayList<V>(this.graph.getOrder());
		if (this.graph.getOrder() == 0) {
			return;
		}
		ArrayList<List<V>> input = new ArrayList<List<V>>();
		input.add(new ArrayList<V>(this.graph.getVertices()));

		while (!input.isEmpty()) {
			ArrayList<List<V>> tmp = new ArrayList<List<V>>();
			V pivot = input.get(0).get(0);
			this.output.add(pivot);
			input.get(0).remove(pivot);

			for (int i = 0; i < input.size(); i++) {
				ArrayList<V> tmp_in = new ArrayList<V>();
				ArrayList<V> tmp_out = new ArrayList<V>();

				for (int j = 0; j < input.get(i).size(); j++) {
					if (this.graph.getEdges(pivot, input.get(i).get(j)).size() != 0) {
						tmp_in.add(input.get(i).get(j));
					} else {
						tmp_out.add(input.get(i).get(j));
					}
				}
				if (!tmp_in.isEmpty()) {
					tmp.add(tmp_in);
				}
				if (!tmp_out.isEmpty()) {
					tmp.add(tmp_out);
				}
			}

			input = tmp;
		}
	}

	/**
	 * Gets a list containing the Lexicographical depth first ordering
	 * 
	 * @return ordering
	 */
	public List<V> getOrder() {
		this.run();
		return Collections.unmodifiableList(this.output);
	}

	@Override
	public Iterator<V> iterator() {
		this.run();
		return this.output.iterator();
	}

}
