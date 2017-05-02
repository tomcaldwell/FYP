import static org.junit.Assert.assertTrue;

import java.util.Collection;
import org.gt4j.annas.graph.DefaultEdge;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.UndirectedGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCond2 {
	
	private GraphInterface<String, DefaultEdge> graph;
	
	private Condition2<String, DefaultEdge> cond2;

	
	String a = "A";
	String b = "B";
	String c = "C";
	String d = "D";
	String e = "E";
	String f = "F";
	String g = "G";
	String h = "H";
	
	
	
	
	@Before
	public void setUp() throws Exception {

		graph = new UndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);

		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		//graph.addVertex(f);
		

		DefaultEdge e1 = graph.addEdge(a, d);
		e1 = graph.addEdge(a, e);
		e1 = graph.addEdge(a, c);
		e1 = graph.addEdge(b, d);
		e1 = graph.addEdge(b, e);
		e1 = graph.addEdge(b, c);
		e1 = graph.addEdge(c, d);
		e1 = graph.addEdge(c, e);
		//e1 = graph.addEdge(f, d);
		//e1 = graph.addEdge(f, e);
		//e1 = graph.addEdge(f, c);
		
		
		

	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void Test1() {
		this.cond2 = new Condition2<String, DefaultEdge>(this.graph);
		
		boolean connected = this.cond2.Satisfies2(this.graph);
		
		assertTrue(connected);
		
	}
}