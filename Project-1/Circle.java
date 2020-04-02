public class Circle {
    /*
     * Here, you should define private variables that represent the radius and
     * centre of this particular Circle. The radius should be of type double,
     * and the centre should be of type Point.
     */

    private double radius;

    private Point centre;

    public static final double GEOMTOL = 1.0e-6;

    // =========================
    // Constructors
    // =========================
    /**
     * Default constructor - performs no initialization.
     */
    public Circle() {
        // This method is complete.
    }

    /**
     * Alternative constructor, which sets the circle up with x and y
     * co-ordinates representing the centre, and a radius. Remember you should
     * not store these x and y co-ordinates explicitly, but instead create a
     * Point to hold them for you.
     *
     * @param xc   x-coordinate of the centre of the circle
     * @param yc   y-coordinate of the centre of the circle
     * @param rad  radius of the circle
     */
    public Circle(double xc, double yc, double rad) {
        centre = new Point(xc, yc);
        radius = rad;
    }

    /**
     * Alternative constructor, which sets the circle up with a Point
     * representing the centre, and a radius.
     *
     * @param centre  Point representing the centre
     * @param rad     Radius of the circle
     */

    public Circle(Point centre, double rad) {
        this.setCentre(centre);
        this.radius = rad;
    }

    // =========================
    // Setters and Getters
    // =========================

    /**
     * Setter - sets the co-ordinates of the centre.
     *
     * @param xc  new x-coordinate of the centre
     * @param yc  new y-coordinate of the centre
     */
    public void setCentre(double xc, double yc) {
        this.setCentre(new Point(xc, yc));
    }

    /**
     * Setter - sets the centre of the circle to a new Point.
     *
     * @param C  A Point representing the new centre of the circle.
     */
    public void setCentre(Point C) {
        this.centre = C;
    }

    /**
     * Setter - change the radius of this circle.
     *
     * @param rad  New radius for the circle.
     */
    public void setRadius(double rad) {
        this.radius = rad;
    }

    /**
     * Getter - returns the centre of this circle.
     *
     * @return The centre of this circle (a Point).
     */
    public Point getCentre(){
        return this.centre;
    }

    /**
     * Getter - extract the radius of this circle.
     *
     * @return The radius of this circle.
     */
    public double getRadius(){
        return this.radius;
    }

    // Added a getter for the x and y co-ordinates of the centre of the circle which invokes the getX and getY
    // method from the Points class

    public double getCentreX() {
        return centre.getX();
    }

    public double getCentreY() {
        return centre.getY();
    }

    // =========================
    // Convertors
    // =========================

    /**
     * Calculates a String representation of the Circle.
     *
     * @return A String of the form: "[Ax,Ay], r=Radius" where Ax and Ay are
     *         numerical values of the coordinates, and Radius is a numerical
     *         value of the radius.
     */
    public String toString() {
        return "[" + getCentreX() + "," + getCentreY() + "], r=" + getRadius();
    }

    // ==========================
    // Service routines
    // ==========================

    /**
     * Similar to the equals() function in Point. Returns true if two Circles
     * are equal. By this we mean:
     *
     * - They have the same radius (up to tolerance).
     * - They have the same centre (up to tolerance).
     *
     * Remember that the second test is already done in the Point class!
     *
     * @return true if the two circles are equal.
     */
    public boolean equals(Circle c) {
        return(Math.abs(this.getRadius() - c.getRadius()) <= GEOMTOL &&
                Math.abs(this.getCentreX() - c.getCentreX()) <= GEOMTOL &&
                Math.abs(this.getCentreY() - c.getCentreY()) <= GEOMTOL);
    }

    // Added a method to compute the distance between the centres of two circles
    // by invoking the distance method in the Point class

    public double distance(Circle c) {
        return centre.distance(c.centre);
    }

    // -----------------------------------------------------------------------
    // Do not change the method below! It is essential for marking the
    // project, and you may lose marks if it is changed.
    // -----------------------------------------------------------------------

    /**
     * Compare this Circle with some Object, using the test above.
     *
     * @param obj  The object to compare with.
     * @return true if the two objects are equal.
     */
    public boolean equals(Object obj) {


        if (obj instanceof Circle) {
            boolean test = false;
            Circle C = (Circle)obj;

            test = this.equals(C);

            return test;
        } else {
            return false;
        }
    }

    // ======================================
    // Implementors
    // ======================================

    /**
     * Computes and returns the area of the circle.
     *
     * @return  Area of the circle
     */
    public double area() {
        return Math.PI * Math.pow(radius, 2);
    }

    /**
     * Tests whether this circle envelops another
     * Circle, as set out in the project formulation.
     *
     * @return An integer describing the overlap with C:
     *         1 - this envelops c; 0 - Neither envelops; -1 c envelops this circle ; -2 - identical.
     */
    public int envelops(Circle C) {
         int env = 0;

         double distance = this.centre.distance(C.getCentre());
         double radius_1 = this.getRadius();
         double radius_2 = C.getRadius();

         if (equals(C)) {
             env = -2;
         }
         if (!equals(C)) {
                 if (distance >= radius_1 + radius_2) {
                     env = 0;
                 }
                 if (distance <= radius_1 - radius_2) {
                     env = 1;
                 }
                 if (distance <= radius_2 - radius_1) {
                     env = -1;
                 }
             }

          return env;
    }



    // =======================================================
    // Tester - test methods defined in this class
    // =======================================================

    public static void main(String args[]) {
        // You need to fill in this method.
    }
}

