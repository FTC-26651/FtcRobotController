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
            telemetry.addData("Target X", target.getX());
            telemetry.addData("Target Y", target.getY());

            nextMove = Pathfinder.getNextMove(target, robot.location.getLocation());
            robot.driveTrain.moveToPoint(robot.location, nextMove);

            telemetry.addData("NextMove X", nextMove.getX());
            telemetry.addData("NextMove Y", nextMove.getY());

            telemetry.addData("location X", robot.location.getLocation().getX());
            telemetry.addData("location Y", robot.location.getLocation().getY());

            telemetry.update();
        }
    }
}