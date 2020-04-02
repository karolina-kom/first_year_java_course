/*
 * PROJECT II: NewtonFractal.java
 *
 * This file contains a template for the class NewtonFractal. Not all methods
 * are implemented. Make sure you have carefully read the project formulation
 * before starting to work on this file.
 *
 * There are a lot of functions in this class, as it deals with creating an
 * image using purely Java. I have already completed a lot of the technical
 * aspects for you, so there should not be a huge amount for you to do in this
 * class!
 *
 * At the bottom of this class there is a section of functions which I have
 * already written and deal with the more complicated tasks. You should make
 * sure that you read through the function descriptions, but DO NOT ALTER
 * THEM! Also, remember to call the setupFractal() function from your
 * constructor!
 *
 * Remember not to change the names, parameters or return types of any
 * variables in this file! You should also test this class using the main()
 * function.
 *
 * The function of the methods and instance variables are outlined in the
 * comments directly above them.
 */

// These next lines import the relevant classes needed to output an image and
// *SHOULD NOT* be changed. You don't need to worry about their definitions
// for the most part!

import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Random;

class NewtonFractal {
    /**
     * A reference to the Newton-Raphson iterator object.
     */
    private Newton iterator;

    /**
     * The top-left corner of the square in the complex plane to examine.
     */
    private Complex origin;

    /**
     * The width of the square in the complex plane to examine.
     */
    private double width;

    /**
     * A list of roots of the polynomial.
     */
    private ArrayList<Complex> roots;

    /**
     * A two dimensional array holding the colours of the plot.
     */
    private Color[][] colors;

    /**
     * A flag indicating the type of plot to generate. If true, we choose
     * darker colors if a particular root takes longer to converge.
     */
    private boolean colorIterations;

    /**
     * A standard Java object which allows us to store a simple image in
     * memory. This will be set up by setupFractal -- you do not need to worry
     * about it!
     */
    private BufferedImage fractal;

    /**
     * This object is another standard Java object which allows us to perform
     * basic graphical operations (drawing lines, rectangles, pixels, etc) on
     * the BufferedImage. This will be set up by setupFractal -- you do not
     * need to worry about it!
     */
    private Graphics2D g2;

    /**
     * Defines the width (in pixels) of the BufferedImage and hence the
     * resulting image.
     */
    public static final int NUMPIXELS = 400;

    // ========================================================
    // Constructor function.
    // ========================================================

    /**
     * Constructor function which initialises the instance variables
     * above. IMPORTANT: Remember to call setupFractal at the end of this
     * function!!
     *
     * @param p       The polynomial to generate the fractal of.
     * @param origin  The top-left corner of the square to image.
     * @param width   The width of the square to image.
     */
    public NewtonFractal(Polynomial p, Complex origin, double width) {

        this.origin = origin;
        this.width = width;
        this.iterator = new Newton(p);
        iterator.iterate(origin);
        this.roots = new ArrayList<Complex>();

        if (iterator.getRoot() != null) {
            roots.add(iterator.getRoot());
        }

        setupFractal();
    }

    // ========================================================
    // Basic operations.
    // ========================================================

    /**
     * Print out all of the roots found so far, which are contained in the
     * roots ArrayList.
     */
    public void printRoots() {
        for (Complex i : roots){
            System.out.println(roots);
        }
    }

    /**
     * Check to see if root is in the roots ArrayList (up to tolerance). If
     * the root is not found, then return -1. Otherwise return the index
     * inside this.roots where you found it.
     *
     * @param root  Root to find in this.roots.
     */
    public int findRoot(Complex root) {

        int isRoot = -1;

        for (int i = 0; i < roots.size(); i++) {

            if (roots.get(i).add(root.minus()).abs() < Newton.TOL) {
                isRoot = i;
                break;
                }
            }
        return isRoot;
    }

    /**
     * Convert from pixel indices (i,j) to the complex number (origin.real +
     * i*dz, origin.imag - j*dz).
     *
     * @param i  x-axis co-ordinate of the pixel located at (i,j)
     * @param j  y-axis co-ordinate of the pixel located at (i,j)
     */
    public Complex pixelToComplex(int i, int j) {
        double dz = width/NUMPIXELS;
        Complex pixelToComplex = new Complex(origin.getReal() + i*dz, origin.getImag() - j*dz);
        return pixelToComplex;
    }

