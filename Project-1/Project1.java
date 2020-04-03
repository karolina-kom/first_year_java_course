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
        circleCounter = 0;
        x = 0;
        y = 0;
        rad = 0;

        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)));

            while (scanner.hasNext()) {

                x = scanner.nextDouble();
                y = scanner.nextDouble();
                rad = scanner.nextDouble();

                if (rad > Point.GEOMTOL) {
                    circleCounter++;
                }
            }

            } catch(Exception e) {
                    System.err.println("An error has occurred - cannot read file.");
                    e.printStackTrace();
            }

        int circleNum = 1;
        Circle firstC = new Circle(0,0,0);
        Circle lastC = new Circle(0,0,0);
        Circle tempC = new Circle(0,0,0);
        Circle[] validCircles = new Circle[circleCounter];
        double[] area = new double[circleCounter];

        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)));

            while (scanner.hasNext()) {

                x = scanner.nextDouble();
                y = scanner.nextDouble();
                rad = scanner.nextDouble();

                if (rad > Point.GEOMTOL){

                    if(circleNum == 1) {
                        firstC.setRadius(rad);
                        firstC.setCentre(x,y);
                    }

                    if (circleNum == circleCounter) {
                        lastC.setRadius(rad);
                        lastC.setCentre(x,y);
                    }

                    tempC.setRadius(rad);
                    tempC.setCentre(x,y);

                    validCircles[circleNum - 1] = tempC;
                    area[circleNum - 1] = tempC.area();

                    circleNum++;
                }

            }

        } catch(Exception e) {
            System.err.println("An error has occurred - cannot read file.");
            e.printStackTrace();
        }

        envelopsFirstLast = firstC.envelops(lastC);

        maxArea = area[0];
        minArea = area[0];
        averageArea = 0;

        for (int i = 0; i < circleCounter; i++) {

            if (area[i] > maxArea) {
                maxArea = area[i];
            }

            if (area[i] < minArea) {
                minArea = area[i];
            }
        }

        averageArea = averageArea(validCircles);
        stdArea = areaStandardDeviation(validCircles);

        boolean swapped = true;

        while (swapped) {

            swapped = false;

            for (int i = 0; i < circleCounter - 2 ; i++) {

                if (area[i] > area[i+1]) {

                    double temp = area[i+1];
                    area[i+1] = area[i];
                    area[i] = temp;
                    swapped = true;
                }
            }
        }

        if (circleCounter % 2 == 0) {

            medArea = (area[((circleNum/2)) - 1] + area[((circleNum/2) + 1) - 1]) / 2;

        } else {

            medArea = area[(circleNum + 1)/2 - 1];
        }
    }

    /**
     * A function to calculate the average area of circles in the array provided.
     *
     * @param circles  An array if Circles
     */
    public double averageArea(Circle[] circles) {

        double total = 0;

        for(int i=0; i < circles.length - 1; i++) {

            double area;
            area = circles[i].area();
            total = total + area;
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

        for(int i=0; i < circles.length - 1; i++) {
            num = circles[i].area();
            standardDev += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDev/circles.length);
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

        Project1 P = new Project1();

        P.results("Project1.data");

        System.out.println("Number of Circles: " + P.circleCounter);
        System.out.println("First circle envelops the last circle? " + P.envelopsFirstLast);
        System.out.println("Min area: "+ P.minArea);
        System.out.println("Max area: " + P.maxArea);
        System.out.println("Average area: " + P.averageArea);
        System.out.println("Standard Deviation of area: " + P.stdArea);
        System.out.println("Medium of area: " + P.medArea);

    }
}
