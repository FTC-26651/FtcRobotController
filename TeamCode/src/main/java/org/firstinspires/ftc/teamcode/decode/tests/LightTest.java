package org.firstinspires.ftc.teamcode.decode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.math.*;

@TeleOp(name = "Light Test", group = "Robot")
public class LightTest extends LinearOpMode {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    Servo LED;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        LED = hardwareMap.get(Servo.class, "LED");

        telemetry.addLine("Test Ready.");
        telemetry.update();

        waitForStart();

        timer.reset();

        while (opModeIsActive()) {
            LED.setPosition(round(timer.seconds() / 10, 2));
            if (timer.seconds() >= 10) {
                timer.reset();
            }
        }
    }
}
