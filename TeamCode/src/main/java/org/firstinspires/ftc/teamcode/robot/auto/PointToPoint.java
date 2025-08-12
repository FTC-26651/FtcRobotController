package org.firstinspires.ftc.teamcode.robot.auto;

import org.firstinspires.ftc.teamcode.libs.Point;
import org.firstinspires.ftc.teamcode.robot.Robot;

import java.util.Objects;

public class PointToPoint {
    Robot robot;

    public PointToPoint(Robot r) {
        robot = r;
    }

    public void move(Point current, Point target) {
        if (robot.driveTrain.type.equals("Tank")) {
            double targetAngle = Math.atan(target.getY() - current.getY() / target.getX() - current.getX());

            robot.driveTrain.turnTo(robot.location, targetAngle);
        }
    }
}
