/**
 * CS 331: Design and Analysis of Algorithms
 * Professor: G. S. Young
 *
 * Programming Assignment #1
 * 
 * MatrixMultiplication class provides the three approaches to matrix
 * multiplication assigned in this project. The three methods are: 
 * classical, divide and conquer, and Strassen's method.
 * 
 * @author Curtis Lu
 */

public class MatrixMultiplication {
  /*
   * Computes matrix multiplication the classical method as how we learned it
   * from school. Takes two parameters Matrix a and Matrix b that will return
   * a Matrix c the product of a x b.
   */
   public Matrix classicalMultiple(Matrix a, Matrix b) {
      if(a.getColumns() != b.getRows()) {
         throw new RuntimeException(" Number of columns in Matrix 1 " +
                                    "does not equal number of rows in Matrix 2");
      } else {
         Matrix c = new Matrix(a.getRows());
         for(int i = 0; i < a.getRows(); i++) {
            for(int j = 0; j < b.getColumns(); j++) {
               for(int k = 0; k < a.getColumns(); k++) {
                  c.matrix[i][j] = c.matrix[i][j] + a.matrix[i][k] *b.matrix[k][j];
               }
            }
         }
         return c;
      }
   }
	
  /*
   * Computes matrix multiplication the divide and conquer method by using recursion.
   * Takes two parameters Matrix a and Matrix b that will return
   * a Matrix c the product of a x b.
   */
   public Matrix divideAndConquer(Matrix a, Matrix b) {
      Matrix c = new Matrix(a.getRows());
      if (a.getRows() == 1) {
         c = classicalMultiple(a, b);
      } else {
         int half = a.getRows() / 2;
		
         Matrix a11 = new Matrix(half);
         Matrix a12 = new Matrix(half);
         Matrix a21 = new Matrix(half);
         Matrix a22 = new Matrix(half);
         fillSubMatrix(a, a11, a12, a21, a22, half);
			
         Matrix b11 = new Matrix(half);
         Matrix b12 = new Matrix(half);
         Matrix b21 = new Matrix(half);
         Matrix b22 = new Matrix(half);
         fillSubMatrix(b, b11, b12, b21, b22, half);
			
         Matrix sub1 = divideAndConquer(a11, b11);
         Matrix sub2 = divideAndConquer(a12, b21);
         Matrix sub3 = divideAndConquer(a11, b12);
         Matrix sub4 = divideAndConquer(a12, b22);
         Matrix sub5 = divideAndConquer(a21, b11);
         Matrix sub6 = divideAndConquer(a22, b21);
         Matrix sub7 = divideAndConquer(a21, b12);
         Matrix sub8 = divideAndConquer(a22, b22);
         Matrix c11 = addMatrix(sub1, sub2);
         Matrix c12 = addMatrix(sub3, sub4);
         Matrix c21 = addMatrix(sub5, sub6);
         Matrix c22 = addMatrix(sub7, sub8);
			
         copyMatrix(c11, c, c11.getRows(), 0, 0);
         copyMatrix(c12, c, c12.getRows(), 0, half);
         copyMatrix(c21, c, c21.getRows(), half, 0);
         copyMatrix(c22, c, c22.getRows(), half, half);
      }
      return c;
   }
	
