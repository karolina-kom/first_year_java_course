/*
 * PROJECT II: Polynomial.java
 *
 * This file contains a template for the class Polynomial. Not all methods are
 * implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * This class is designed to use Complex in order to represent polynomials
 * with complex co-efficients. It provides very basic functionality and there
 * are very few methods to implement! The project formulation contains a
 * complete description.
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file! You should also test this class using the main()
 * function.
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

public class Polynomial {
    /**
     * An array storing the complex co-efficients of the polynomial.
     */
    Complex[] coeff;

    // ========================================================
    // Constructor functions.
    // ========================================================

    /**
     * General constructor: assigns this polynomial a given set of
     * co-efficients.
     *
     * @param coeff  The co-efficients to use for this polynomial.
     */
    public Polynomial(Complex[] coeff) {
        this.coeff = new Complex[coeff.length];
        for (int i = 0; i < coeff.length; i++) {
            this.coeff[i] = coeff[i];
        }
    }

    /**
     * Default constructor: sets the Polynomial to the zero polynomial.
     */
    public Polynomial() {
        Complex[] zero = new Complex[1];
        zero[0] = new Complex(0,0);
        this.coeff = zero;
    }

    // ========================================================
    // Operations and functions with polynomials.
    // ========================================================

    /**
     * Create a string representation of the polynomial.
     *
     * For example: (1.0+1.0i)+(1.0+2.0i)X+(1.0+3.0i)X^2
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coeff.length; i++) {
            if (i > 0) {
                sb.append(" + ");
            }
            sb.append("(");
            sb.append(coeff[i].toString());
            sb.append(")z^");
            sb.append(Integer.toString(i));
        }
        return sb.toString();
    }

    /**
     * Returns the degree of this polynomial.
     */
    public int degree() {
        return coeff.length - 1;
    }

    /**
     * Evaluates the polynomial at a given point z.
     *
     * @param z  The point at which to evaluate the polynomial
     * @return   The complex number P(z).
     */
    public Complex evaluate(Complex z) {
        Complex p = new Complex();
        for (int i = degree(); i >= 0; i --) {
            p = coeff[i].add(p.multiply(z));
        }
        return p;
    }

    /**
     * Calculate and returns the derivative of this polynomial.
     *
     * @return The derivative of this polynomial.
     */
    public Polynomial derivative() {
        Complex[] coefficients = new Complex[degree()];
        for (int i = 0; i < degree(); i ++) {
            coefficients[i] = coeff[i + 1].multiply(i + 1);
        }
        return new Polynomial(coefficients);
    }

    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        Polynomial zero = new Polynomial();

        Complex[] c1 = {new Complex(1,0), new Complex(2,1), new Complex(3,2)};

        Polynomial p1 = new Polynomial(c1);

        System.out.println("zero(z) = " + zero);
        System.out.println("p1(z) = " + p1);
        System.out.println("Degree of p1(z): " + p1.degree());
        System.out.println("p1(1) = " + p1.evaluate(new Complex(1,0)));
        System.out.println("p1'(z) = " + p1.derivative());
    }
}