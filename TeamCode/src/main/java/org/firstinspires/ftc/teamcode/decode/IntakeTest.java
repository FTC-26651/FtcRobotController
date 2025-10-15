package org.firstinspires.ftc.teamcode.decode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Intake Test", group = "Robot")
public class IntakeTest extends LinearOpMode {
    CRServoImplEx left;

    @Override
    public void runOpMode() {
        left = this.hardwareMap.get(CRServoImplEx.class, "left");

        left.setDirection(DcMotor.Direction.FORWARD);

        left.setDirection(DcMotorSimple.Direction.FORWARD);

        telemetry.addLine("Test Ready.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                left.setPower(1);
            } else if (gamepad1.dpad_down) {
                left.setPower(-1);
            } else {
            }
        }
    }
}