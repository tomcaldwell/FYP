package org.gt4j.annas.graph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMultiHashMap {

	private MultiHashMap<Integer, String> map;

	private String one, two, three, four, five;

	@Before
	public void setUp() throws Exception {
		this.map = new MultiHashMap<Integer, String>();
		this.one = "one";
		this.two = "two";
		this.three = "three";
		this.four = "four";
		this.five = "five";
	}

	@After
	public void tearDown() throws Exception {
		this.map = null;
	}

	@Test
	public void testClear() {
		this.map.put(1, one);
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		assertTrue(this.map.size()== 5);
		this.map.clear(); 
		assertTrue(this.map.size() == 0);
	}

	@Test
	public void testMultiHashMap() {
		this.map = new MultiHashMap<Integer, String>();
		assertNotNull(this.map);
	}

	@Test
	public void testMultiHashMapInt() {
		this.map = new MultiHashMap<Integer, String>(5);
		assertNotNull(this.map);
	}

	@Test
	public void testMultiHashMapIntFloat() {
		this.map = new MultiHashMap<Integer, String>(5,0.7f);
		assertNotNull(this.map);
		this.map = new MultiHashMap<>(5, 2f);
		assertNotNull(this.map);
	}

	@Test
	public void testContainsValueObject() {
		assertFalse(this.map.containsValue(five));
		this.map.put(1, one);
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		assertTrue(this.map.containsValue(five));
	}

	@Test
	public void testContainsValueObjectObject() {
		assertFalse(this.map.containsValue(1, one));
		this.map.put(1, one);
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		assertTrue(this.map.containsValue(1, one));
		assertFalse(this.map.containsValue(1, five));
	}
	
	@Test
	public void testGetCollection() {
		this.map.put(1, one);
		this.map.put(1, "1+0");
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		
		assertNull(this.map.getCollection(9));
		assertTrue(this.map.getCollection(1).size() ==2);
		assertTrue(this.map.getCollection(2).size() ==1);
		assertTrue(this.map.getCollection(3).size() ==1);
		assertTrue(this.map.getCollection(4).size() ==1);
		assertTrue(this.map.getCollection(5).size() ==1);
		assertTrue(this.map.getCollection(1).contains(one));
		assertTrue(this.map.getCollection(1).contains("1+0"));
		
	}

	@Test
	public void testPutObjectObject() {
		this.map.put(1, one);
		this.map.put(1, "1+0");
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		assertTrue(this.map.size() == 5);
		assertTrue(this.map.totalSize() == 6);
	}

	@Test
	public void testPutAllObjectCollection() {
		List<String> l = new ArrayList<String>(2);
		l.add(one);
		l.add("1+0");
		this.map.putAll(1, l);
		
		assertTrue(this.map.size()==1);
		assertTrue(this.map.totalSize()==2);
	}

	@Test
	public void testRemoveKV() {
		this.map.put(1, one);
		this.map.put(1, "1+0");
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		assertTrue(this.map.size(1)==2);
		
		assertNotNull(this.map.remove(1, one));
		assertTrue(this.map.size(1)==1);
		
		assertNotNull(this.map.remove(1, "1+0"));
		assertTrue(this.map.size(1)==0);
	}
	

	@Test
	public void testSizeObject() {
		this.map.put(1, one);
		this.map.put(1, "1+0");
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		assertTrue(this.map.size() == 5);
		assertTrue(this.map.totalSize() == 6);
		assertTrue(this.map.size(1)==2);
		assertTrue(this.map.size(2)==1);
	}

	@Test
	public void testTotalSize() {
		this.map.put(1, one);
		this.map.put(1, "1+0");
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		assertTrue(this.map.size() == 5);
		assertTrue(this.map.totalSize() == 6);
		this.map.put(1, one);
		assertTrue(this.map.totalSize() == 7);
		this.map.put(1, "2/2");
		assertTrue(this.map.totalSize() ==8);
	}


	@Test
	public void testSize() {
		this.map.put(1, one);
		this.map.put(1, "1+0");
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		assertTrue(this.map.size() == 5);
		this.map.remove(1);
		assertTrue(this.map.size() == 4);
		this.map.remove(2);
		assertTrue(this.map.size() == 3);
	}

	@Test
	public void testIsEmpty() {
		assertTrue(this.map.isEmpty());
		this.map.put(1, one);
		this.map.put(1, "1+0");
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		assertFalse(this.map.isEmpty());
	}


	@Test
	public void testContainsKey() {
		this.map.put(1, one);
		this.map.put(1, "1+0");
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		assertTrue(this.map.containsKey(1));
		assertFalse(this.map.containsKey(9));
	}

	@Test
	public void testRemoveObject() {
		this.map.put(1, one);
		this.map.put(1, "1+0");
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		assertTrue(this.map.size() == 5);
		assertTrue(this.map.totalSize() == 6);
		this.map.remove(1);
		assertTrue(this.map.size() == 4);
		assertTrue(this.map.totalSize() == 4);
		
	}

	@Test
	public void testKeySet() {
		this.map.put(1, one);
		this.map.put(1, "1+0");
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		
		assertTrue(this.map.keySet().contains(1));
		assertTrue(this.map.keySet().contains(2));
		assertTrue(this.map.keySet().contains(3));
		assertTrue(this.map.keySet().contains(4));
		assertTrue(this.map.keySet().contains(5));
		
		assertFalse(this.map.keySet().contains(6));
	}

	@Test
	public void testValues() {
		this.map.put(1, one);
		this.map.put(1, "1+0");
		this.map.put(2, two);
		this.map.put(3, three);
		this.map.put(4, four);
		this.map.put(5,five);
		
		assertTrue(this.map.values().contains(one));
		assertTrue(this.map.values().contains(two));
		assertTrue(this.map.values().contains(three));
		assertTrue(this.map.values().contains(four));
		assertTrue(this.map.values().contains(five));
		assertTrue(this.map.values().contains("1+0"));
	}
}
