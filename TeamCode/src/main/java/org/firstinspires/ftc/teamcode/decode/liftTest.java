package org.firstinspires.ftc.teamcode.decode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Lift Test", group = "Robot")
public class liftTest extends LinearOpMode {
    DcMotorEx left;
    DcMotorEx right;

    int pos = 0;

    @Override
    public void runOpMode() {
        left = this.hardwareMap.get(DcMotorEx.class, "left");
        right = this.hardwareMap.get(DcMotorEx.class, "right");

        left.setDirection(DcMotor.Direction.FORWARD);
        right.setDirection(DcMotor.Direction.FORWARD);


        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addLine("Test Ready.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                left.setPower(1);
                right.setPower(1);
                pos = left.getCurrentPosition();
            } else if (gamepad1.dpad_down) {
                left.setPower(-1);
                right.setPower(-1);
                pos = left.getCurrentPosition();
            } else {
                //left.setPower(getPid(pos, left.getCurrentPosition()));
            }
        }
    }

    double error;
    double integralSum;
    double derivative;
    double lastError;
    double out;

    double p = 0.015;
    double i = 0.001;
    double d = 0.0001;

    ElapsedTime timer = new ElapsedTime();

    public double getPid(double target, double real) {
        error = target - real;
        integralSum = integralSum + (error * timer.seconds());
        derivative = (error - lastError) / timer.seconds();

        out = (p * error) + (i * integralSum) + (d * derivative);

        lastError = error;

        timer.reset();

        return out;
    }
}

