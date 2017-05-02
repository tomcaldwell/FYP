package org.gt4j.annas.math;

import java.text.DecimalFormat;

/**
 * Class that represents the Matrix type
 * 
 * @author Sam Wilson
 * 
 */
public class Matrix {

	/**
	 * Enables debug (prints a lot to stdout)
	 */
	private static final boolean DEBUG = false;

	private int iDF;

	/**
	 * Representation of matrix
	 */
	private double[][] matrix;

	/**
	 * Creates a square matrix of size x size.
	 * 
	 * @param size
	 *            of matrix
	 */
	public Matrix(int size) {
		this.matrix = new double[size][size]; 
	}

	/**
	 * Creates a matrix of sizeY x sizeX
	 * 
	 * @param sizeX
	 *            Number of Columns
	 * @param sizeY
	 *            Number of rows
	 */
	public Matrix(int sizeX, int sizeY) {
		this.matrix = new double[sizeY][sizeX];
	}

	/**
	 * Create a matrix from an array. This constructor copies the values of the
	 * array.
	 * 
	 * @param m double array
	 */
	public Matrix(double[][] m) {
		try {
			int w = m[0].length;
			for (double[] d : m) {
				if (d.length != w) {
					this.matrix = new double[0][0];
				}
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			this.matrix = new double[0][0];
		}
		if (this.matrix == null) {
			this.matrix = new double[m.length][m[0].length];
			if (m.length == this.matrix.length
					&& m[0].length == this.matrix[0].length) {

				for (int i = 0; i < m.length; i++) {
					for (int j = 0; j < m[0].length; j++) {
						this.matrix[i][j] = m[i][j];
					}
				}

			}
		}
	}

	/**
	 * Create a matrix from an array. This constructor copies the values of the
	 * array.
	 * 
	 * @param m integer array
	 */
	public Matrix(int[][] m) {

		this.matrix = new double[m.length][m[0].length];
		if (m.length == this.matrix.length
				&& m[0].length == this.matrix[0].length) {

			for (int i = 0; i < m.length; i++) {
				for (int j = 0; j < m[0].length; j++) {
					this.matrix[i][j] = m[i][j];
				}
			}

		}
	}

	/**
	 * Sets an element of the matrix
	 * 
	 * @param r
	 *            row
	 * @param c
	 *            column
	 * @param value
	 *            value
	 */
	public void setElement(int r, int c, double value) {
		this.matrix[r][c] = value;
	}

	/**
	 * Gets an element of the matrix
	 * 
	 * @param r
	 *            row
	 * @param c
	 *            column
	 * @return value
	 */
	public double getElement(int r, int c) {
		return this.matrix[r][c];
	}

	/**
	 * Set the values of the matrix from a double array. This method check the
	 * size of the array.
	 * 
	 * @param m double matrix
	 */
	public void setMatrix(double[][] m) {
		if (m.length == this.matrix.length
				&& m[0].length == this.matrix[0].length) {
			this.matrix = m;
		}
	}

	/**
	 * Gets the 2 dimensional array representing the matrix.
	 * 
	 * @return the underlying two dimensional double array.
	 */
	public double[][] getMatrix() {
		return this.matrix;
	}

	/**
	 * Creates the identity matrix of size i.
	 * 
	 * @param i
	 *            Size of matrix.
	 * @return Identity matrix.
	 */
	public static Matrix createIdentity(int i) {
		double[][] g = new double[i][i];
		for (int j = 0; j < i; j++) {
			for (int k = 0; k < i; k++) {
				if (j == k) {

					g[j][k] = 1;
				} else {
					g[j][k] = 0;
				}
			}
		}
		return new Matrix(g);
	}

	/**
	 * Multiplies two matrices.
	 * 
	 * @param B matrix
	 * @return The result of this x B
	 * @throws IllegalArgumentException Caused if the given matrix is not compatible for matrix multiplication
	 */
	public Matrix multiply(Matrix B) {
		double[][] b = B.getMatrix();
		double[][] a = this.matrix;
		if (a[0].length != b.length)
			throw new IllegalArgumentException(
					"Matrices incompatible for multiplication");
		double matrix[][] = new double[a.length][b[0].length];

		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < b[i].length; j++)
				matrix[i][j] = 0;

		// cycle through answer matrix
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = calculateRowColumnProduct(a, i, b, j);

			}
		}

		return new Matrix(matrix);

	}

	/**
	 * Raises the matrix to a power, A^3 = A x A x A.
	 * 
	 * @param power power to raise the matrix too
	 * @return result of calculation
	 */
	public Matrix pow(int power) {

		Matrix retval = new Matrix(this.matrix);
		for (int i = 1; i < power; i++) {
			try {
				retval = retval.multiply(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return retval;
	}

	private double calculateRowColumnProduct(double[][] A, int row,
			double[][] B, int col) {
		double product = 0;
		for (int i = 0; i < A[row].length; i++)
			product += A[row][i] * B[i][col];
		return product;
	}

	// --------------------------------------------------------------

	/**
	 * Performs the transposition of the matrix.
	 * 
	 * @return transpose of the matrix
	 */
	public Matrix getTranspose() {
		return new Matrix(this.transpose(this.matrix));
	}

	private double[][] transpose(double[][] a) {

		debug("Performing Transpose...");

		double m[][] = new double[a[0].length][a.length];

		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a[i].length; j++)
				m[j][i] = a[i][j];
		return m;
	}

	/**
	 * Computes the inverse of a matrix. Formula used to Calculate Inverse: a
	 * inv(A) = 1/det(A) * adj(A)
	 * 
	 * @return inverse of this matrix.
	 * @throws RuntimeException if the matrix is not invertible
	 */
	public Matrix getInverse() {
		double[][] a = this.matrix;

		debug("Performing Inverse...");

		int tms = a.length;

		double m[][] = new double[tms][tms];
		double mm[][] = adjoint(a);

		double det = determinant(a);
		double dd = 0;

		if (det == 0) {

			debug("Determinant Equals 0, Not Invertible.");
			throw new RuntimeException("not invertible");

		} else {
			dd = 1 / det;
		}

		for (int i = 0; i < tms; i++)
			for (int j = 0; j < tms; j++) {
				m[i][j] = dd * mm[i][j];
			}

		return new Matrix(m);
	}

	/**
	 * Computes the adjoint of the matrix. Wikipedia's article of <a
	 * href="http://en.wikipedia.org/wiki/Adjugate_matrix">adjugate matrix</a>.
	 * 
	 * @return adjoint of the matrix
	 * 
	 */
	public Matrix getAdjoint() {
		return new Matrix(this.adjoint(this.matrix));
	}

	private double[][] adjoint(double[][] a) {

		debug("Performing Adjoint...");

		int tms = a.length;

		double m[][] = new double[tms][tms];

		int ii, jj, ia, ja;
		double det;

		for (int i = 0; i < tms; i++)
			for (int j = 0; j < tms; j++) {
				ia = ja = 0;

				double ap[][] = new double[tms - 1][tms - 1];

				for (ii = 0; ii < tms; ii++) {
					for (jj = 0; jj < tms; jj++) {

						if ((ii != i) && (jj != j)) {
							ap[ia][ja] = a[ii][jj];
							ja++;
						}

					}
					if ((ii != i) && (jj != j)) {
						ia++;
					}
					ja = 0;
				}

				det = determinant(ap);
				m[i][j] = Math.pow(-1, i + j) * det;
			}

		m = transpose(m);

		return m;
	}

	/**
	 * Computing the UpperTriangle of the matrix.
	 * 
	 * @return Upper triangle of the matrix
	 */
	public Matrix getUpperTriangle() {
		return new Matrix(this.upperTriangle(this.matrix));
	}

	private double[][] upperTriangle(double[][] m) {

		debug("Converting to Upper Triangle...");

		double f1 = 0;
		double temp = 0;
		int tms = m.length; // get This Matrix Size (could be smaller than
		// global)
		int v = 1;

		this.iDF = 1;

		for (int col = 0; col < tms - 1; col++) {
			for (int row = col + 1; row < tms; row++) {
				v = 1;

				outahere: while (m[col][col] == 0) {
					if (col + v >= tms) {
						this.iDF = 0;
						break outahere;
					} else {
						for (int c = 0; c < tms; c++) {
							temp = m[col][c];
							m[col][c] = m[col + v][c]; // switch rows
							m[col + v][c] = temp;
						}
						v++; // count row switchs
						this.iDF = this.iDF * -1; // each switch changes
													// determinant
						// factor
					}
				}

				if (m[col][col] != 0) {

					debug("tms = " + tms + "   col = " + col + "   row = "
							+ row);

					f1 = (-1) * m[row][col] / m[col][col];
					for (int i = col; i < tms; i++) {
						m[row][i] = f1 * m[col][i] + m[row][i];
					}

				}

			}
		}

		return m;
	}

	/**
	 * Computes the determinant of the matrix.
	 * 
	 * @return the determinant
	 */
	public double getDeterminant() {
		return this.determinant(this.matrix);
	}

	private double determinant(double[][] matrix) {

		debug("Getting Determinant...");

		int tms = matrix.length;

		double det = 1;

		matrix = upperTriangle(matrix);

		for (int i = 0; i < tms; i++) {
			det = det * matrix[i][i];
		} // multiply down diagonal

		det = det * this.iDF; // adjust w/ determinant factor

		debug("Determinant: " + det);

		return det;
	}

	/**
	 * Adds this matrix to matrix m
	 * 
	 * @param m
	 *            operand
	 * @return result of adding the given matrix to this matrix
	 */
	public Matrix addMatrix(Matrix m) {
		if ((m.matrix.length == this.matrix.length)
				&& (m.matrix[0].length == this.matrix[0].length)) {
			Matrix retval = new Matrix(this.matrix.length);
			for (int i = 0; i < this.matrix[0].length; i++) {
				for (int j = 0; j < this.matrix.length; j++) {
					retval.matrix[i][j] = this.matrix[i][j] + m.matrix[i][j];
				}
			}
			return retval;
		} else {
			throw new IllegalArgumentException("Incompatible size matrix");
		}

	}

	/**
	 * Subtracts m from this matrix.
	 * 
	 * @param m
	 *            Operand
	 * @return result of subtracting the given matrix from this matrix
	 */
	public Matrix subtractMatrix(Matrix m) {
		if ((m.matrix.length == this.matrix.length)
				&& (m.matrix[0].length == this.matrix[0].length)) {
			Matrix retval = new Matrix(this.matrix.length);
			for (int i = 0; i < this.matrix[0].length; i++) {
				for (int j = 0; j < this.matrix.length; j++) {
					retval.matrix[i][j] = this.matrix[i][j] - m.matrix[i][j];
				}
			}
			return retval;
		} else {
			throw new IllegalArgumentException("Incompatible size matrix");
		}

	}

	/**
	 * Computes the trace of the matrix.
	 * 
	 * @return trace of matrix
	 */
	public float getTrace() {
		float retval = 0;

		if (this.matrix.length == this.matrix[0].length) {

			for (int i = 0; i < this.matrix.length; i++) {
				retval += this.matrix[i][i];
			}
		} else {
			throw new RuntimeException("Matrix must be square");
		}
		return retval;
	}

	/**
	 * Pseudo-division. Inverse(this) * j
	 * 
	 * @param j
	 *            operand
	 * @return result of pseudo-division
	 */
	public Matrix divide(Matrix j) {
		try {
			Matrix mi = this.getInverse();
			mi = mi.multiply(j);
			return mi;
		} catch (Exception e) {
			throw new RuntimeException("Invalid Matrix operation.");
		}
	}

	/**
	 * Performs a deep equals
	 * 
	 * @param B matrix
	 * @return true if the matrices are equal with 0 tolerance
	 */
	public boolean eq(Matrix B) {
		return this.eq(B, 0.0);
	}

	/**
	 * Performs a deep equals
	 * 
	 * @param B matrix
	 * @param tolerance tolerance
	 * @return true if the matrices are equal up to a tolerance.
	 */
	public boolean eq(Matrix B, double tolerance) {
		Matrix A = this;
		if (B.getMatrix().length != getMatrix().length
				|| B.getMatrix()[0].length != getMatrix()[0].length)
			throw new RuntimeException("Illegal matrix dimensions.");
		for (int i = 0; i < this.getMatrix().length; i++)
			for (int j = 0; j < this.getMatrix()[0].length; j++)
				if (Math.abs(A.getMatrix()[i][j] - B.getMatrix()[i][j]) > tolerance)
					return false;
		return true;
	}

	/**
	 * Zero's the diagonal
	 */
	public void zerodiag() {
		if (this.matrix.length == this.matrix[0].length) {
			for (int i = 0; i < this.matrix.length; i++) {
				this.matrix[i][i] = 0;
			}
		}
	}

	/**
	 * Computes the multiplication of the array of matrices.
	 * 
	 * @param m array of matrices
	 * @return result of computation
	 */
	public static Matrix multiplyChain(Matrix... m) {
		MatrixChainProduct moo = new MatrixChainProduct(m);
		return moo.multiplyChain();
	}

	/**
	 * Pretty prints the matrix to stdout.
	 */
	public void print() {
		System.out.println(this.toString());
	}

	/**
	 * Pretty print the matrix to stdout.
	 * 
	 * @param Pattern
	 *            a Formatter for the double value.
	 */
	public void printformatted(String Pattern) {
		DecimalFormat Formatter = new DecimalFormat(Pattern);

		for (int i = 0; i < this.matrix.length; i++) {
			System.out.print('{');
			for (int j = 0; j < this.matrix[0].length; j++) {
				if (j < this.matrix[0].length - 1) {
					System.out
							.print(' ' + Formatter.format(this.matrix[i][j]) + ',');
				} else {
					System.out.print(' ' + Formatter.format(this.matrix[i][j]));
				}

			}
			System.out.println(" }");

		}

	}

	@Override
	public String toString() {

		String retval = "";
		for (int i = 0; i < this.matrix.length; i++) {
			retval = retval + "{ ";
			for (int j = 0; j < this.matrix[0].length; j++) {
				if (j < this.matrix[0].length - 1) {
					retval = retval + (" " + this.matrix[i][j] + ", ");
				} else {
					retval = retval + (" " + this.matrix[i][j]);
				}

			}
			retval = retval + (" }\n");

		}
		return retval;
	}

	/**
	 * Prints a debug message to stdout
	 * 
	 * @param str
	 */
	private void debug(String str) {
		if (DEBUG) {
			System.out.println(str);
		}
	}
}
