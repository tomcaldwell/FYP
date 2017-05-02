package org.gt4j.annas.misc;

import java.util.Arrays;

import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.IntegerEdge;
import org.gt4j.annas.graph.SimpleUndirectedGraph;
import org.gt4j.annas.graph.util.Utilities;
import org.gt4j.annas.math.Matrix;

/**
 * Encodes and decodes graphs in graph6 format.
 * 
 * @author Sam Wilson
 * @version v1.1
 */
public class Graph6 {

	/**
	 * Given a string representing a graph in graph6 format this method returns
	 * a Simple Undirected graph.
	 * 
	 * @param entry
	 *            String representing a graph in graph6 format.
	 * @return the graph represented by the given string
	 */
	public static GraphInterface<Integer, IntegerEdge> decodeGraph(String entry) {
		SimpleUndirectedGraph<Integer, IntegerEdge> graph = new SimpleUndirectedGraph<>(
				IntegerEdge.class);
		if (entry.equals("?")) {
			return graph;
		}
		Matrix m = Graph6.decodeGraph(entry.getBytes());
		for (int h = 0; h < m.getMatrix().length; h++) {
			graph.addVertex(h);
		}

		for (int i = 0; i < m.getMatrix().length; i++) {
			for (int j = 0; j < i; j++) {
				if (m.getMatrix()[i][j] != 0) {
					graph.addEdge(i, j);
				}
			}
		}
		return graph;
	}

	/**
	 * This method given a graph produces a string representing the graph in
	 * graph6 file format.
	 * 
	 * @param graph graph to encode
	 * @return String representing the graph in graph6 file format.
	 */
	public static String encodeGraph(GraphInterface<?, ?> graph) {
		double[][] m = Utilities.getAdjacencyMatrix(graph).getMatrix();
		String adjmatrix = "";

		for (int i = 1; i < m.length; i++) {
			for (int j = 0; j < i; j++) {
				if (m[i][j] != 0) {
					adjmatrix += "1";
				} else {
					adjmatrix += "0";
				}
			}
		}

		return encodeGraph(graph.getOrder(), adjmatrix);
	}

	private static Matrix decodeGraph(byte[] i) {
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

	private static String encodeGraph(int NoNodes, String adjmatrix) {
		String rv = "";
		byte[] nn = encodeN(NoNodes);
		byte[] adj = encodeR(adjmatrix);
		byte[] res = new byte[nn.length + adj.length];
		System.arraycopy(nn, 0, res, 0, nn.length);
		System.arraycopy(adj, 0, res, nn.length, adj.length);
		for (int i = 0; i < res.length; i++) {
			rv = rv + (char) res[i];
		}
		return rv;
	}

	private static byte[] encodeN(long n) {
		if (n >= 0 && n <= 62) {
			return new byte[] { (byte) (n + 63) };
		} else if (63 <= n && n <= 258047) {
			byte[] ret = new byte[4];
			ret[0] = 126;
			byte[] r = encodeR(padLeft(Long.toString(n, 2), 18));

			for (int i = 1; i <= r.length; i++) {
				ret[i] = r[i - 1];
			}
			return ret;
		} else if (258048 <= n && n <= Integer.MAX_VALUE) {
			byte[] ret = new byte[8];
			ret[0] = ret[1] = 126;
			byte[] r = encodeR(padLeft(Long.toString(n, 2), 36));

			for (int i = 2; i <= r.length; i++) {
				ret[i] = r[i - 1];
			}
			return ret;
		}
		return null;
	}

	private static String padLeft(String str, int h) {
		String retval = "";
		for (int i = 0; i < h - str.length(); i++) {
			retval += "0";
		}
		return retval + str;
	}

	private static byte[] encodeR(String str) {
		str = padRight(str);
		byte[] r = split(str);
		return r;
	}

	private static byte[] split(String str) {
		int length = str.length() / 6;
		byte[] retval = new byte[length];
		for (int i = 0; i < length; i++) {
			retval[i] = (byte) (Byte.parseByte(
					str.substring(i * 6, (i * 6) + 6), 2) + 63);
		}

		return retval;
	}

	private static String padRight(String str) {
		int length = str.length();
		int pad = 6 - (length % 6);
		if (pad == 6) {
			return str;
		}
		for (int i = 0; i < pad; i++) {
			str += "0";
		}
		return str;
	}

	private static long decodeN(byte i[]) {
		if (i.length > 2 && i[0] == 126 && i[1] == 126) {
			return Long.parseLong(decodeR(new byte[] { i[2], i[3], i[4], i[5],
					i[6], i[7] }), 2);
		} else if (i.length > 1 && i[0] == 126) {
			return Long.parseLong(decodeR(new byte[] { i[1], i[2], i[3] }), 2);
		} else {
			return i[0] - 63;
		}
	}

	private static String decodeR(byte[] bytes) {
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

}
