package org.gt4j.annas.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestComplex {
	
	private static double delta = 0.05;

	@Test
	public void testComplex() {
		Complex c = new Complex(0,0);
		assertTrue(c != null);
	}

	@Test
	public void testReal() {
		Complex c = new Complex(1,2);
		assertTrue(c.real() == 1);
	}

	@Test
	public void testImag() {
		Complex c = new Complex(1,2);
		assertTrue(c.imag() == 2);
	}

	@Test
	public void testMod() {
		Complex c = new Complex(3,4);
		assertTrue(c.mod() == 5);
		
		Complex d = new Complex(0,0);
		assertTrue(d.mod()==0);
	}

	@Test
	public void testArg() {
		Complex c = new Complex(1,1);
		assertTrue(c.arg() == Math.PI/4);
	}

	@Test
	public void testConj() {
		Complex c = new Complex(1,2);
		Complex conj = new Complex(1,-2);

		assertTrue(c.conj().equals(conj));
	}

	@Test
	public void testPlus() {
		Complex c = new Complex(1,2);
		Complex d = new Complex(1,3);
		
		assertTrue(c.add(d).equals(new Complex(2, 5)));
	}

	@Test
	public void testMinus() {
		Complex c = new Complex(1,2);
		Complex d = new Complex(1,3);
		
		assertTrue(c.subtract(d).equals(new Complex(0, -1)));
		assertTrue(d.subtract(c).equals(new Complex(0, 1)));
	}

	@Test
	public void testTimes() {
		Complex c = new Complex(1,2);
		Complex d = new Complex(1,3);

		assertTrue(c.multiply(d).equals(new Complex(-5,5)));
	}

	@Test
	public void testDiv() {
		Complex c = new Complex(4,2);
		Complex d = new Complex(3,-1);

		assertTrue(c.divide(d).equals(new Complex(1,1)));
	}

	@Test
	public void testChs(){
		Complex c = new Complex(4,2);
		Complex d = new Complex(-4,-2);
		assertTrue(c.chs().equals(d));
	}
	
	@Test
	public void testExp(){
		Complex c = new Complex(4,2);
		Complex e = c.exp();
		assertEquals(-22.7, e.real(), delta);
		assertEquals(49.6, e.imag(), delta);
		
	}
	
	@Test
	public void testLog(){
		Complex c = new Complex(4,2);
		Complex d = c.log();
		assertEquals(1.49, d.real(), delta);
		assertEquals(0.46, d.imag(), delta);
	}
	@Test
	public void testSqrt() {
		Complex c = new Complex(4,2);
		Complex d = c.sqrt();
		assertEquals(2.05, d.real(),delta);
		assertEquals(0.48, d.imag(),delta);
		
	}

	@Test
	public void testSin() {
		Complex c = new Complex(4,2);
		Complex d = c.sin();
		assertEquals(-2.84, d.real(),delta);
		assertEquals(-2.37, d.imag(),delta);
	}

	@Test
	public void testCos() {
		Complex c = new Complex(4,2);
		Complex d = c.cos();
		assertEquals(-2.495, d.real(),delta);
		assertEquals(2.74, d.imag(),delta);
	}

	@Test
	public void testSinh() {
		Complex c = new Complex(4,2);
		Complex d = c.sinh();
		assertEquals(-11.35, d.real(),delta);
		assertEquals(24.83, d.imag(),delta);
	}

	@Test
	public void testCosh() {
		Complex c = new Complex(4,2);
		Complex d = c.cosh();
		assertEquals(-11.36, d.real(),delta);
		assertEquals(24.81, d.imag(),delta);
	}

	@Test
	public void testTan() {
		Complex c = new Complex(4,2);
		Complex d = c.tan();
		assertEquals(0.036, d.real(),delta);
		assertEquals(1.004, d.imag(),delta);
	}
	
	
	@Test
	public void testToString(){
		Complex c = new Complex(4,2);
		Complex d = new Complex(4,-2);
		Complex e = new Complex(4,0);
		Complex f = new Complex(0,-2);
		Complex g = new Complex(0,2);
		Complex h = new Complex(Double.NaN,Double.NaN);
		
		assertTrue(c.toString().equals("4.0+2.0i"));
		assertTrue(d.toString().equals("4.0-2.0i"));
		assertTrue(e.toString().equals("4.0"));
		assertTrue(f.toString().equals("-2.0i"));
		assertTrue(g.toString().equals("2.0i"));
		assertTrue(h.toString().equals(Double.NaN+"+i*"+ Double.NaN));
		
	}
	
	@Test
	public void testEquals(){
		Complex c = new Complex(4,2);
		Complex d = new Complex(4,2);
		Complex e = new Complex(0,0);
		Complex f = new Complex(3,2);
		
		assertTrue(c.equals(d));
		assertTrue(c.equals(c));
		assertFalse(c.equals(null));
		assertFalse(c.equals(9));
		assertFalse(c.equals(f));
		assertFalse(c.equals(e));
	}
}
