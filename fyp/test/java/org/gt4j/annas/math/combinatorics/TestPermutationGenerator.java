package org.gt4j.annas.math.combinatorics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestPermutationGenerator {

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
	public void testPermutationGenerator() {
		assertNotNull(this.pg);
	}

	@Test
	public void testGetOriginalVector() {
		assertEquals(this.groundset, pg.getOriginalCollection());
	}

	@Test
	public void testGetNumberOfGeneratedObjects() {
		assertEquals(this.pg.getNumberOfGeneratedObjects(), 120);
		this.pg = new PermutationGenerator<Integer>(new ArrayList<Integer>());
		assertEquals(0, this.pg.getNumberOfGeneratedObjects());
	}

	@Test
	public void testIterator() {
		assertNotNull(this.pi);
	}

}
