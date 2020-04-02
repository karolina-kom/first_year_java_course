/*
 * PROJECT III: Project3.java
 *
 * This file contains a template for the class Project3. None of methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file. You will also need to have completed
 * the Matrix class, as well as GeneralMatrix and TriMatrix.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file!
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

public class Project3 {
    /**
     * Calculates the variance of the distribution defined by the determinant
     * of a random matrix. See the formulation for a detailed description.
     *
     * @param m           The matrix object that will be filled with random
     *                    samples.
     * @param numSamples  The number of samples to generate when calculating
     *                    the variance.
     * @return            The variance of the distribution.
     */
    public static double matVariance(Matrix m, int numSamples) {

        double det = 0;
        double sumDet = 0;
        double sumDet2 = 0;

        for(int i = 0; i < numSamples; i++) {

            m.random();

            det = m.determinant();

            sumDet = sumDet + det;
            sumDet2 = sumDet2 + Math.pow(det,2);
        }

        return (sumDet2 / (numSamples - Math.pow((sumDet/ numSamples),2)));
    }

    /**
     * This function should calculate the variances of matrices for matrices
     * of size 2 <= n <= 50. See the formulation for more detail.
     */
    public static void main(String[] args) {

        GeneralMatrix Gen;
        TriMatrix Tri;

        for (int i = 2; i < 51; i++) {

            Gen = new GeneralMatrix(i,i);
            Tri = new TriMatrix(i);
            System.out.println(i + " " +matVariance(Gen, 150000)+ " " +matVariance(Tri, 150000));
        }
    }
}