package org.firstinspires.ftc.teamcode.robot.auto;

import org.firstinspires.ftc.teamcode.libs.Point;
import org.firstinspires.ftc.teamcode.robot.Robot;

import java.util.Objects;

public class PointToPoint {
    Robot robot;

    public PointToPoint(Robot r) {
        robot = r;
    }

    public void move(Point current, Point taget) {
        if (robot.driveTrain.type.equals("Tank")) {
            robot.driveTrain.turnTo(22);
        }
    }
}
