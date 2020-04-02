/*
 * PROJECT III: TriMatrix.java
 *
 * This file contains a template for the class TriMatrix. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Matrix class.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

import java.security.cert.TrustAnchor;

public class TriMatrix extends Matrix {

    // Getter function for n
    public int getN() {
        return n;
    }

    // Getter function for m
    public int getM() {
        return m;
    }

    /**
     * An array holding the diagonal elements of the matrix.
     */
    private double[] diag;

    /**
     * An array holding the upper-diagonal elements of the matrix.
     */
    private double[] upper;

    /**
     * An array holding the lower-diagonal elements of the matrix.
     */
    private double[] lower;

    /**
     * Constructor function: should initialise m and n through the Matrix
     * constructor and set up the data array.
     *
     * @param N  The dimension of the array.
     */
    public TriMatrix(int N) {

        super(N,N); // Initialise N through the Matrix constructor

        // Check that dimension of N is valid - otherwise throw an exception

        if (N < 1){
            throw new MatrixException("Invalid matrix dimension.");
        }

        // Set up the three arrays for storing the lower diagonal, upper diagonal and the diagonal

        diag = new double[N];
        upper = new double[N-1];
        lower = new double[N-1];
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

        double IJ;

        if (i >= 0 && j >= 0 && i < m && i < n) {

             // Check if the element is in the diagonal

            if (i
                    == j) {
                IJ = diag[i];

                // Check if the element is in the upper diagonal

            } else if (j == i + 1) {
                IJ = upper[i];

                // Check if the element is in the lower diagonal

            } else if (i == j + 1) {
                IJ = lower[j];

            } else {
                IJ = 0;
            }

            return IJ;

        } else {

            throw new MatrixException("Invalid ith jth position.");
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


        if (i >= 0 && j >= 0 && i < m && i < n) {

            // Check if the element is in the diagonal - set the value in the array

            if (i == j) {
                diag[i] = val;

                // Check if the element is in the upper diagonal - set the value in the array

            } else if (j == i + 1) {
                upper[i] = val;

                // Check if the element is in the lower diagonal - set the value in the array

            } else if (i == j + 1) {
                lower[j] = val;

            } else {

                throw new MatrixException("Cannot change non-tridiagonal element.");
            }

        } else {
            throw new MatrixException("Invalid ith jth position.");
        }
    }

    /**
     * Return the determinant of this matrix.
     *
     * @return The determinant of the matrix.
     */
    public double determinant() {

        // Set up double to be used to calculate the determinant

        double det = 1;

        TriMatrix LU = this.decomp();

        // Similar to GeneralMatrix - determinant is the product of the diagonal elements

        for (int i = 0; i < this.n; i++) {
            det = det * LU.getIJ(i,i);
        }

        // Return the determinant

        return det;
    }

    /**
     * Returns the LU decomposition of this matrix. See the formulation for a
     * more detailed description.
     *
     * @return The LU decomposition of this matrix.
     */
    public TriMatrix decomp() {

        // Set up a new matrix to be returned by function

        TriMatrix LU = new TriMatrix(this.n);

        // First entry of this matrix remains the same - set first entry of LU to this value

        LU.setIJ( 0,0, this.getIJ(0,0));

        // Loop through all the upper diagonal elements of this matrix (all remain the same) - copy into LU

        for (int i = 0; i < (this.n) - 1; i++) {
            LU.setIJ(i, i + 1, this.getIJ(i,i + 1));
        }

        // Loop through rest of the matrix - calculate the value of each element

        for (int i = 0; i < (this.n) - 1; i++) {

            // Compare each entry in this matrix to the corresponding entry in L*U
            // Use difference relation to calculate the value of each element in LU

            // Loop through lower diagonal elements

            LU.setIJ(i + 1, i, getIJ(i + 1, i)/LU.getIJ(i, i));

            // Loop through diagonal elements

            LU.setIJ(i+1, i+1, this.getIJ(i+1, i+1) - (LU.getIJ(i+1, i) * LU.getIJ(i,i+1)));
        }

        // Return the LU matrix

        return LU;

    }

    /**
     * Add the matrix to another matrix A.
     *
     * @param A  The Matrix to add to this matrix.
     * @return   The sum of this matrix with the matrix A.
     */
    public Matrix add(Matrix A){

        if (A instanceof GeneralMatrix) {

            return A.add(this);
        } else if (A instanceof TriMatrix) {

            if (this.n == A.getN()) {

                TriMatrix newMatrix = new TriMatrix(this.n);

                // Loop through all upper diagonal elements - add corresponding elements in A and this matrices

                for (int i = 0; i < (this.n) - 1; i++) {

                    newMatrix.setIJ(i, i+1, this.getIJ(i,i+1) + A.getIJ(i,i+1));
                }

                // Loop through all diagonal elements - add corresponding elements in A and this matrices

                for (int i = 0; i < this.n; i++) {

                    newMatrix.setIJ(i,i, this.getIJ(i,i) + A.getIJ(i,i));
                }

                // Loop through all lower diagonal elements - add corresponding elements in A and this matrices

                for (int i = 0; i < (this.n) - 1; i++) {

                    newMatrix.setIJ(i+1,i, this.getIJ(i+1,i) + A.getIJ(i+1,i));
                }

                return newMatrix;
            } else {

                throw new MatrixException("Invalid matrix dimensions.");
            }
        } else {
            throw new MatrixException("Invalid matrix type.");
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

            // Returns newMatrix - consists of the product of this and A

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

        // Set up a matrix which the function will return

        TriMatrix newMatrix = new TriMatrix(this.n);

        // Loop through all the upper diagonal elements - multiply each by scalar a

        for (int i = 0; i < (this.n) - 1; i++) {

            newMatrix.setIJ(i,i+1, this.getIJ(i,i+1) * a);
        }

        // Loop through all diagonal elements - multiply each by scalar a

        for (int i = 0; i < this.n; i++) {

            newMatrix.setIJ(i,i, this.getIJ(i,i) * a);
        }

        // Loop through all lower diagonal elements - multiply each by scalar a

        for (int i = 0; i < (this.n) - 1; i++) {

            newMatrix.setIJ(i + 1,i, this.getIJ(i + 1,i) * a);
        }

        // Returns newMatrix

        return newMatrix;
    }

    /**
     * Populates the matrix with random numbers which are uniformly
     * distributed between 0 and 1.
     */
    public void random() {

        // Loop through the upper and lower diagonal, and diagonal elements of the matrix
        // Set each element to a random value between 0 and 1

        for (int i = 0; i < (this.n) - 1; i++) {
            this.setIJ(i,i+1, Math.random());
        }

        for (int i = 0; i < (this.n); i++) {
            this.setIJ(i,i, Math.random());
        }

        for (int i = 0; i < (this.n) - 1; i++) {
            this.setIJ(i+1,i, Math.random());
        }
    }

    /*
     * Your tester function should go here.
     */
    public static void main(String[] args) {

        // Test Constructor and setIJ function

        Matrix A = new TriMatrix(3);
        A.setIJ(0,0,1);
        A.setIJ(0,1,2);
        A.setIJ(1,0,2);
        A.setIJ(1,1,1);
        A.setIJ(1,2,2);
        A.setIJ(2,1,4);
        A.setIJ(2,2,3);

        Matrix B = new TriMatrix(3);
        B.setIJ(0,0,1);
        B.setIJ(0,1,1);
        B.setIJ(1,0,2);
        B.setIJ(1,1,3);
        B.setIJ(1,2,2);
        B.setIJ(2,1,3);
        B.setIJ(2,2,4);

        Matrix C = new GeneralMatrix(3,3);
        C.setIJ(0,0,1);
        C.setIJ(0,1,1);
        C.setIJ(1,0,1);
        C.setIJ(2,0,1);
        C.setIJ(0,2,1);
        C.setIJ(1,1,1);
        C.setIJ(1,2,1);
        C.setIJ(2,1,1);
        C.setIJ(2,2,1);

        // Test getIJ() function
        System.out.println("Get 0,0 value of matrix A: " + A.getIJ(0,0));

        // Test toString() function
        System.out.println("Matrix A: \n" + A.toString());
        System.out.println("Matrix B: \n" + B.toString());
        System.out.println("Matrix C: \n" + C.toString());

        // Test determinant() function
        System.out.println("Determinant of A is: " + A.determinant());
        System.out.println("Determinant of B is: " + B.determinant());

        // Test add() function
        Matrix D = A.add(B);
        System.out.println("Matrix A + B (two TriMatrices): \n" + D.toString());

        Matrix E = A.add(C);
        System.out.println("Matrix A + C (one GeneralMatrix and one TriMatrix): \n" + E.toString());

        // Test multiply() function
        Matrix F = A.multiply(B);
        System.out.println("Matrix A * B (two TriMatrices): \n" + F.toString());

        Matrix G = A.multiply(2);
        System.out.println("Matrix A * 2: \n" + G.toString());

        Matrix H = A.multiply(C);
        System.out.println("Matrix A * C (one GeneralMatrix and one TriMatrix): \n" + H.toString());

        // Test random() function
        Matrix I = new TriMatrix(3);
        I.random();
        System.out.println("Random Matrix: \n" + I.toString());

        //
        // Test exceptions
        //

        // Try to create matrix of dimension zero

        System.out.println("Trying to generate a matrix of dimension zero ");

        try {

            Matrix A2 = new TriMatrix(0);

        } catch (MatrixException Except){

            System.out.println(Except.getMessage());

        }

        // Try to add two matrices of invalid dimensions

        System.out.println("Trying to add 2x2 matrix to 3x3 matrix: ");

        try {

            Matrix A2 = new TriMatrix(2);
            A2.random();

            Matrix B2 = new TriMatrix(3);
            B2.random();

            Matrix C2 = B2.add(A2);

        } catch (MatrixException Except){

            System.out.println(Except.getMessage());

        }

        // Try to multiply two matrices of invalid dimensions

        System.out.println("Trying to multiply 3x3 matrix by a 2x2 matrix: ");

        try {

            Matrix A2 = new TriMatrix(2);
            A2.random();

            Matrix B2 = new TriMatrix(3);
            B2.random();

            Matrix C2 = B2.multiply(A2);

        } catch (MatrixException Except){

            System.out.println(Except.getMessage());

        }

        // Try to set a non-diagonal element

        System.out.println("Trying to set a non-diagonal element.");

        try {

            Matrix A2 = new TriMatrix(3);
            A2.setIJ(2,0, 1);

        } catch (MatrixException Except){
            System.out.println((Except.getMessage()));
        }
    }
}
