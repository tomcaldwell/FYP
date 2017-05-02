package org.gt4j.annas.math;

/**
 * Immutable class representing the complex number type.
 * 
 * @author Sam Wilson
 * 
 */
public class Complex {

	/**
	 * Complex number representing 1 + 0i
	 */
	public static Complex ONE;

	/**
	 * Complex number representing 0 + 0i
	 */
	public static Complex ZERO;

	/**
	 * Complex number representing 0 + i
	 */
	public static Complex I;

	/**
	 * Complex number representing Double.NaN + Double.NaN * i
	 */
	public static Complex NaN;

	/**
	 * Complex number representing 1 + 0i
	 */
	public static Complex INF;

	static {
		Complex.ONE = new Complex(1, 0);
		Complex.ZERO = new Complex(0, 0);
		Complex.I = new Complex(0, 1);
		Complex.NaN = new Complex(Double.NaN, Double.NaN);
		Complex.ONE = new Complex(Double.POSITIVE_INFINITY,
				Double.POSITIVE_INFINITY);

	}

	/**
	 * Real part
	 */
	private double r;

	/**
	 * Imaginary part
	 */
	private double i;

	/**
	 * Constructs the complex number z = u + i*v
	 * 
	 * @param u real part
	 * @param v imaginary part
	 */
	public Complex(double u, double v) {
		this.r = u;
		this.i = v;
	}

	/**
	 * Real part of this Complex number (the x-coordinate in rectangular
	 * coordinates).
	 * 
	 * @return real part of the complex number
	 */
	public double real() {
		return this.r;
	}

	/**
	 * Imaginary part of this Complex number (the y-coordinate in rectangular
	 * coordinates).
	 * 
	 * @return Im[z] where z is this Complex number.
	 */
	public double imag() {
		return this.i;
	}

	/**
	 * Modulus of this Complex number (the distance from the origin in polar
	 * coordinates).
	 * 
	 * @return |z| where z is this Complex number.
	 */
	public double mod() {
		if (this.r != 0 || this.i != 0) {
			return Math.sqrt(this.r * this.r + this.i * this.i);
		} else {
			return 0d;
		}
	}

	/**
	 * Argument of this Complex number (the angle in radians with the x-axis in
	 * polar coordinates).
	 * 
	 * @return arg(z) where z is this Complex number.
	 */
	public double arg() {
		return Math.atan2(this.i, this.r);
	}

	/**
	 * Complex conjugate of this Complex number (the conjugate of x+i*y is
	 * x-i*y).
	 * 
	 * @return z-bar where z is this Complex number.
	 */
	public Complex conj() {
		return new Complex(this.r, -this.i);
	}

	/**
	 * Addition of Complex numbers (doesn't change this Complex number). <br>
	 * (x+i*y) + (s+i*t) = (x+s)+i*(y+t).
	 * 
	 * @param w
	 *            is the number to add.
	 * @return z+w where z is this Complex number.
	 */
	public Complex add(Complex w) {
		return new Complex(this.r + w.real(), this.i + w.imag());
	}

	/**
	 * Subtraction of Complex numbers (doesn't change this Complex number). <br>
	 * (x+i*y) - (s+i*t) = (x-s)+i*(y-t).
	 * 
	 * @param w
	 *            is the number to subtract.
	 * @return z-w where z is this Complex number.
	 */
	public Complex subtract(Complex w) {
		return new Complex(this.r - w.real(), this.i - w.imag());
	}

	/**
	 * Complex multiplication (doesn't change this Complex number).
	 * 
	 * @param w
	 *            is the number to multiply by.
	 * @return z*w where z is this Complex number.
	 */
	public Complex multiply(Complex w) {
		return new Complex(this.r * w.real() - this.i * w.imag(), this.r
				* w.imag() + this.i * w.real());
	}

	/**
	 * Division of Complex numbers (doesn't change this Complex number). <br>
	 * (x+i*y)/(s+i*t) = ((x*s+y*t) + i*(y*s-y*t)) / (s^2+t^2)
	 * 
	 * @param w
	 *            is the number to divide by
	 * @return new Complex number z/w where z is this Complex number
	 */
	public Complex divide(Complex w) {
		double den = w.real() * w.real() + w.imag() * w.imag();
		return new Complex((this.r * w.real() + this.i * w.imag()) / den,
				(this.i * w.real() - this.r * w.imag()) / den);
	}

