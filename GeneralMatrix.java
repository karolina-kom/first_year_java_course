/*
 * PROJECT III: GeneralMatrix.java
 *
 * This file contains a template for the class GeneralMatrix. Not all methods
 * are implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Matrix class.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

import java.util.Arrays;

public class GeneralMatrix extends Matrix {

    // Getter function for n
    public int getN() {
        return n;
    }

    // Getter function for m
    public int getM() {
        return m;
    }

    /**
     * This instance variable stores the elements of the matrix.
     */
    private double[][] data;

    /**
     * Constructor function: should initialise m and n through the Matrix
     * constructor and set up the data array.
     *
     * @param m  The first dimension of the array.
     * @param n  The second dimension of the array.
     */
    public GeneralMatrix(int m, int n) throws MatrixException {

        super(m, n); // Initialise m and n through the Matrix constructor

        // Check that dimensions of m and n are valid - otherwise throw an exception

        if (m < 1 || n < 1) {
            throw new MatrixException("Invalid Matrix Dimensions");
        }

        // Initialise a new array of dimension m x n

        data = new double[m][n];
    }

    /**
     * Constructor function. This is a copy constructor; it should create a
     * copy of the matrix A.
     *
     * @param A  The matrix to create a copy of.
     */
    public GeneralMatrix(GeneralMatrix A) {

        super(A.m , A.n); // Initialise m and n through Matrix constructor

        data = new double[A.m][A.n]; // Initialise data as an array of dimension m x n

        // Loop through each entry of original matrix and copy to matrix A

        for (int i = 0; i < A.m; i++) {

            for (int j = 0; j < A.n; j++) {

                data[i][j] = A.getIJ(i,j);

            }
        }
    }

    /**
     * Getter function: return the (i,j)'th entry of the matrix.
     *
     * @param i  The location in the first co-ordinate.
     * @param j  The location in the second co-ordinate.
     * @return   The (i,j)'th entry of the matrix.
     */
    public double getIJ(int i, int j) {

        // Check that i and j are valid choices for position in matrix - otherwise throw exception

        if( i >= 0 && j >= 0 && i < m && j < n) {

            return data[i][j]; // Return ith jth element of matrix

        } else {

            throw new MatrixException("Invalid choice for i or j.");
        }
    }

    /**
     * Setter function: set the (i,j)'th entry of the data array.
     *
     * @param i    The location in the first co-ordinate.
     * @param j    The location in the second co-ordinate.
     * @param val  The value to set the (i,j)'th entry to.
     */
    public void setIJ(int i, int j, double val) {

        // Check that i and j are valid choices for position in matrix - otherwise throw exception

        if( i >= 0 && j >= 0 && i < m && j < n) {

            data[i][j] = val; // Set ith jth element of matrix

        } else {

            throw new MatrixException("Invalid entry for i or j.");
        }
    }

    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {

        // Check that matrix is square = otherwise through exception

        if( this.m == this.n) {

            double[] d = new double[1]; // Set up array of length 1

            double det = 1;

            GeneralMatrix LU = this.decomp(d); // Pass d to LU - on exit d will contain 1 or -1

            // Det(LU) is equal to the product of the diagonal entries of LU
            // Loop through LU and find product of these entries

            for(int i = 0; i < this.n; i++) {

                det = det * LU.getIJ(i,i);

            }

            // Multiply the determinant by the 0th element of d

            det = det * d[0];

            // Return determinant

            return det;

        } else {

            throw new MatrixException("Not a square matrix - cannot calculate determinant.");

        }
    }

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public Matrix add(Matrix A) {

        // Check that the matrices have the same size - otherwise throw an exception

        if (this.n == A.getN() && this.m == A.getM()) {

            // Generate a new general matrix which will be returned

            GeneralMatrix newMatrix = new GeneralMatrix(this.m, this.n);

            // Loop through each element of the matrices and add the corresponding elements together

            for (int i = 0; i < this.m; i++){

                for (int j = 0; j < this.n; j++) {

                    newMatrix.setIJ( i, j, (A.getIJ(i,j) + this.getIJ(i,j)));
                }
            }

            return newMatrix;

        } else {

            throw new MatrixException("Matrices are not of the same dimensions - cannot add matrices together.");
        }
    }

    /**
     * Multiply the matrix by another matrix A. This is a _left_ product,
     * i.e. if this matrix is called B then it calculates the product BA.
     *
     * @param A  The Matrix to multiply by.
     * @return   The product of this matrix with the matrix A.
     */
    public Matrix multiply(Matrix A) {

        // Set up double - used to calculate the sum of each row

        double  rowSum;

        // Check whether the number of columns in this is equal to the number of rows in A
        // Otherwise - throws exception

        if (this.n == A.getM()) {

            // Set up a new GeneralMatrix - size number of rows of this and number of columns of A

            GeneralMatrix newMatrix = new GeneralMatrix(this.m, A.getN());

            // Loop through the rows of this

            for (int r = 0; r < this.m; r++) {

                // Loop through the columns of A

                for ( int c = 0; c < A.getN(); c++) {

                    rowSum = 0;

                    // Loop through elements in row of A - multiple each pair of elements together

                    for ( int i = 0; i < this.n; i++) {

                        rowSum = rowSum + (this.getIJ(r,i) * A.getIJ(i,c));

                    }

                    // Set the total to the corresponding element in newMatrix

                    newMatrix.setIJ(r,c,rowSum);
                }
            }

            // Returns the newMatrix - consists of the product of this and A

            return newMatrix;

        } else {

            throw new MatrixException("Invalid matrix dimensions - cannot multiply matrices together.");
        }
    }

    /**
     * Multiply the matrix by a scalar.
     *
     * @param a  The scalar to multiply the matrix by.
     * @return   The product of this matrix with the scalar a.
     */
    public Matrix multiply(double a) {

        // Set up new GeneralMatrix of size m and n

        GeneralMatrix newMatrix = new GeneralMatrix(this.m, this.n);

        // Loop through elements of the matrix and multiply each element by a scalar

        for (int i = 0; i < this.m; i++) {

            for (int j = 0; j < this.n; j ++) {

                newMatrix.setIJ(i,j,getIJ(i,j) * a);

            }
        }

        return newMatrix;
    }


    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {

        // Loop through each element of this matrix
        // Set each element to a random value between 0 and 1

        for (int i = 0; i < this.m; i++) {

            for (int j = 0; j < this.n; j++) {

                this.setIJ(i,j,Math.random());
            }
        }
    }

    /**
     * Returns the LU decomposition of this matrix; i.e. two matrices L and U
     * so that A = LU, where L is lower-diagonal and U is upper-diagonal.
     *
     * On exit, decomp returns the two matrices in a single matrix by packing
     * both matrices as follows:
     *
     * [ u_11 u_12 u_13 u_14 ]
     * [ l_21 u_22 u_23 u_24 ]
     * [ l_31 l_32 u_33 u_34 ]
     * [ l_41 l_42 l_43 l_44 ]
     *
     * where u_ij are the elements of U and l_ij are the elements of l. When
     * calculating the determinant you will need to multiply by the value of
     * d[0] calculated by the function.
     *
     * If the matrix is singular, then the routine throws a MatrixException.
     *
     * This method is an adaptation of the one found in the book "Numerical
     * Recipies in C" (see online for more details).
     *
     * @param d  An array of length 1. On exit, the value contained in here
     *           will either be 1 or -1, which you can use to calculate the
     *           correct sign on the determinant.
     * @return   The LU decomposition of the matrix.
     */
    public GeneralMatrix decomp(double[] d) {
        // This method is complete. You should not even attempt to change it!!
        if (n != m)
            throw new MatrixException("Matrix is not square");
        if (d.length != 1)
            throw new MatrixException("d should be of length 1");

        int           i, imax = -10, j, k;
        double        big, dum, sum, temp;
        double[]      vv   = new double[n];
        GeneralMatrix a    = new GeneralMatrix(this);

        d[0] = 1.0;

        for (i = 1; i <= n; i++) {
            big = 0.0;
            for (j = 1; j <= n; j++)
                if ((temp = Math.abs(a.data[i-1][j-1])) > big)
                    big = temp;
            if (big == 0.0)
                throw new MatrixException("Matrix is singular");
            vv[i-1] = 1.0/big;
        }

        for (j = 1; j <= n; j++) {
            for (i = 1; i < j; i++) {
                sum = a.data[i-1][j-1];
                for (k = 1; k < i; k++)
                    sum -= a.data[i-1][k-1]*a.data[k-1][j-1];
                a.data[i-1][j-1] = sum;
            }
            big = 0.0;
            for (i = j; i <= n; i++) {
                sum = a.data[i-1][j-1];
                for (k = 1; k < j; k++)
                    sum -= a.data[i-1][k-1]*a.data[k-1][j-1];
                a.data[i-1][j-1] = sum;
                if ((dum = vv[i-1]*Math.abs(sum)) >= big) {
                    big  = dum;
                    imax = i;
                }
            }
            if (j != imax) {
                for (k = 1; k <= n; k++) {
                    dum = a.data[imax-1][k-1];
                    a.data[imax-1][k-1] = a.data[j-1][k-1];
                    a.data[j-1][k-1] = dum;
                }
                d[0] = -d[0];
                vv[imax-1] = vv[j-1];
            }
            if (a.data[j-1][j-1] == 0.0)
                a.data[j-1][j-1] = 1.0e-20;
            if (j != n) {
                dum = 1.0/a.data[j-1][j-1];
                for (i = j+1; i <= n; i++)
                    a.data[i-1][j-1] *= dum;
            }
        }

        return a;
    }

    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {

        Matrix A = new GeneralMatrix(2,2);
        A.setIJ(0,0,1);
        A.setIJ(0,1,2);
        A.setIJ(1,0,4);
        A.setIJ(1,1,3);

        Matrix B = new GeneralMatrix(2,2);
        B.setIJ(0,0,1);
        B.setIJ(0,1,0);
        B.setIJ(1,0,1);
        B.setIJ(1,1,2);

        Matrix C = new GeneralMatrix((GeneralMatrix)A);

        // Test getIJ function
        System.out.println("Get 0,0 value of matrix A: " + A.getIJ(0,0));

        // Test toString() function
        System.out.println("Matrix A: " + A.toString());
        System.out.println("Matrix B: " + B.toString());
        System.out.println("Matrix C (Copy of A): " + C.toString());

        // Test determinant() function
        System.out.println("Determinant of A is: " + A.determinant() + " (Answer: -5)");
        System.out.println("Determinant of B is: " + B.determinant() + " (Answer: 2)");

        // Test add() function
        Matrix F = A.add(B);
        System.out.println("Matrix A + B: " + F.toString());

        // Test multiply() function
        Matrix E = A.multiply(B);
        System.out.println("Matrix A * B: " + E.toString());
        Matrix D = A.multiply(2);
        System.out.println("Matrix A * 2: " + D.toString());


        // Test random() function
        Matrix G = new GeneralMatrix(2,2);
        G.random();
        System.out.println("Random Matrix: " + G.toString());

        //
        // Test exceptions
        //

        // Try to create matrix of dimension zero

        System.out.println("Trying to generate a matrix of dimension zero ");

        try {

            Matrix A2 = new GeneralMatrix(0,0);

        } catch (MatrixException Except){

            System.out.println(Except.getMessage());

        }

        // Try to add two matrices of invalid dimensions

        System.out.println("Trying to add 2x2 matrix to 3x3 matrix: ");

        try {

            Matrix A2 = new GeneralMatrix(2,2);
            A2.random();

            Matrix B2 = new GeneralMatrix(3,3);
            B2.random();

            Matrix C2 = B2.add(A2);

        } catch (MatrixException Except){

            System.out.println(Except.getMessage());

        }

        // Try to multiply two matrices of invalid dimensions

        System.out.println("Trying to multiply 3x3 matrix by a 2x2 matrix: ");

        try {

            Matrix A2 = new GeneralMatrix(2,2);
            A2.random();

            Matrix B2 = new GeneralMatrix(3,3);
            B2.random();

            Matrix C2 = B2.multiply(A2);

        } catch (MatrixException Except){

            System.out.println(Except.getMessage());

        }

        // Try to find determinant of a 5x3 matrix

        System.out.println("Trying to find determinant of 5x3 matrix: ");

        try {

            Matrix A2 = new GeneralMatrix(5,3);
            A2.determinant();

        } catch (MatrixException Except) {

            System.out.println(Except.getMessage());

        }

        // Try to find determinant of singular 2x2 matrix

        System.out.println("Trying to find determinant of a singular matrix: ");

        try {

            Matrix A2 = new GeneralMatrix(2,2);

            A2.setIJ(0,0,1);
            A2.setIJ(0,1,1);
            A2.setIJ(1,0,0);
            A2.setIJ(1,1,0);

            A2.determinant();

        } catch (MatrixException Except){

            System.out.println(Except.getMessage());

        }
    }
}