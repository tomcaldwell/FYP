package org.gt4j.annas.graph.util.traverse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.gt4j.annas.graph.GraphInterface;

/**
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 */
public class LexBFS<V> implements Iterable<V> {

	private GraphInterface<V, ?> graph;

	protected List<V> output;

	/**
	 * Constructor given a graph
	 * 
	 * @param graph input graph
	 */
	public LexBFS(GraphInterface<V, ?> graph) {
		super();
		if (graph == null) {
			throw new NullPointerException("Graph can not be null");
		}
		this.graph = graph;
	}

	public void run() {
		this.output = new ArrayList<V>(this.graph.getOrder());
		ArrayList<List<V>> input = new ArrayList<List<V>>();
		input.add(new ArrayList<V>(this.graph.getVertices()));
		this.lexbfs(input);

	}

	private void lexbfs(List<List<V>> input) {

		while (!input.isEmpty()) {
			V vertex = input.get(0).remove(0);
			if (input.get(0).isEmpty()) {
				input.remove(0);
			}

			this.output.add(vertex);
			ArrayList<List<V>> input_new = new ArrayList<List<V>>();
			for (int i = 0; i < input.size(); i++) {
				ArrayList<V> tmp_in = new ArrayList<V>();
				ArrayList<V> tmp_out = new ArrayList<V>();

				for (int j = 0; j < input.get(i).size(); j++) {
					if (this.graph.getEdges(vertex, input.get(i).get(j)).size() != 0) {
						tmp_in.add(input.get(i).get(j));
					} else {
						tmp_out.add(input.get(i).get(j));
					}
				}
				if (!tmp_in.isEmpty()){
					input_new.add(tmp_in);
				}

				if (!tmp_out.isEmpty()){
					input_new.add(tmp_out);
				}
			}
			input = input_new;
		}
	}

	/**
	 * Gets a list containing the Lexicographical breadth first ordering
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
