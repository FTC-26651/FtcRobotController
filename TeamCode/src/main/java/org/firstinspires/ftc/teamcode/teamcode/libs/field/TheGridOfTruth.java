package org.firstinspires.ftc.teamcode.libs.field;

public class TheGridOfTruth {
    /* Robot Local */
    static double[] currentPos = {0,0};

    public static void updateLocation(double[] newPos) {
        currentPos = newPos;
    }
    public static double[] getLocation() {
        return currentPos;
    }

    /* Places of Interest */

    static double[] samplePickup = {0,0};

    public static void setSamplePickup(double[] newPickup) {
        samplePickup = newPickup;
    }
    public static double[] getSamplePickup() {
        return samplePickup;
    }
}
