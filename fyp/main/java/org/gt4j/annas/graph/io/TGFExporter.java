package org.gt4j.annas.graph.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Hashtable;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;

/**
 * 
 * @author Sam Wilson
 * 
 * @param <V>
 *            Vertex type
 * @param <E>
 *            Edge type
 */
public class TGFExporter<V, E extends EdgeInterface<V>> implements
		GraphExporter<V, E> {

	/**
	 * Vertex labeller
	 */
	private VertexLabeller<V> labeller = new DefaultVertexLabeller<V>();

	/**
	 * Constructs a TGFExporter which will label the vertices as prescribed by
	 * the {@link org.gt4j.annas.graph.io.VertexLabeller}
	 * 
	 * @param labeller Vertex labeller
	 */
	public TGFExporter(VertexLabeller<V> labeller) {
		super();
		this.labeller = labeller;
	}

	/**
	 * Default constructor
	 */
	public TGFExporter() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void exportGraph(OutputStream ops, GraphInterface<V, E> graph) {
		Writer writer = new PrintWriter(ops);
		Hashtable<V, Integer> map = new Hashtable<V, Integer>(graph.getOrder());
		int i = 1;
		try {
			for (V v : graph.getVertices()) {
				map.put(v, i);
				writer.write(i + " " + this.labeller.getLabel(v) + "\n");
				i++;

			}

			writer.write("#\n");

			for (E e : graph.getEdges()) {
				writer.write(map.get(e.getTail()) + " " + map.get(e.getHead())
						+ "\n");
			}
			writer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void exportGraph(GraphInterface<V, E> graph) {
		this.exportGraph(System.out, graph);
	}

}
