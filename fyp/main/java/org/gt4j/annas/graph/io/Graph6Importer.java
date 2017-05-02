package org.gt4j.annas.graph.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.IntegerEdge;
import org.gt4j.annas.misc.Graph6;

/**
 * 
 * @author Sam Wilson
 * 
 */
public class Graph6Importer implements GraphImporter<Integer, IntegerEdge> {

	@Override
	public GraphInterface<Integer, IntegerEdge> importGraph(InputStream input,
			GraphInterface<Integer, IntegerEdge> graph) {
		BufferedReader in = new BufferedReader(new InputStreamReader(input));
		try {
			String line = in.readLine();
			GraphInterface<Integer, IntegerEdge> g = Graph6.decodeGraph(line);

			graph.addVertices(g.getVertices());
			for (IntegerEdge e : g.getEdges()) {
				graph.addEdge(e.getTail(), e.getHead());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return graph;
	}

	@Override
	public GraphInterface<Integer, IntegerEdge> importGraph(String filename,
			GraphInterface<Integer, IntegerEdge> graph) {

		try {
			return this.importGraph(new FileInputStream(filename), graph);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return graph;
	}

}
