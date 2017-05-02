package org.gt4j.annas.misc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.IntegerEdge;

/**
 * Reads a .g6 file and returns an iterator. The iterator reads from a file
 * instead of reading the file into memory.
 * 
 * @author Sam Wilson
 * 
 */
public class Graph6FileReader implements
		Iterable<GraphInterface<Integer, IntegerEdge>> {

	private String filename;

	/**
	 * Constructs a reader that reads from a given file.
	 * 
	 * @param filename
	 *            File name
	 */
	public Graph6FileReader(String filename) {
		super();
		this.filename = filename;

	}

	@Override
	public Iterator<GraphInterface<Integer, IntegerEdge>> iterator() {
		return new GraphIterator(this.filename);
	}

	class GraphIterator implements
			Iterator<GraphInterface<Integer, IntegerEdge>> {

		private BufferedReader in;

		private String filename;

		private String newLine;

		private boolean preread;

		public GraphIterator(String filename) {
			super();
			this.filename = filename;
			this.preread = false;
			try {
				this.in = new BufferedReader(new FileReader(filename));
			} catch (FileNotFoundException e) {
				throw new RuntimeException("File could not be found");
			}
		}

		@Override
		public boolean hasNext() {
			if (this.preread == true) {
				return true;
			}

			String line;
			try {
				line = this.in.readLine();
			} catch (IOException e) {
				return false;
			}
			if (line != null) {
				this.preread = true;
				this.newLine = line;
				return true;
			} else {
				return false;
			}
		}

		@Override
		public GraphInterface<Integer, IntegerEdge> next() {
			String line = null;
			if (this.preread != true) {
				try {
					line = this.in.readLine();

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				line = this.newLine;
				this.preread = false;
			}

			return Graph6.decodeGraph(line);
		}

		@Override
		public void remove() {
			throw new NoSuchMethodError();

		}

		@Override
		public String toString() {
			return "GraphIterator: Connected to " + this.filename;
		}
	}

	@Override
	public String toString() {
		return "Graph6FileReader: Connected to " + this.filename;
	}
}
