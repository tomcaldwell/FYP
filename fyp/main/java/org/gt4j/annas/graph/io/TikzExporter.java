package org.gt4j.annas.graph.io;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.gt4j.annas.graph.EdgeInterface;
import org.gt4j.annas.graph.GraphInterface;

/**
 * Quick method to produce Tikz output for a graph. The format is suitable for
 * the PGF/Tikz latex package to layout.
 * 
 * @author scsswi
 *
 * @param <V>
 * @param <E>
 */
public class TikzExporter<V, E extends EdgeInterface<V>> implements
		GraphExporter<V, E> {

	@Override
	public void exportGraph(OutputStream writer, GraphInterface<V, E> graph) {
		new PrintWriter(writer, true).println(toTikz(graph));

	}

	@Override
	public void exportGraph(GraphInterface<V, E> graph) {
		System.out.println(toTikz(graph));

	}

	private String toTikz(GraphInterface<V, E> graph) {
		String str = "\\tikz \\graph [ spring layout, radius=1cm , nodes={draw,circle}] {";
		for (E e : graph.getEdges()) {
			str += e.getHead() + "--" + e.getTail() + ";\n";
		}
		str += "};";

		return str;
	}
}
