import static org.junit.Assert.assertTrue;

import org.gt4j.annas.graph.DefaultEdge;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.UndirectedGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testChordal {
	private GraphInterface<String, DefaultEdge> graph;
	
	private isChordal<String, DefaultEdge> chordal;
	
	@Before
	public void setUp() throws Exception {

		
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
		

		DefaultEdge e1 = graph.addEdge(a, b);
		e1 = graph.addEdge(a, d);
		e1 = graph.addEdge(b, c);
		e1 = graph.addEdge(b, d);
		e1 = graph.addEdge(c, d);
		e1 = graph.addEdge(c, e);
		e1 = graph.addEdge(c, f);
		e1 = graph.addEdge(d, e);
		e1 = graph.addEdge(e, f);
		e1 = graph.addEdge(e, g);
		e1 = graph.addEdge(f, g);
		e1 = graph.addEdge(g, i);
		e1 = graph.addEdge(g, h);
		e1 = graph.addEdge(g, j);
		e1 = graph.addEdge(h, i);
		e1 = graph.addEdge(h, j);
		e1 = graph.addEdge(i, j);
		
	}
	
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void Test2() {
		this.chordal = new isChordal<String, DefaultEdge>(this.graph);
		
		boolean checkChord = this.chordal.isChordalYes(this.graph);
		
		assertTrue(checkChord == true);
		System.out.println("Graph satisfies Condition 1!");
	}
	
}
