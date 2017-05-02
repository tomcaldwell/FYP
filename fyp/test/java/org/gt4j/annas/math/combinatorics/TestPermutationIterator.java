package org.gt4j.annas.math.combinatorics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class TestPermutationIterator {

	private Iterator<List<Integer>> pi;

	private Collection<Integer> groundset;

	private PermutationGenerator<Integer> pg;

	@Before
	public void setUp() throws Exception {
		this.groundset = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++)
			this.groundset.add(i);

		this.pg = new PermutationGenerator<Integer>(this.groundset);
		this.pi = this.pg.iterator();
	}

	@Test
	public void testPermutationIterator() {
		assertTrue(this.pi != null);
	}

	@Test
	public void testHasNext() {
		assertTrue(this.pi.hasNext());
		
		for(int i = 0; i <120; i++)
			assertTrue(this.pi.next()!= null);
		
		assertFalse(this.pi.hasNext());
	}

	@Test
	public void testNext() {
		assertTrue(this.pi.hasNext());
		
		for(int i = 0; i <120; i++)
			assertTrue(this.pi.next() != null);
		
		try{
			this.pi.next();
		}catch (NoSuchElementException e){
			assertTrue(true);
		}
	}

	@Test
	public void testRemove() {
		try{
			this.pi.remove();
		}catch (UnsupportedOperationException e){
			assertTrue(true);
		}
	}

}
