package org.gt4j.annas.math.combinatorics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class TestPowersetIterator {
	
	private PowersetIterator<Integer> pst;
	
	private Collection<Integer> groundset;
	
	@Before
	public void setUp() throws Exception{
		this.groundset = new ArrayList<Integer>();
		for(int i=0; i <5; i++)
			this.groundset.add(i);
	}

	@Test
	public void testPowersetIteratorCollectionOfT() {
		this.pst = new PowersetIterator<Integer>(this.groundset);
		assertTrue(this.pst != null);
		assertTrue(this.pst.next().size() ==0);
	}

	@Test
	public void testPowersetIteratorCollectionOfTBoolean() {
		this.pst = new PowersetIterator<Integer>(this.groundset,false);
		assertTrue(this.pst != null);
		assertTrue(this.pst.next().size() ==0);
		
		this.pst = new PowersetIterator<Integer>(this.groundset,true);
		assertTrue(this.pst != null);
		assertTrue(this.pst.next().size() ==5);
	}

	@Test
	public void testHasNext() {
		this.pst = new PowersetIterator<Integer>(this.groundset);
		assertTrue(this.pst.hasNext());
		for(int i = 0 ; i < 32; i++){
			assertTrue(this.pst.next() != null);
		}
		assertFalse(this.pst.hasNext());
	}

	@Test
	public void testNext() {
		this.pst = new PowersetIterator<Integer>(this.groundset);
		assertTrue(this.pst.hasNext());
		for(int i = 0 ; i < 32; i++){
			assertTrue(this.pst.next() != null);
		}
		try{
			this.pst.next();
		} catch (NoSuchElementException e){
			assertTrue(true);
		}
	}

	@Test
	public void testRemove() {
		this.pst = new PowersetIterator<Integer>(this.groundset);
		try{
			this.pst.remove();
		} catch (UnsupportedOperationException e){
			assertTrue(true);
		}
	}

}
