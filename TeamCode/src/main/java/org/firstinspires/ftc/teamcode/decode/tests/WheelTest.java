package org.firstinspires.ftc.teamcode.decode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Disabled
@TeleOp(name = "Wheel Test", group = "Robot")
public class WheelTest extends LinearOpMode {
    DcMotorEx left;

    @Override
    public void runOpMode() {
        left = this.hardwareMap.get(DcMotorEx.class, "left");

        telemetry.addLine("Test Ready.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_down) {
                left.setPower(-1);
            } else if (gamepad1.dpad_up) {
                left.setPower(1);
            } else {
                left.setPower(0);
            }
        }
    }
}