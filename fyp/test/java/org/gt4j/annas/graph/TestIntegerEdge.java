package org.gt4j.annas.graph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestIntegerEdge {
	
	private IntegerEdge edge;

	@Test
	public void testDefaultEdge() {
		this.edge = new IntegerEdge();
		assertTrue(this.edge != null);
	}

	@Test
	public void testToString() {
		this.edge = new IntegerEdge();
		this.edge.setHead(1);
		this.edge.setTail(2);
		
		assertTrue(this.edge.toString().equals("2--1"));
	}

	@Test
	public void testGetHead() {
		this.edge = new IntegerEdge();
		assertTrue(this.edge.getHead() == null);
		this.edge.setHead(1);
		assertTrue(this.edge.getHead().equals(1));
	}

	@Test
	public void testSetHead() {
		this.edge = new IntegerEdge();
		assertTrue(this.edge.getHead()==null);
		this.edge.setHead(1);
		assertTrue(this.edge.getHead().equals(1));
	}

	@Test
	public void testGetTail() {
		this.edge = new IntegerEdge();
		assertTrue(this.edge.getTail() == null);
		this.edge.setTail(1);
		assertTrue(this.edge.getTail().equals(1));
	}

	@Test
	public void testSetTail() {
		this.edge = new IntegerEdge();
		assertTrue(this.edge.getTail() == null);
		this.edge.setTail(1);
		assertTrue(this.edge.getTail().equals(1));
	}

	@Test
	public void testIsIncident() {
		this.edge = new IntegerEdge();
		this.edge.setTail(1);
		this.edge.setHead(2);
		assertTrue(this.edge.isIncident(1));
		assertTrue(this.edge.isIncident(2));
		assertFalse(this.edge.isIncident(3));
	}

	@Test
	public void testGetOtherEndpoint() {
		this.edge = new IntegerEdge();
		this.edge.setTail(1);
		this.edge.setHead(2);
		assertTrue(this.edge.getOtherEndpoint(1)==2);
		assertTrue(this.edge.getOtherEndpoint(2)==1);
		assertTrue(this.edge.getOtherEndpoint(3)==null);
	}

}
