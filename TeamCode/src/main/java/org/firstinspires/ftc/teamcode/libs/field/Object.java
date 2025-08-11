package org.firstinspires.ftc.teamcode.libs.field;

import org.firstinspires.ftc.teamcode.libs.Point;
import org.firstinspires.ftc.teamcode.libs.Polygon;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Object {
    Set<String> flags = new HashSet<>();
    Polygon location;

    public Object(Polygon loc) {
        location = loc;
    }

    public Object(List<Point> vertices) {
        location = new Polygon(vertices);
    }

    public Object(Polygon loc, Set<String> f) {
        location = loc;
        flags = f;
    }

    public Object(List<Point> vertices, Set<String> f) {
        location = new Polygon(vertices);
        flags = f;
    }

    public boolean hasFlag(String flag) {
        if (flags.contains(flag)) {
            return true;
        }
        return false; // Didn't find the flag
    }

    public Polygon getLocation() {
        return location;
    }

    public void moveObject(List<Point> vertices) {
        location.move(vertices);
    }
}