public class Point {
    /**
     * x and y co-ordinates of the point on the plane.
     */
    private double X, Y;

    /**
     * GEOMTOL is described in the formulation. It describes the tolerance
     * that we are going to use throughout this project. Remember that you do
     * NOT need to redefine this in other classes.
     */
    public static final double GEOMTOL = 1.0e-6;

    // =========================
    // Constructors
    // =========================

    /**
     * Default constructor - this initializes X and Y to the point (0,0).
     */
    public Point() {
        // This method is complete.
        setPoint(0.0,0.0);
    }

    /**
     * Two-parameter version of the constructor. Initialiases (X,Y) to the
     * a point (a,b) which is supplied to the function.
     *
     * @param X - x-coordinate of the point
     * @param Y - y-coordinate of the point
     */
    public Point (double X, double Y) {
        // This method is complete.
        setPoint(X,Y);
    }

    // =========================
    // Setters and Getters
    // =========================

    /**
     * Setter for instance variables - sets the coordinates of the point.
     *
     * @param X - new x-coordinate for this point.
     * @param Y - new y-coordinate for this point
     */
    public void setPoint(double X, double Y) {
        // This method is complete.
        this.X = X;
        this.Y = Y;
    }

    /**
     * Getter for x co-ordinate.
     *
     * @param  none
     * @return The x co-ordinate of this point.
     */
    public double getX() {
        return X;
    }

    /**
     * Getter for y co-ordinate.
     *
     * @param  none
     * @return The y co-ordinate of this point.
     */
    public double getY() {
        return Y;
    }

    // =========================
    // Convertors
    // =========================

    /**
     * Calculates a String representation of the Point.
     *
     * @return A String of the form [X,Y]
     */
    public String toString() {
        // This method is complete.
        return "[" + X + "," + Y + "]";
    }

    // ==========================
    // Implementors
    // ==========================

    /**
     * Compute the distance of this Point to the supplied Point x.
     *
     * @param x  Point from which the distance should be measured.
     * @return   The distance between x and this instance
     */
    public double distance(Point x) {
        double dx = x.X  - X;
        double dy = x.Y - Y;
        return Math.sqrt((dx*dx + dy*dy));
    }

    // ==========================
    // Service routines
    // ==========================

    // -----------------------------------------------------------------------
    // Do not change the two methods below! They are essential for marking the
    // project, and you may lose marks if they are changed.
    //
    // We shall talk about these functions later (week 17).
    // -----------------------------------------------------------------------

    /**
     * Compare this with some Object. Two points are equal if their are in a
     * box given by the constant GEOMTOL.
     *
     * @param obj The object to compare with.
     * @return If obj is a Quaternion with the same coefficients.
     */
    public boolean equals(Object obj) {
        // This method is complete.
        if (obj instanceof Point) {
            Point q = (Point)obj;
            return (Math.abs(X - q.X) <= GEOMTOL &&
                    Math.abs(Y - q.Y) <= GEOMTOL);
        } else {
            return false;
        }
    }

    /**
     * Compare two points. Two points are equal if their are in a box given by
     * the constant GEOMTOL.
     *
     * @param q  A Point to be compared to this instance
     * @return   true if Point q is equal to this instance.
     */
    public boolean equals(Point q) {
        return (Math.abs(X - q.X) <= GEOMTOL &&
                Math.abs(Y - q.Y) <= GEOMTOL);
    }

    // =======================================================
    // Tester - tests methods defined in this class
    // =======================================================