	/**
	 * Complex exponential (doesn't change this Complex number).
	 * 
	 * @return exp(z) where z is this Complex number.
	 */
	public Complex exp() {
		return new Complex(Math.exp(this.r) * Math.cos(this.i),
				Math.exp(this.r) * Math.sin(this.i));
	}

	/**
	 * Principal branch of the Complex logarithm of this Complex number.
	 * (doesn't change this Complex number). The principal branch is the branch
	 * with -pi &lt; arg &lt;= pi.
	 * 
	 * @return log(z) where z is this Complex number.
	 */
	public Complex log() {
		return new Complex(Math.log(this.mod()), this.arg());
	}

	/**
	 * Complex square root (doesn't change this complex number). Computes the
	 * principal branch of the square root, which is the value with 0 &lt;= arg &lt;=
	 * pi.
	 * 
	 * @return sqrt(z) where z is this Complex number.
	 */
	public Complex sqrt() {
		double r = Math.sqrt(this.mod());
		double theta = this.arg() / 2;
		return new Complex(r * Math.cos(theta), r * Math.sin(theta));
	}

	// Real cosh function (used to compute complex trig functions)
	private double cosh(double theta) {
		return (Math.exp(theta) + Math.exp(-theta)) / 2;
	}

	// Real sinh function (used to compute complex trig functions)
	private double sinh(double theta) {
		return (Math.exp(theta) - Math.exp(-theta)) / 2;
	}

	/**
	 * Sine of this Complex number (doesn't change this Complex number). <br>
	 * sin(z) = (exp(i*z)-exp(-i*z))/(2*i).
	 * 
	 * @return sin(z) where z is this Complex number.
	 */
	public Complex sin() {
		return new Complex(cosh(this.i) * Math.sin(this.r), sinh(this.i)
				* Math.cos(this.r));
	}

	/**
	 * Cosine of this Complex number (doesn't change this Complex number). <br>
	 * cos(z) = (exp(i*z)+exp(-i*z))/ 2.
	 * 
	 * @return cos(z) where z is this Complex number.
	 */
	public Complex cos() {
		return new Complex(cosh(this.i) * Math.cos(this.r), -sinh(this.i)
				* Math.sin(this.r));
	}

	/**
	 * Hyperbolic sine of this Complex number (doesn't change this Complex
	 * number). <br>
	 * sinh(z) = (exp(z)-exp(-z))/2.
	 * 
	 * @return sinh(z) where z is this Complex number.
	 */
	public Complex sinh() {
		return new Complex(sinh(this.r) * Math.cos(this.i), cosh(this.r)
				* Math.sin(this.i));
	}

	/**
	 * Hyperbolic cosine of this Complex number (doesn't change this Complex
	 * number). <br>
	 * cosh(z) = (exp(z) + exp(-z)) / 2.
	 * 
	 * @return cosh(z) where z is this Complex number.
	 */
	public Complex cosh() {
		return new Complex(cosh(this.r) * Math.cos(this.i), sinh(this.r)
				* Math.sin(this.i));
	}

	/**
	 * Tangent of this Complex number (doesn't change this Complex number). <br>
	 * tan(z) = sin(z)/cos(z).
	 * 
	 * @return tan(z) where z is this Complex number.
	 */
	public Complex tan() {
		return (this.sin()).divide(this.cos());
	}

	/**
	 * Negative of this complex number (chs stands for change sign). This
	 * produces a new Complex number and doesn't change this Complex number. <br>
	 * -(x+i*y) = -x-i*y.
	 * 
	 * @return -z where z is this Complex number.
	 */
	public Complex chs() {
		return new Complex(-this.r, -this.i);
	}

	/**
	 * String representation of this Complex number.
	 * 
	 * @return x+i*y, x-i*y, x, or i*y as appropriate.
	 */
	@Override
	public String toString() {
		if (this.r != 0 && this.i > 0) {
			return this.r + "+" + this.i + "i";
		}
		if (this.r != 0 && this.i < 0) {
			return this.r + "-" + (-this.i) + "i";
		}
		if (this.i == 0) {
			return String.valueOf(this.r);
		}
		if (this.r == 0) {
			return this.i + "i";
		}
		// shouldn't get here (unless Inf or NaN)
		return this.r + "+i*" + this.i;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.i);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.r);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(this.i) != Double.doubleToLongBits(other.i))
			return false;
		if (Double.doubleToLongBits(this.r) != Double.doubleToLongBits(other.r))
			return false;
		return true;
	}
}
