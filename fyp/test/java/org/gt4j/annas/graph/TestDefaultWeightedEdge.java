package org.gt4j.annas.graph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestDefaultWeightedEdge {

	private DefaultWeightedEdge<String> edge;

	@Test
	public void testDefaultWeightedEdge() {
		this.edge = new DefaultWeightedEdge<String>();
		assertTrue(this.edge != null);
	}

	@Test
	public void testToString() {
		this.edge = new DefaultWeightedEdge<String>();
		this.edge.setHead("a");
		this.edge.setTail("b");
		assertTrue(this.edge.toString().equals("b-[0.0]->a"));
	}

	@Test
	public void testGetHead() {
		this.edge = new DefaultWeightedEdge<String>();
		assertTrue(this.edge.getHead() == null);
		this.edge.setHead("a");
		assertTrue(this.edge.getHead().equals("a"));
	}

	@Test
	public void testSetHead() {
		this.edge = new DefaultWeightedEdge<String>();
		assertTrue(this.edge.getHead() == null);
		this.edge.setHead("a");
		assertTrue(this.edge.getHead().equals("a"));
	}

	@Test
	public void testGetTail() {
		this.edge = new DefaultWeightedEdge<String>();
		assertTrue(this.edge.getTail() == null);
		this.edge.setTail("a");
		assertTrue(this.edge.getTail().equals("a"));
	}

	@Test
	public void testSetTail() {
		this.edge = new DefaultWeightedEdge<String>();
		assertTrue(this.edge.getTail() == null);
		this.edge.setTail("a");
		assertTrue(this.edge.getTail().equals("a"));
	}

	@Test
	public void testSetWeight() {
		this.edge = new DefaultWeightedEdge<String>();
		assertTrue(this.edge.getWeight() == 0.0);
		this.edge.setWeight(1.2);
		assertTrue(this.edge.getWeight() == 1.2);
	}

	@Test
	public void testIsIncident() {
		this.edge = new DefaultWeightedEdge<String>();
		this.edge.setTail("a");
		this.edge.setHead("b");
		assertTrue(this.edge.isIncident("a"));
		assertTrue(this.edge.isIncident("b"));
		assertFalse(this.edge.isIncident("c"));
	}

	@Test
	public void testGetOtherEndpoint() {
		this.edge = new DefaultWeightedEdge<String>();
		this.edge.setTail("a");
		this.edge.setHead("b");
		assertTrue(this.edge.getOtherEndpoint("a") == "b");
		assertTrue(this.edge.getOtherEndpoint("b") == "a");
		assertTrue(this.edge.getOtherEndpoint("c") == null);
	}

}
