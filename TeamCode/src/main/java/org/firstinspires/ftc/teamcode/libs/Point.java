package org.firstinspires.ftc.teamcode.libs;

public class Point {
    double x, y;
    double theta;

    public Point(double x_axis, double y_axis) {
        x = x_axis;
        y = y_axis;
    }

    public void movePoint(double x_axis, double y_axis) {
        x = x_axis;
        y = y_axis;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public double distance(Point other) {
        return Math.hypot(this.x - other.x, this.y - other.y);
    }
}
