/**
 * CS 331: Design and Analysis of Algorithms
 * Professor: G. S. Young
 *
 * Programming Assignment #1
 *
 * <description-of-assignment>
 *	This assignment is to demonstrate the runtime complexities of three
 *  matrix multiplication algorithms: classic, divide and conquer,
 *  and Strassen's method and compare the results. 
 *  
 * @author Curtis Lu
 */
import java.util.Random;

public class MatrixMultiplicationDemo {

  /*
   * Main method that runs the application.
   */
   public static void main (String[] args) {
      int size = 4096;
      long start, end;
      Matrix a = new Matrix(size);
      Matrix b = new Matrix(size);
      Matrix c = new Matrix(size);
      MatrixMultiplication m = new MatrixMultiplication();
      fill(a);
      fill(b);
		
      start = System.nanoTime();
      c = m.classicalMultiple(a, b);
      end = System.nanoTime();
      System.out.println("Classical Runtime		  : " + (end - start));
		
      c = new Matrix(size);
      start = System.nanoTime();
      c = m.divideAndConquer(a, b);
      end = System.nanoTime();
      System.out.println("Divide And Conquer Runtime	  : " + (end - start));
      c = new Matrix(size);
      start = System.nanoTime();
      c = m.strassen(a, b);
      end = System.nanoTime();
      System.out.println("Strassen's Runtime		  : " + (end - start));
   }
	
  /*
   * This is a helper method that takes one parameter Matrix a.
   * This method fills the matrix with values ranging from 1-100
   */
   public static void fill(Matrix a) {
      Random generator = new Random();
      for(int i = 0; i < a.getRows(); i++) {
         for(int j = 0; j < a.getColumns(); j++) {
            a.matrix[i][j] = generator.nextInt(100) + 1;
         }
      }
   }
}
