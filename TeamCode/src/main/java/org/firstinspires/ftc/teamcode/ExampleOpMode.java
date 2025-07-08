package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.robot.ExampleRobot;
import org.firstinspires.ftc.teamcode.robot.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ExampleOpMode", group = "Robot")
public class ExampleOpMode extends LinearOpMode {

    /* Declare OpMode member. */
    ExampleRobot robot;

    boolean dUpIsPressed = false;
    boolean dDownIsPressed = false;

    @Override
    public void runOpMode() {
        robot = new ExampleRobot(this);

        robot.setName("Example Robot");
        robot.initRobot();

        telemetry.addLine("Robot Ready.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            robot.runEveryLoop();

            robot.driveTrain.move(gamepad1.left_stick_x, gamepad1.left_stick_y, -1 * gamepad1.right_stick_x);

            /////////////////
            // --DISPLAY-- //
            /////////////////
            telemetry.update();
        }
    }
}