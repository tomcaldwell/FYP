package org.gt4j.annas.math;

/**
 * Calculates the optimal parameterization for matrix chain product. Implements
 * the Dynamic programming algorithm outlined in Introduction to algorithms (MIT
 * press)
 * 
 * @author Sam Wilson
 */
public class MatrixChainProduct {

	/**
	 * Matrices (in order)
	 */
	private Matrix[] matrices;

	/**
	 * Minimum number of multiplications to multiply each subsequence
	 */
	private int[][] m;

	/**
	 * How to parenthesis the sequence of matrices.
	 */
	private int[][] s;

	/**
	 * Finds the optimal solution for multiplying the sequence of matrices.
	 * 
	 * @param p
	 *            array of matrices
	 */
	public MatrixChainProduct(Matrix[] p) {
		super();
		this.matrices = p;
		int[] i = new int[p.length + 1];
		i[0] = p[0].getMatrix().length;
		for (int j = 0; j < p.length; j++) {
			i[j + 1] = p[j].getMatrix()[0].length;
		}
		matrixChainOrder(i);
	}


	private void matrixChainOrder(int... p) {
		int n = p.length - 1;
		this.m = new int[n][n];
		this.s = new int[n][n];
		for (int i = 0; i < n; i++) {
			this.m[i] = new int[n];
			this.m[i][i] = 0;
			this.s[i] = new int[n];
		}
		for (int ii = 1; ii < n; ii++) {
			for (int i = 0; i < n - ii; i++) {
				int j = i + ii;
				this.m[i][j] = Integer.MAX_VALUE;
				for (int k = i; k < j; k++) {
					int q = this.m[i][k] + this.m[k + 1][j] + p[i] * p[k + 1]
							* p[j + 1];
					if (q < this.m[i][j]) {
						this.m[i][j] = q;
						this.s[i][j] = k;
					}
				}
			}
		}
		System.out.println(toString());

	}

	/**
	 * Computes the product of the sequence of matrices.
	 * 
	 * @return product of the sequence
	 */
	public Matrix multiplyChain() {
		return mmo(this.s, 0, this.s.length - 1);
	}

	private Matrix mmo(int[][] s, int i, int j) {
		if (i == j) {
			return this.matrices[i];
		}
		Matrix a = mmo(s, i, s[i][j]);
		Matrix b = mmo(s, s[i][j] + 1, j);
		try {
			return a.multiply(b);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}

}
