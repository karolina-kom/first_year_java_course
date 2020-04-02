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
