package org.firstinspires.ftc.teamcode.libs.pathfinder;

import org.firstinspires.ftc.teamcode.libs.Point;

public class Node {
    Point point;
    double cost;
    double estimatedTotalCost;
    Node parent;

    Node(Point point, double cost, double estimatedTotalCost, Node parent) {
        this.point = point;
        this.cost = cost;
        this.estimatedTotalCost = estimatedTotalCost;
        this.parent = parent;
    }
}
