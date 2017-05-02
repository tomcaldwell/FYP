import java.util.ArrayList;
import java.util.List;

import org.gt4j.annas.graph.DefaultEdge;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.UndirectedGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testMinColour {
	
	private MinVertexColour<String, DefaultEdge> minColour;
	
	private GraphInterface<String, DefaultEdge> graph;
	
	@Before
	public void setUp() throws Exception {

		graph = new UndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);

		this.minColour = new MinVertexColour<String, DefaultEdge>(makeGraph());
	
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testColours(){
		
		
		System.out.println(this.minColour.getNumber());
	}
	
	public GraphInterface<String, DefaultEdge> makeGraph(){
		GraphInterface<String, DefaultEdge> graph = originalG();
		return graph;
	}
	
	public GraphInterface<String, DefaultEdge> Graph(){
		String a = "A";
		String b = "B";
		String c = "C";
		String d = "D";
		String e = "E";
		
		

		graph = new UndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);

		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		

		DefaultEdge e1 = graph.addEdge(a, b);
		e1 = graph.addEdge(a, c);
		e1 = graph.addEdge(b, c);
		e1 = graph.addEdge(c, d);
		e1 = graph.addEdge(b, d);
		e1 = graph.addEdge(e, d);
		e1 = graph.addEdge(e, b);
		e1 = graph.addEdge(e, c);
		
		
		return graph;
	}	
	
	public GraphInterface<String, DefaultEdge> originalG(){
		String a = "A";
		String b = "B";
		String c = "C";
		String d = "D";
		String e = "E";
		String f = "F";
		String g = "G";
		String h = "H";
		String i = "I";
		String j = "J";
		String k = "K";
		String l = "L";
		String m = "M";
		String n = "N";
		String o = "O";
		
		String p = "P";
		String q = "Q";
		String r = "R";
		

		graph = new UndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);

		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		graph.addVertex(g);
		graph.addVertex(h);
		graph.addVertex(i);
		graph.addVertex(j);
		graph.addVertex(k);
		graph.addVertex(l);
		graph.addVertex(m);
		graph.addVertex(n);
		graph.addVertex(o);		

		DefaultEdge e1 = graph.addEdge(a, b);
		e1 = graph.addEdge(a, c);
		e1 = graph.addEdge(b, c);
		e1 = graph.addEdge(c, d);
		e1 = graph.addEdge(c, e);
		e1 = graph.addEdge(d, e);
		e1 = graph.addEdge(d, f);
		e1 = graph.addEdge(e, h);
		e1 = graph.addEdge(f, g);
		e1 = graph.addEdge(h, g);
		e1 = graph.addEdge(h, i);
		e1 = graph.addEdge(g, i);
		e1 = graph.addEdge(h, j);
		e1 = graph.addEdge(j, i);
		e1 = graph.addEdge(j, k);
		e1 = graph.addEdge(j, l);
		e1 = graph.addEdge(j, m);
		e1 = graph.addEdge(j, n);
		e1 = graph.addEdge(o, n);
		e1 = graph.addEdge(o, m);
		e1 = graph.addEdge(n, l);
		e1 = graph.addEdge(n, m);
		e1 = graph.addEdge(n, k);
		e1 = graph.addEdge(k, m);
		e1 = graph.addEdge(k, l);
		e1 = graph.addEdge(l, m);
		
		
		return graph;
	}	
}