  /*
   * Computes matrix multiplication using Strassen's method.
   * Takes two parameters Matrix a and Matrix b that will return
   * a Matrix c the product of a x b.
   */
   public Matrix strassen(Matrix a, Matrix b) {
      Matrix c = new Matrix(a.getRows());
      if (a.getRows() == 1) {
         c = classicalMultiple(a, b);
      } else {
         int half = a.getRows() / 2;
			
         Matrix a11 = new Matrix(half);
         Matrix a12 = new Matrix(half);
         Matrix a21 = new Matrix(half);
         Matrix a22 = new Matrix(half);
         fillSubMatrix(a, a11, a12, a21, a22, half);
			
         Matrix b11 = new Matrix(half);
         Matrix b12 = new Matrix(half);
         Matrix b21 = new Matrix(half);
         Matrix b22 = new Matrix(half);
         fillSubMatrix(b, b11, b12, b21, b22, half);
			
         Matrix p = strassen(addMatrix(a11,a22), addMatrix(b11,b22));
         Matrix q = strassen(addMatrix(a21,a22), b11);
         Matrix r = strassen(a11, subtractMatrix(b12, b22));
         Matrix s = strassen(a22, subtractMatrix(b21, b11));
         Matrix t = strassen(addMatrix(a11,a12), b22);
         Matrix u = strassen(subtractMatrix(a21, a11), addMatrix(b11, b12));
         Matrix v = strassen(subtractMatrix(a12, a22), addMatrix(b21, b22));
			
         Matrix c11 = addMatrix(subtractMatrix(addMatrix(p, s), t), v);
         Matrix c12 = addMatrix(r, t);
         Matrix c21 = addMatrix(q, s);
         Matrix c22 = addMatrix(subtractMatrix(addMatrix(p, r), q), u);
			
         copyMatrix(c11, c, c11.getRows(), 0, 0);
         copyMatrix(c12, c, c12.getRows(), 0, half);
         copyMatrix(c21, c, c21.getRows(), half, 0);
         copyMatrix(c22, c, c22.getRows(), half, half);
      }
      return c;
   }
	
  /*
   * Helper method for Divide and Conquer and Strassen's method.
   * Takes two parameters Matrix a and Matrix b and returns Matrix c
   * the sum of the two matrices.
   */
   public Matrix addMatrix(Matrix a, Matrix b) {
      Matrix c = new Matrix(a.getRows());
      for(int i = 0; i < a.getRows(); i++) {
         for(int j = 0; j < b.getColumns(); j++) {
            c.matrix[i][j] = a.matrix[i][j] + b.matrix[i][j];
         }
      }
      return c;
   }
	
  /*
   * Helper method for Divide and Conquer and Strassen's method.
   * Takes two parameters Matrix a and Matrix b and returns Matrix c
   * the difference of the two matrices.
   */
   public Matrix subtractMatrix(Matrix a, Matrix b) {
      Matrix c = new Matrix(a.getRows());
      for(int i = 0; i < a.getRows(); i++) {
         for(int j = 0; j < b.getColumns(); j++) {
            c.matrix[i][j] = a.matrix[i][j] - b.matrix[i][j];
         }
      }
      return c;
   }
	
  /*
   * Helper method for Divide and Conquer and Strassen's method.
   * Takes five parameters Matrix original, Matrix copied, in size,
   *  int offSetOriginal, int offSetCopied. This method helps copy
   *  the sub matrices back to the orginial matrix.
   */
   public void copyMatrix(Matrix original, Matrix copied, int size, int offSetOriginal, int offSetCopied) {
      for(int i = 0, i2 = offSetOriginal; i < size ; i++, i2++) {
         for(int j = 0, j2 = offSetCopied; j < size; j++, j2++) {
            copied.matrix[i2][j2] = original.matrix[i][j];
         }
      }
   }
	
  /*
   * Helper method for Divide and Conquer and Strassen's method.
   * Takes six parameters Matrix a, Matrix a11, Matrix a12, Matrix a21,
   *  Matrix a22, int size. Matrix a is the original matrix, 
   *  while a11, a12, a21, and a22 are the entries that make up a specific
   *  part of matrix a. Size represents Matrix a's size. This method
   *  copies the corresponding values from Matrix a to its sub matrices.
   */
   public void fillSubMatrix(Matrix a, Matrix a11, Matrix a12, Matrix a21, Matrix a22, int size) {
      for (int i = 0; i < size; i++) {
         for(int j = 0; j < size; j++) {
            a11.matrix[i][j] = a.matrix[i][j];
            a12.matrix[i][j] = a.matrix[i][j + size];
            a21.matrix[i][j] = a.matrix[i + size][j];
            a22.matrix[i][j] = a.matrix[i + size][j + size];
         }
      }
   }
}
