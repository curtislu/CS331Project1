/**
 * CS 331: Design and Analysis of Algorithms
 * Professor: G. S. Young
 *
 * Programming Assignment #1
 * 
 * Matrix class creates an n x n matrix with default values of all zeros
 * for the entries.
 * 
 * @author Curtis Lu
 */

public class Matrix {
	int matrix[][]; // N x N MATRIX
	int rows; // Number of rows in the matrix
	int columns; // Number of columns in the matrix
	
	/*
	 * Creates an N x N Matrix where 'size' = N
	 */
	public Matrix(int size)
	{
		this.rows = this.columns = size;
		matrix = new int[this.rows][this.columns];
	}
	
	/*
	 * Returns the index number for a selected row
	 */
	public int getRows() {
		return rows;
	}
	
	/*
	 * Returns the index number for a selected column
	 */
	public int getColumns() {
		return columns;
	}
	
	/*
	 * Displays the matrix
	 */
	public void print(Matrix a) {
		for(int i = 0; i < a.getRows(); i++) {
			for(int j = 0; j < a.getColumns(); j++) {
				
				System.out.print(a.matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
