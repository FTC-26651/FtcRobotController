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
            nextMove = Pathfinder.getNextMove(target, robot.location.getLocation());
            robot.driveTrain.moveToPoint(robot.location, nextMove);

            telemetry.update();
        }
    }
}