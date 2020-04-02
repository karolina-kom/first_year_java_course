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