package org.firstinspires.ftc.teamcode.decode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Intake Test", group = "Robot")
public class IntakeTest extends LinearOpMode {
    CRServo left;
    //DcMotorEx left1;


    @Override
    public void runOpMode() {
        left = this.hardwareMap.get(CRServo.class, "notleft");
        //left1 = this.hardwareMap.get(DcMotorEx.class, "left");

        left.setDirection(DcMotor.Direction.FORWARD);
        //left1.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addLine("Test Ready.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                left.setPower(1);
            } else if (gamepad1.dpad_down) {
                left.setPower(-1);
            } else {
                left.setPower(0);
            }

//            if (gamepad1.left_bumper) {
//                left1.setPower(1);
//            } else if (gamepad1.right_bumper) {
//                left1.setPower(-1);
//            } else {
//                left1.setPower(0);
//            }
        }
    }
}