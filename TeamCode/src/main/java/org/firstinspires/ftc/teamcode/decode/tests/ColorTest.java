package org.firstinspires.ftc.teamcode.decode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.decode.robot.sensors.GreenPurpleSensor;

@Disabled
@TeleOp(name = "Color Sensor Test", group = "Robot")
public class ColorTest extends LinearOpMode {
    GreenPurpleSensor sensor;

    @Override
    public void runOpMode() throws InterruptedException {
        sensor = new GreenPurpleSensor("color");

        telemetry.addLine("Test Ready.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Color:", sensor.getColor());

            telemetry.addLine();

            NormalizedRGBA colors = sensor.getRGB();

            telemetry.addData("R", colors.red);
            telemetry.addData("G", colors.green);
            telemetry.addData("B", colors.blue);
            telemetry.addData("A", colors.alpha);

            telemetry.update();
        }
    }
}
