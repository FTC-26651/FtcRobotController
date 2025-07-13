package org.firstinspires.ftc.teamcode.libs.pathfinder;

import org.firstinspires.ftc.teamcode.libs.Polygon;
import org.firstinspires.ftc.teamcode.libs.field.Point;

import java.util.*;

public class Pathfinder {
    static List<Polygon> obstacles = new ArrayList<>();

    private static boolean isBlocked(Point from, Point to) {
        for (Polygon poly : obstacles) {
            if (poly.intersects(from, to)) return true;
        }
        return false;
    }

    private static Point reconstructFirstMove(Node node) {
        Node current = node;
        while (current.parent != null && current.parent.parent != null) {
            current = current.parent;
        }
        return current.point;
    }

    public static Point getNextMove(Point start, Point goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.estimatedTotalCost));
        Set<String> visited = new HashSet<>();

        Node startNode = new Node(start, 0, start.distance(goal), null);
        openSet.add(startNode);

        double stepSize = 1.0;

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            String key = current.point.getX() + "," + current.point.getY();
            if (visited.contains(key)) continue;
            visited.add(key);

            if (current.point.distance(goal) < stepSize) {
                return reconstructFirstMove(current);
            }

            // Calculate costs
            for (double angle = 0; angle < 360; angle += 30) {
                double rad = Math.toRadians(angle);
                double nx = current.point.getX() + stepSize * Math.cos(rad);
                double ny = current.point.getY() + stepSize * Math.sin(rad);
                Point neighbor = new Point(nx, ny);

                if (isBlocked(current.point, neighbor)) continue;

                double newCost = current.cost + current.point.distance(neighbor);
                Node neighborNode = new Node(neighbor, newCost, newCost + neighbor.distance(goal), current);
                openSet.add(neighborNode);
            }
        }

        return null; // No path found
    }

    public static void loadObject(Polygon p) {
        obstacles.add(p);
    }
}
