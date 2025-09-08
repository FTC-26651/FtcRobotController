package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.libs.Point;
import org.firstinspires.ftc.teamcode.libs.pathfinder.Pathfinder;
import org.firstinspires.ftc.teamcode.robot.DevModeRobot;

@Autonomous(name = "Auto Test", group = "Robot")
public class AutoTest extends LinearOpMode {
    DevModeRobot robot;

    Point target;
    Point nextMove;

    public void runOpMode() {
        target = new Point(6, 7);

        robot = new DevModeRobot(this);

        robot.setName("DevModeRobot");
        robot.initRobot();

        telemetry.addLine("Robot Ready.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            nextMove = Pathfinder.getNextMove(target, robot.gps.getLocation());

            double targetAngle = Math.toDegrees(Math.atan2(target.getY() - robot.gps.getLocation().getY(), target.getX() - robot.gps.getLocation().getX()));

            if (Math.abs(robot.gps.getRotation() - targetAngle) > 5) {
                robot.driveTrain.turnTo(robot.gps, targetAngle);
            } else {
                robot.driveTrain.moveToPoint(robot.gps, nextMove);
            }

            telemetry.addData("Current X: ", robot.gps.getLocation().getX());
            telemetry.addData("Current Y: ", robot.gps.getLocation().getY());

            telemetry.update();
        }
    }
}