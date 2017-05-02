import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.gt4j.annas.graph.DefaultEdge;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.UndirectedGraph;
import org.gt4j.annas.graph.generate.RandomGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class Recognition {
	
	private GraphInterface<String, DefaultEdge> graph;
	private Condition1<String, DefaultEdge> cond1;
	private Condition2<String, DefaultEdge> cond2;
	private isChordal<String, DefaultEdge> chordal;
	private CliqueSeparator2<String, DefaultEdge> decomposition;
	private CliqueSeparator2<String, DefaultEdge>.DecompositionNode root;
	
		
	@Before
	public void setUp() throws Exception {

		graph = new UndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);

		this.decomposition = new CliqueSeparator2<String, DefaultEdge>(originalG());
	
	}
	
	@After
	public void tearDown() throws Exception {
	}
		
	@Test
	public void recogniseGraph() {
		GraphInterface<String, DefaultEdge> graph = makeGraph();
		
		this.decomposition = new CliqueSeparator2<String, DefaultEdge>(graph);
		this.cond1 = new Condition1<String, DefaultEdge>(graph);
		this.cond2 = new Condition2<String, DefaultEdge>(graph);
		this.chordal = new isChordal<String, DefaultEdge>(graph);
		
		ArrayList<ArrayList<String>> Atoms = new ArrayList<ArrayList<String>>();
		
		ArrayList<ArrayList<String>> Leaves = this.decomposition.construct(root, Atoms);
		
		if(Atoms.size()==1){
			System.out.println("could not find a clique cutset");
		}
		
		boolean inclass=true;
		
		for(ArrayList<String> Leaf : Leaves){
			if(!inClass(Leaf)){
				inclass=false;
			}System.out.println("Leaf: " + Leaf + ", in Class? " + inclass);
		}
		
		if(inclass){
			System.out.println("GRAPH IS IN CLASS C");
		}else{
			System.out.println("GRAPH IS NOT IN CLASS");
		}
		
	}
	
	public boolean inClass(List<String> Leaf){
		GraphInterface<String, DefaultEdge> Atom = getGraph(Leaf);
		if(!this.cond1.Satisfies1(Atom) && !this.cond2.Satisfies2(Atom) && !this.chordal.isChordalYes(Atom)){
			return false;
		}
		else{
			return true;
		}
	}
	
	public GraphInterface<String, DefaultEdge> getGraph(List<String> vertices){
		List<String> rems = new ArrayList<>(makeGraph().getVertices());
		rems.removeAll(vertices);
		GraphInterface<String, DefaultEdge> newGraph = makeGraph();
		newGraph.removeVertices(rems);
		return newGraph;
	}
	
	
	public GraphInterface<String, DefaultEdge> makeGraph(){
		GraphInterface<String, DefaultEdge> graph = wheel();
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
	
	public GraphInterface<String, DefaultEdge> chordal(){
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
		
		
		return graph;
	}
	
	public GraphInterface<String, DefaultEdge> theta(){
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
		e1 = graph.addEdge(a, g);
		e1 = graph.addEdge(a, h);
		e1 = graph.addEdge(b, c);
		e1 = graph.addEdge(c, d);
		e1 = graph.addEdge(f, g);
		e1 = graph.addEdge(f, e);
		e1 = graph.addEdge(d, e);
		e1 = graph.addEdge(h, i);
		e1 = graph.addEdge(i, j);
		e1 = graph.addEdge(j, d);
		
		
		
		return graph;
	}
	
	public GraphInterface<String, DefaultEdge> pyramid(){
		String a = "A";
		String b = "B";
		String c = "C";
		String d = "D";
		String e = "E";
		String f = "F";
		String g = "G";
		String h = "H";
		String i = "I";
		
				

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
		
		

		DefaultEdge e1 = graph.addEdge(a, b);
		e1 = graph.addEdge(a, g);
		e1 = graph.addEdge(a, h);
		e1 = graph.addEdge(b, c);
		e1 = graph.addEdge(c, d);
		e1 = graph.addEdge(d, e);
		e1 = graph.addEdge(d, i);
		e1 = graph.addEdge(i, e);
		e1 = graph.addEdge(h, i);
		e1 = graph.addEdge(e, f);
		e1 = graph.addEdge(f, g);
		
		
		
		return graph;
	}
	
	public GraphInterface<String, DefaultEdge> prism(){
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
		
		

		DefaultEdge e1 = graph.addEdge(a, b);
		e1 = graph.addEdge(a, c);
		e1 = graph.addEdge(a, d);
		e1 = graph.addEdge(b, c);
		e1 = graph.addEdge(b, j);
		e1 = graph.addEdge(c, k);
		e1 = graph.addEdge(d, e);
		e1 = graph.addEdge(e, f);
		e1 = graph.addEdge(f, g);
		e1 = graph.addEdge(f, h);
		e1 = graph.addEdge(g, h);
		e1 = graph.addEdge(g, k);
		e1 = graph.addEdge(h, i);
		e1 = graph.addEdge(i, j);
		
		
		
		return graph;
	}
	
	public GraphInterface<String, DefaultEdge> wheel(){
		String a = "A";
		String b = "B";
		String c = "C";
		String d = "D";
		String e = "E";
		String f = "F";
		String g = "G";
		String h = "H";
		
				

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
		
		

		DefaultEdge e1 = graph.addEdge(a, b);
		e1 = graph.addEdge(a, g);
		e1 = graph.addEdge(b, c);
		e1 = graph.addEdge(c, d);
		e1 = graph.addEdge(c, h);
		e1 = graph.addEdge(d, e);
		e1 = graph.addEdge(e, f);
		e1 = graph.addEdge(f, g);
		e1 = graph.addEdge(f, h);
		e1 = graph.addEdge(g, h);
		
		
		
		return graph;
	}
	
	public GraphInterface<String, DefaultEdge> complete6(){
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
		
				

		graph = new UndirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);

		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		
		

		DefaultEdge e1 = graph.addEdge(a, b);
		e1 = graph.addEdge(a, c);
		e1 = graph.addEdge(a, d);
		e1 = graph.addEdge(a, e);
		e1 = graph.addEdge(a, f);
		e1 = graph.addEdge(b, c);
		e1 = graph.addEdge(b, d);
		e1 = graph.addEdge(b, e);
		e1 = graph.addEdge(b, f);
		e1 = graph.addEdge(c, d);
		e1 = graph.addEdge(c, e);
		e1 = graph.addEdge(c, f);
		e1 = graph.addEdge(d, e);
		e1 = graph.addEdge(d, f);
		e1 = graph.addEdge(e, f);
		
		return graph;
	}
	
}
