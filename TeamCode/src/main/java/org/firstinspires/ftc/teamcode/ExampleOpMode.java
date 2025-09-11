package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ExampleOpMode", group = "Robot")
public class ExampleOpMode extends LinearOpMode {

    /* Declare OpMode member. */
    boolean dUpIsPressed = false;
    boolean dDownIsPressed = false;

    @Override
    public void runOpMode() {

        telemetry.addLine("Robot Ready.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            /////////////////
            // --DISPLAY-- //
            /////////////////
            telemetry.update();
        }
    }
}