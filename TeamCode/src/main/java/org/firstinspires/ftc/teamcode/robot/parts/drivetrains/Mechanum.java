package org.firstinspires.ftc.teamcode.robot.parts.drivetrains;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.robot.extensions.LionsDcMotorEx;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class Mechanum extends driveTrain {
    double speed;

    DcMotorEx FLD = null;
    DcMotorEx FRD = null;
    DcMotorEx BLD = null;
    DcMotorEx BRD = null;

    LionsDcMotorEx frontLeftDrive;
    LionsDcMotorEx frontRightDrive;
    LionsDcMotorEx backLeftDrive;
    LionsDcMotorEx backRightDrive;

    public Mechanum(LinearOpMode LOM) {
        super(LOM);
    }

    public void init() {
        FLD = this.linearOpMode.hardwareMap.get(DcMotorEx.class, "left_front_drive");
        FRD = this.linearOpMode.hardwareMap.get(DcMotorEx.class, "right_front_drive");
        BLD = this.linearOpMode.hardwareMap.get(DcMotorEx.class, "left_back_drive");
        BRD = this.linearOpMode.hardwareMap.get(DcMotorEx.class, "right_back_drive");

        frontLeftDrive = new LionsDcMotorEx(FLD);
        frontRightDrive = new LionsDcMotorEx(FRD);
        backLeftDrive = new LionsDcMotorEx(BLD);
        backRightDrive = new LionsDcMotorEx(BRD);

        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeftDrive.setPid(0.05, 0.001, 0.001);
        frontRightDrive.setPid(0.05, 0.001, 0.001);
        backLeftDrive.setPid(0.05, 0.001, 0.001);
        backRightDrive.setPid(0.05, 0.001, 0.001);
    }

    public void setPower(double power) {
        speed = power;
    }

    public double getPower() {
        return speed;
    }

    public void move(double x_axis, double y_axis, double tilt) {
        double leftBackPower = Range.clip(y_axis + x_axis + tilt, -1.0, 1.0);
        double rightBackPower = Range.clip(y_axis - x_axis - tilt, -1.0, 1.0);
        double leftFrontPower = Range.clip(y_axis - x_axis + tilt, -1.0, 1.0);
        double rightFrontPower = Range.clip(y_axis + x_axis - tilt, -1.0, 1.0);

        backLeftDrive.setPower(leftBackPower * speed);
        backRightDrive.setPower(rightBackPower * speed);
        frontLeftDrive.setPower(leftFrontPower * speed);
        frontRightDrive.setPower(rightFrontPower * speed);
    }

    public void stop() {
        backLeftDrive.setPower(0);
        frontLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        frontRightDrive.setPower(0);
    }

    public void resetEncoders() {
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int getEncoder(String name) {
        switch(name) {
            case "fl":
                return frontLeftDrive.getCurrentPosition();
            case "fr":
                return frontRightDrive.getCurrentPosition();
            case "bl":
                return backLeftDrive.getCurrentPosition();
            case "br":
                return backRightDrive.getCurrentPosition();
            default:
                return 0;
        }
    }
}
