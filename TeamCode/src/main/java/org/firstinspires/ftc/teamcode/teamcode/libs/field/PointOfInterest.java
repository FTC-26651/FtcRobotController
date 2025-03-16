package org.firstinspires.ftc.teamcode.libs.field;

public class PointOfInterest {
    String[] interalStorageOFlags;
    double[][] coordinates;

    public PointOfInterest(double[][] coords, String[] flags) {
        interalStorageOFlags = flags;
        coordinates = coords;
    }
    public PointOfInterest(double[][] coords) {
        coordinates = coords;
    }

    public double[][] getCoordinates() {
        return coordinates;
    }

    public String[] getFlags() {
        return interalStorageOFlags;
    }
    public boolean hasFlag(String flag) {
        boolean hasFoundFlag = false;

        for (String fl:  interalStorageOFlags) {
            if (fl.equals(flag)) {
                hasFoundFlag = true;
                break;
            }
        }

        return hasFoundFlag;
    }
}
