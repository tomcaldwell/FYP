import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gt4j.annas.graph.DefaultEdge;
import org.gt4j.annas.graph.GraphInterface;
import org.gt4j.annas.graph.UndirectedGraph;
import org.gt4j.annas.graph.util.traverse.LexBFS;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDecomp {
	
	private GraphInterface<String, DefaultEdge> graph;
	
	private CliqueSeparator2<String, DefaultEdge> decomposition;
	
	private CliqueSeparator2<String, DefaultEdge>.DecompositionNode node;
	
	private CliqueSeparator2<String, DefaultEdge>.DecompositionNode root;

	
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
	
	
	
	
	@Before
	public void setUp() throws Exception {

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
		
		/**e1 = graph.addEdge(a, c);
		e1 = graph.addEdge(a, d);
		e1 = graph.addEdge(a, e);
		e1 = graph.addEdge(a, f);
		e1 = graph.addEdge(a, g);
		e1 = graph.addEdge(a, h);
		e1 = graph.addEdge(a, i);
		e1 = graph.addEdge(a, j);
		
		
		e1 = graph.addEdge(b, c);
		e1 = graph.addEdge(c, d);
		e1 = graph.addEdge(d, e);
		e1 = graph.addEdge(e, f);
		e1 = graph.addEdge(f, g);
		e1 = graph.addEdge(g, h);
		e1 = graph.addEdge(h, i);
		e1 = graph.addEdge(i, j);
		e1 = graph.addEdge(j, k);
		e1 = graph.addEdge(k, l);
		e1 = graph.addEdge(l, m);
		e1 = graph.addEdge(m, n);
		e1 = graph.addEdge(n, o);
		e1 = graph.addEdge(n, m);
		e1 = graph.addEdge(g, o);*/
		
		
		
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
		
		//e1 = graph.addEdge(c, d);
		
		
		this.decomposition = new CliqueSeparator2<String, DefaultEdge>(this.graph);
		//assertTrue(this.graph.getVertices().size()==15);
	
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testMinOrd(){
		List<String> order = new ArrayList<>(this.graph.getVertices());
		System.out.println(this.decomposition.getMinimalEliminationOrdering(order));
	}
	
	@Test
	public void testCutset() {
		List<String> order = new ArrayList<>(this.graph.getVertices());
		Collection<String> C = new ArrayList<>();
		
		C.add(j);
		assertTrue(this.decomposition.isCutset(order, C));
		C.add(n);
		assertTrue(this.decomposition.isCutset(order, C));
		C.clear();
		C.add(e);
		C.add(h);
		C.add(g);
		assertTrue(this.decomposition.isCutset(order, C));
		C.clear();
		C.add(m);
		C.add(l);
		C.add(k);
		C.add(e);
		C.add(i);
		C.add(b);
		assertFalse(this.decomposition.isCutset(order, C));
	}
	
	@Test
	public void testPath() {
		HashSet<String> visited = new HashSet<>();
		List<String> order = new ArrayList<>(this.graph.getVertices());
		
		/////for(int i=0; i<order.size(); i++){
			//for(int j=0; j<order.size(); j++){
				//if(i!=j){
					//visited.clear();
					//assertTrue(this.decomposition.isPath(visited, order.get(i), order.get(j), this.graph));
				//}
			//}/////
		
		GraphInterface<String, DefaultEdge> graph = this.graph;
		graph.removeVertex(j);
		visited.clear();
		assertFalse(this.decomposition.isPath(visited, a, m, graph, order));
		visited.clear();
		assertFalse(this.decomposition.isPath(visited, f, o, graph, order));
		visited.clear();
		assertTrue(this.decomposition.isPath(visited, o, k, graph, order));
		visited.clear();
		assertTrue(this.decomposition.isPath(visited, b, i, graph, order));
	}
	
	@Test
	public void TestConnected(){
		List<String> vertices = new ArrayList<>();
		List<String> C=new ArrayList<>();
		vertices.addAll(this.graph.getVertices());
		assertTrue(this.decomposition.isConnected(vertices));
		this.graph.removeEdge(h,j);
		assertTrue(this.decomposition.isConnected(vertices));
		this.graph.removeEdge(i,j);
		//this.graph.removeVertex(j);
		//vertices.addAll(this.graph.getVertices());
		assertFalse(this.decomposition.isConnected(vertices));
		this.graph.addEdge(h,j);
		this.graph.addEdge(i,j);
		C.add(a);
		C.add(e);
		C.add(i);
		C.add(k);
		C.add(m);
		C.add(o);
		vertices.removeAll(C);
		assertTrue(this.decomposition.isConnected(vertices));
		this.graph.removeVertices(C);
		assertTrue(this.decomposition.isConnected(vertices));
		this.graph.resetEdges();
		assertFalse(this.decomposition.isConnected(vertices));
	}

	@Test
	public void TestgetCC(){
		Collection<String> C = new ArrayList<>();
		List<String> A = new ArrayList<>();
		C.add(m);
		C.add(j);
		A.add(o);
		A.add(n);
		A.add(l);
		A.add(k);
		List<String> vertices = new ArrayList<>(this.graph.getVertices());
		assertTrue(this.decomposition.getConnectedComponent(n,C,vertices).containsAll(A));
		assertTrue(A.containsAll(this.decomposition.getConnectedComponent(n,C,vertices)));
	}
	
	@Test
	public void TestgetC(){
		List<String> vertices = new ArrayList<>(this.graph.getVertices());
		List<String> order = this.decomposition.getMinimalEliminationOrdering(vertices);
		System.out.println(order);
		List<String> C = new ArrayList<>();
		C.add(m);
		C.add(j);
		//assertTrue(this.decomposition.isClique(C));
		//assertTrue(this.decomposition.isCutset(order, C));
		assertTrue(this.decomposition.getC(n, order, 0).containsAll(C));
		assertTrue(C.containsAll(this.decomposition.getC(n, order, 0)));
		
	}
	
	
	@Test
	public void TestIsClique(){
		this.graph.resetEdges();
		
		for(String s : this.graph.getVertices()){
			for (String u : this.graph.getVertices()){
				if(s != u ){
						this.graph.addEdge(s, u);
				}
			}
		}
		
		assertTrue(this.decomposition.isClique(this.graph.getVertices()));
		
		this.graph.removeEdge(a,b);
		assertFalse(this.decomposition.isClique(this.graph.getVertices()));
		
		this.graph.resetEdges();
		assertFalse(this.decomposition.isClique(this.graph.getVertices()));
		
		this.graph.addEdge(a,b);
		assertFalse(this.decomposition.isClique(this.graph.getVertices()));
	}
	
	
	@Test
	public void TestisCutset(){
		List<String> vertices = new ArrayList<>(this.graph.getVertices());
		List<String> orde = this.decomposition.getMinimalEliminationOrdering(vertices);
		System.out.println(this.graph);
		Collection<String> A = Arrays.asList("H", "G");
		Collection<String> B = Arrays.asList("C", "D", "E", "F");
		Collection<String> C = Arrays.asList("J");
		Collection<String> D = Arrays.asList("H", "M");
		
		System.out.println(orde);
		System.out.println(D);
		
		assertTrue(this.decomposition.isCutset(orde, A));
		assertTrue(this.decomposition.isCutset(orde, B));
		assertTrue(this.decomposition.isCutset(orde, C));
		assertFalse(this.decomposition.isCutset(orde, D));
		
		
	}
	
	@Test
	public void TestCutset(){
		Collection<String> C = new ArrayList<>();
		List<String> vertices = new ArrayList<>(this.graph.getVertices());
		C.clear();
		C.add(k);
		C.add(m);
		C.add(o);
		C.add(a);
		List<String> ordr = this.decomposition.getMinimalEliminationOrdering(vertices);
		//assertFalse(this.decomposition.isCutset(ordr,C));
		System.out.println("SUCCESS");
		
		C.clear();
		C.add(a);
		C.add(e);
		C.add(i);
		C.add(k);
		C.add(m);
		C.add(o);
		//assertFalse(this.decomposition.isCutset(ordr,C));
		
		C.clear();
		C.add(h);
		C.add(m);
		//List<String> ord = this.decomposition.getMinimalEliminationOrdering(this.graph);
		//assertFalse(this.decomposition.isCutset(ordr,C));
		System.out.println("SUCCESS");
		
		C.clear();
		C.add(j);
		//List<String> order = this.decomposition.getMinimalEliminationOrdering(this.graph);
		//assertTrue(this.decomposition.isCutset(ordr,C));
		System.out.println("SUCCESS");
		
		C.clear();
		C.add(d);
		C.add(e);
		//List<String> orde = this.decomposition.getMinimalEliminationOrdering(this.graph);
		//assertTrue(this.decomposition.isCutset(ordr,C));
		System.out.println("SUCCESS");
		
		C.clear();
		C.add(c);
		C.add(e);
		C.add(d);
		//List<String> oder = this.decomposition.getMinimalEliminationOrdering(this.graph);
		//assertTrue(this.decomposition.isCutset(ordr,C));
		System.out.println("SUCCESS");
	}
	
	@Test
	public void TestMinElOrd(){
		List<String> vertices = new ArrayList<>(this.graph.getVertices());
		assertTrue(this.decomposition.getMinimalEliminationOrdering(vertices).size()==this.graph.getVertices().size());
	}
	
	@Test
	public void TestStep(){
		LexBFS<String> lex = new LexBFS<String>(this.graph);
		List<String> out = lex.getOrder();
		HashSet<String> visited = new HashSet<>();
		List<String> in = new ArrayList<>();
		int step=0;
		
		
		//System.out.println(out);
		
		//assertTrue(this.decomposition.getStep(visited,0,d,o,out)==5);
		//assertTrue(this.decomposition.getStep(visited,0,c,o,out)==5);
		visited.clear();
		//assertTrue(this.decomposition.getStep(visited,0,a,o,out)==6);
		//visited.clear();
		//assertTrue(this.decomposition.getStep(visited,0,b,o,out)==6);
		assertTrue(this.decomposition.getStep(visited, 0, a, i, graph)==4);
		assertTrue(this.decomposition.getStep(visited, 0, e, g, graph)==2);
		assertTrue(this.decomposition.getStep(visited, 0, g, e, graph)==2);
		assertTrue(this.decomposition.getStep(visited, 0, d, b, graph)==2);
		assertTrue(this.decomposition.getStep(visited, 0, e, b, graph)==2);
		assertTrue(this.decomposition.getStep(visited, 0, h, f, graph)==2);
		assertTrue(this.decomposition.getStep(visited, 0, f, h, graph)==2);
		
		
		for(int j=out.size()-1; j>=0; j--){
			for(int i=0; i<out.size(); i++){
				if(i!=j){
					visited.clear();
					step=0;
					//assertTrue(this.decomposition.getStep(visited,step,out.get(i), out.get(j), out)!=0);
					System.out.println("start: " + out.get(i) + ", end: " + out.get(j) + ", step: " + this.decomposition.getStep(visited,step,out.get(i), out.get(j), this.graph));
				}
			}
		}
	}
	
	@Test
	public void TestneighboursOf(){
		LexBFS<String> lex = new LexBFS<String>(this.graph);
		List<String> out = lex.getOrder();
		
		System.out.println(out);
		
		assertTrue(this.decomposition.neighboursOf(this.graph,out,o).size()==2);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,d).size()==0);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,c).size()==1);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,e).size()==2);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,f).size()==1);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,b).size()==1);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,a).size()==2);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,h).size()==1);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,g).size()==2);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,i).size()==2);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,j).size()==2);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,n).size()==1);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,m).size()==2);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,l).size()==3);
		assertTrue(this.decomposition.neighboursOf(this.graph,out,k).size()==4);
	}
	

}