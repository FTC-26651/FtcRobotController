package org.firstinspires.ftc.teamcode.libs;

import org.firstinspires.ftc.teamcode.libs.field.Point;

import java.util.List;

public class Polygon {
    List<Point> vertices;

    public Polygon(List<Point> vertices) {
        this.vertices = vertices;
    }

    public void move(List<Point> vertices) {
        this.vertices = vertices;
    }

    public boolean intersects(Point a, Point b) {
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Point p1 = vertices.get(i);
            Point p2 = vertices.get((i + 1) % n);
            if (linesIntersect(a, b, p1, p2)) {
                return true;
            }
        }
        return false;
    }

    private boolean linesIntersect(Point a1, Point a2, Point b1, Point b2) {
        return ccw(a1, b1, b2) != ccw(a2, b1, b2) &&
                ccw(a1, a2, b1) != ccw(a1, a2, b2);
    }

    private boolean ccw(Point a, Point b, Point c) {
        return (c.getY() - a.getY()) * (b.getX() - a.getX()) > (b.getY() - a.getY()) * (c.getX() - a.getX());
    }
}
