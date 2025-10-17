package org.firstinspires.ftc.teamcode.decode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.core.robot.drivetrain.MecanumDrive;

@TeleOp(name = "Drive Test", group = "Robot")
public class DriveTest extends LinearOpMode {
    private final Pose2d initialPose = new Pose2d(9.0, 111.0, Math.toRadians(-90.0));
    MecanumDrive drive;

    public void move(double x_axis, double y_axis, double tilt) {
        double leftBackPower = Range.clip(y_axis + x_axis + tilt, -1.0, 1.0);
        double rightBackPower = Range.clip(y_axis - x_axis - tilt, -1.0, 1.0);
        double leftFrontPower = Range.clip(y_axis - x_axis + tilt, -1.0, 1.0);
        double rightFrontPower = Range.clip(y_axis + x_axis - tilt, -1.0, 1.0);

        drive.leftBack.setPower(leftBackPower);
        drive.rightBack.setPower(rightBackPower);
        drive.leftFront.setPower(leftFrontPower);
        drive.rightBack.setPower(rightFrontPower);
    }

    @Override
    public void runOpMode() {
        drive = new MecanumDrive(hardwareMap, initialPose);

        telemetry.addLine("Robot Ready.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            move(gamepad1.left_stick_x, gamepad1.left_stick_y, -1 * gamepad1.right_stick_x);
        }
    }
}
