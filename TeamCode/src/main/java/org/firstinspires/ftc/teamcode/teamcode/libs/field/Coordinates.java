package org.firstinspires.ftc.teamcode.libs.field;

public class Coordinates {
    double x;
    double y;
    double theta;

    public Coordinates(double x_axis, double y_axis, double rotation) {
        x = x_axis;
        y = y_axis;
        theta = rotation;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getTheta() {
        return theta;
    }
}
