package org.gt4j.annas.graph.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.gt4j.annas.graph.DefaultEdge;
import org.gt4j.annas.graph.GraphInterface;

/**
 * A very rough implementation, it should be rewritten to provide sensible error
 * messages.
 * 
 * @author Sam Wilson
 * 
 */
public class TGFImporter implements GraphImporter<String, DefaultEdge> {

	@Override
	public GraphInterface<String, DefaultEdge> importGraph(InputStream input,
			GraphInterface<String, DefaultEdge> graph) {

		BufferedReader in = new BufferedReader(new InputStreamReader(input));
		String line = null;

		ArrayList<String> vertices = new ArrayList<String>();
		boolean edges = false;
		try {
			while ((line = in.readLine()) != null) {
				if (line.charAt(0) == '#') {
					edges = true;
					continue;
				}

				if (!edges) {
					String str = line.substring(line.indexOf(" ")).trim();
					vertices.add(str);
					graph.addVertex(str);
				} else {
					String first = line.split(" ")[0].trim();
					String second = line.split(" ")[1].trim();
					graph.addEdge(vertices.get(Integer.parseInt(first) - 1),
							vertices.get(Integer.parseInt(second) - 1));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Incorrectly formatted tgf file");
			e.printStackTrace();
		}

		return graph;
	}

	@Override
	public GraphInterface<String, DefaultEdge> importGraph(String filename,
			GraphInterface<String, DefaultEdge> graph) {
		try {
			return this.importGraph(new FileInputStream(filename), graph);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return graph;
	}

}
