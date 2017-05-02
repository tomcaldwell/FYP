package org.gt4j.annas.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;

import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.IntegerEdge;
import org.gt4j.annas.graph.UndirectedGraph;
import org.gt4j.annas.math.Matrix;

/**
 * Iterator that iterates graphs from a {@link java.sql.ResultSet} provided by
 * {@link org.gt4j.annas.misc.GraphDatabase}
 * 
 * @author Sam Wilson
 */
public class GraphIterator implements
		Iterator<GraphInterface<Integer, IntegerEdge>> {

	private ResultSet rs;

	/**
	 * Constructs a graph iterator from a given ResultSet
	 * 
	 * @param rs ResultSet
	 */
	public GraphIterator(ResultSet rs) {
		super();
		this.rs = rs;
	}

	@Override
	public boolean hasNext() {
		try {
			return this.rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public GraphInterface<Integer, IntegerEdge> next() {
		try {
			byte[] b = this.rs.getBytes("sgraph");
			String str = new String(b);
			return unpackgraph(str);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	private GraphInterface<Integer, IntegerEdge> unpackgraph(String sgraph) {
		return Fromg6.fromg6(sgraph);
	}

	private static class Fromg6 {

		public static GraphInterface<Integer, IntegerEdge> fromg6(String str) {
			Matrix m = decodeGraph(StringToIntArray(str));

			GraphInterface<Integer, IntegerEdge> graph = new UndirectedGraph<Integer, IntegerEdge>(
					IntegerEdge.class);

			for (int i = 0; i < m.getMatrix().length; i++) {
				graph.addVertex(i);
			}

			double[][] ma = m.getMatrix();
			for (int i = 0; i < ma.length; i++) {
				for (int j = 0; j < ma.length; j++) {
					if (ma[i][j] == 1) {
						graph.addEdge(i, j);
					}
				}
			}

			return graph;
		}

		private static Matrix decodeGraph(int[] i) {
			long nuNodes = decodeN(i);
			String a = "";
			if (nuNodes <= 62) {
				a = decodeR(Arrays.copyOfRange(i, 1, i.length));
			} else if (nuNodes > 62 && nuNodes <= 258047) {
				a = decodeR(Arrays.copyOfRange(i, 4, i.length));
			} else {
				a = decodeR(Arrays.copyOfRange(i, 8, i.length));
			}

			int[][] adj = new int[(int) nuNodes][(int) nuNodes];

			int q = 0;
			for (int w = 0; w < nuNodes; w++) {
				for (int e = 0; e < w; e++) {
					adj[w][e] = Integer.parseInt((a.charAt(q)) + "");
					q++;
				}
			}

			return new Matrix(adj);
		}

		private static long decodeN(int i[]) {
			if (i.length > 2 && i[0] == 126 && i[1] == 126) {
				return Long.parseLong(decodeR(new int[] { i[2], i[3], i[4],
						i[5], i[6], i[7] }), 2);
			} else if (i.length > 1 && i[0] == 126) {
				return Long.parseLong(decodeR(new int[] { i[1], i[2], i[3] }),
						2);
			} else {
				return i[0] - 63;
			}
		}

		private static String decodeR(int[] bytes) {
			String retval = "";
			for (int i = 0; i < bytes.length; i++) {
				retval += padL(Integer.toBinaryString(bytes[i] - 63), 6);
			}
			return retval;
		}

		private static String padL(String str, int h) {
			String retval = "";
			for (int i = 0; i < h - str.length(); i++) {
				retval += "0";
			}
			return retval + str;
		}

		private static int[] StringToIntArray(String str) {
			int[] v = new int[str.length()];
			for (int l = 0; l < str.length(); l++) {
				v[l] = str.charAt(l);
			}
			return v;
		}

	}
}
