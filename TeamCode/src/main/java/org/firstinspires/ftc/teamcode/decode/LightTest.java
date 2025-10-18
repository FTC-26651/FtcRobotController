package org.firstinspires.ftc.teamcode.decode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Light Test", group = "Robot")
public class LightTest extends LinearOpMode {
    Servo LED;
    RevBlinkinLedDriver blink;

    @Override
    public void runOpMode() throws InterruptedException {
        LED = hardwareMap.get(Servo.class, "LED");
        blink = hardwareMap.get(RevBlinkinLedDriver.class, "blink");

        telemetry.addLine("Test Ready.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            LED.setPosition(0.277);
            blink.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
        }
    }
}