    // ========================================================
    // Fractal generating function.
    // ========================================================

    /**
     * Generate the fractal image. See the colorIterations instance variable
     * for a better description of its purpose.
     */
    public void createFractal(boolean colorIterations) {

        this.colorIterations = colorIterations;

        int root_number = 0;
        int current_root_number = 0;
        int count = 0;

        for(int j = 0; j < NUMPIXELS; j ++) {
            for(int i = 0; i < NUMPIXELS; i ++) {

                iterator.iterate(pixelToComplex(i, j));

                if (iterator.getRoot() != null) {
                    current_root_number = findRoot(iterator.getRoot());

                    if (current_root_number == -1) {
                        roots.add(count, iterator.getRoot());
                        root_number = count;
                        count = count + 1;
                    } else {
                        root_number = current_root_number;
                    }
                }

                colorPixel( i, j, root_number, iterator.getNumIterations());
            }
        }
    }

    // ========================================================
    // Tester function.
    // ========================================================

    public static void main(String[] args) {
        // Here is some example code which generates the two images seen in
        // figure 1 of the formulation.
        Complex[] coeff = new Complex[] { new Complex(-1.0,0.0), new Complex(),
                new Complex(), new Complex(), new Complex(), new Complex(1.0,0.0) };
        Polynomial p    = new Polynomial(coeff);
        NewtonFractal f = new NewtonFractal(p, new Complex(-1.0,0.0), 2.0);

        f.createFractal(false);
        f.saveFractal("fractal-light.png");
        f.createFractal(true);
        f.saveFractal("fractal-dark.png");
    }

    // ====================================================================
    // OTHER FUNCTIONS
    //
    // The rest of the functions in this class are COMPLETE (with the
    // exception of the main function) since they involve quite complex Java
    // code to deal with the graphics. This means they *do not* and *should
    // not* need to be altered! But you should read their descriptions so you
    // know how to use them.
    // ====================================================================

    /**
     * Sets up all the fractal image. Make sure that your constructor calls
     * this function!
     */
    private void setupFractal()
    {
        // This function is complete!
        int i, j;

        if (iterator.getF().degree() < 3 || iterator.getF().degree() > 5)
            throw new RuntimeException("Degree of polynomial must be between 3 and 5 inclusive!");

        this.colors       = new Color[5][Newton.MAXITER];
        this.colors[0][0] = Color.RED;
        this.colors[1][0] = Color.GREEN;
        this.colors[2][0] = Color.BLUE;
        this.colors[3][0] = Color.CYAN;
        this.colors[4][0] = Color.MAGENTA;

        for (i = 0; i < 5; i++) {
            float[] components = colors[i][0].getRGBComponents(null);
            float[] delta      = new float[3];

            for (j = 0; j < 3; j++)
                delta[j] = 0.8f*components[j]/Newton.MAXITER;

            for (j = 1; j < Newton.MAXITER; j++) {
                float[] tmp  = colors[i][j-1].getRGBComponents(null);
                colors[i][j] = new Color(tmp[0]-delta[0], tmp[1]-delta[1],
                        tmp[2]-delta[2]);
            }
        }

        fractal = new BufferedImage(NUMPIXELS, NUMPIXELS, BufferedImage.TYPE_INT_RGB);
        g2      = fractal.createGraphics();
    }

    /**
     * Colors a pixel in the image.
     *
     * @param i          x-axis co-ordinate of the pixel located at (i,j)
     * @param j          y-axis co-ordinate of the pixel located at (i,j)
     * @param rootColor  An integer between 0 and 4 inclusive indicating the
     *                   root number.
     * @param numIter    Number of iterations at this root.
     */
    private void colorPixel(int i, int j, int rootColor, int numIter)
    {
        // This function is complete!
        if (colorIterations)
            g2.setColor(colors[rootColor][numIter-1]);
        else
            g2.setColor(colors[rootColor][0]);
        g2.fillRect(i,j,1,1);
    }

    /**
     * Saves the fractal image to a file.
     *
     * @param fileName  The filename to save the image as. Should end in .png.
     */
    public void saveFractal(String fileName) {
        // This function is complete!
        try {
            File outputfile = new File(fileName);
            ImageIO.write(fractal, "png", outputfile);
        } catch (IOException e) {
            System.out.println("I got an error trying to save! Maybe you're out of space?");
        }
    }
}