    /**
     * Your tester function should go here (see week 14 lecture notes if
     * you're confused). It is not tested by BOSS, but you will receive extra
     * credit if it is implemented in a sensible fashion.
     */
    public static void main (String args[]) {
        // You should fill in this method.
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Project1 {
    // -----------------------------------------------------------------------
    // Do not modify the names of the variables below! This is where you will
    // store the results generated in the Results() function.
    // -----------------------------------------------------------------------
    public int    circleCounter; // Number of non-singular circles in the file.
    public int    envelopsFirstLast;  //The result of the first circle enveloping the last circle in the file.
    public double maxArea;       // Area of the largest circle (by area).
    public double minArea;       // Area of the smallest circle (by area).
    public double averageArea;   // Average area of the circles.
    public double stdArea;       // Standard deviation of area of the circles.
    public double medArea;       // Median of the area.
    public int    stamp=472148;
    // -----------------------------------------------------------------------
    // You may implement - but *not* change the names or parameters of - the
    // functions below.
    // -----------------------------------------------------------------------

    /**
     * Default constructor for Project1. You should leave it empty.
     */
    public Project1() {
        // This method is complete.
    }

    /**
     * Results function. It should open the file called FileName (using
     * Scanner), and from it generate the statistics outlined in the project
     * formulation. These are then placed in the instance variables above.
     *
     * @param fileName  The name of the file containing the circle data.
     */
    public void results(String fileName) {

        double x, y, rad;
        int lineCount = 0;
        circleCounter = 0;

        double maxX = Double.MIN_VALUE;
        double minX = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;

        double maxRad = Double.MAX_VALUE;
        double minRad = Double.MIN_VALUE;

        // Counts number of lines i.e. circles in the file

        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)));

            while (scanner.hasNext()) {

                lineCount++;

            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Creates an array to store the circles

        Circle[] stored_circles = new Circle[lineCount];


        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)));

            int i = 0;

            while(scanner.hasNext()) {

                x = scanner.nextDouble();
                y = scanner.nextDouble();
                rad = scanner.nextDouble();

                // Each value of x,y and rad to used to form a new circle which is stored in the array

                Circle ith_circle = new Circle(x,y,rad);
                stored_circles[i] = ith_circle;
                i = i + 1;

                if (rad != 0) {
                    circleCounter ++;
                }

                if (x > maxX) {
                    maxX = x;
                }
                if (y > maxY) {
                    maxY = y;
                }
                if(rad > maxRad) {
                    maxRad = rad;
                }
                if (x < minX) {
                    minX = x;
                }
                if (y < minY) {
                    minY = y;
                }
                if (rad < minRad) {
                    minRad = rad;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Generate a circle at origin with the maximum radius by using the Circle class
        // Compute maximum area by invoking the area method in the Circle class

        Circle bigcircle = new Circle(0,0,maxRad);
        maxArea = bigcircle.area();

        // Same approach for the minimum area

        Circle smallcircle = new Circle(0,0,minRad);
        minArea = smallcircle.area();

        // Use function averageArea to calculate the average area of the circles in the array

        averageArea = averageArea(stored_circles);

        // Use function areaStandardDeviation to calculate the standard deviation of the areas in the array

        stdArea = areaStandardDeviation(stored_circles);

        // Result of the first circle enveloping the last circle in the array

        envelopsFirstLast = stored_circles[1].envelops(stored_circles[lineCount]);

        // Array to store the values of the area of each circle in the array

        double[] area_circles = new double[lineCount];

        for(int i = 0; i < lineCount ; i++) {
            double area;
            area = stored_circles[i].area();
            area_circles[i] = area;

        }

        // Bubble sort the array
        for (int i = 0; i < lineCount-1; i++)
            for (int j = 0; j < lineCount-i-1; j++)
                if (area_circles[j] > area_circles[j+1])
                {
                    double temp = area_circles[j];
                    area_circles[j] = area_circles[j+1];
                    area_circles[j+1] = temp;
                }

        // Check for number of elements in the array
        // Check for even case

        if (lineCount % 2 != 0)
            medArea = area_circles[lineCount / 2];
        else
            medArea = (area_circles[(lineCount - 1) / 2] + area_circles[lineCount / 2]) / 2.0;
    }

    /**
     * A function to calculate the average area of circles in the array provided.
     *
     * @param circles  An array if Circles
     */
    public double averageArea(Circle[] circles) {
        double total = 0;

        for(int i=0; i<circles.length; i++) {
            double a;
            a = circles[i].area();
            total = total + a;
        }
        return (total /circles.length);
    }

    /**
     * A function to calculate the standard deviation of areas in the circles in the array provided.
     *
     * @param circles  An array of Circles
     */
    public double areaStandardDeviation(Circle[] circles) {
        double standardDev = 0, mean, num;
        mean = averageArea(circles);
        int length = circles.length;

        for(int i=0; i<length; i++) {
            num = circles[i].area();
            standardDev += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDev/length);
    }

    // =======================================================
    // Tester - tests methods defined in this class
    // =======================================================

    /**
     * Your tester function should go here (see week 14 lecture notes if
     * you're confused). It is not tested by BOSS, but you will receive extra
     * credit if it is implemented in a sensible fashion.
     */
    public static void main(String args[]){
        // You need to fill in this method.
    }
}
