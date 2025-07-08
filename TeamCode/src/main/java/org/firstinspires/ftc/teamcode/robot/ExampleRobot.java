package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.robot.extensions.LionsDcMotorEx;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.robot.parts.drivetrains.Mechanum;

public class ExampleRobot extends Robot {
    DcMotorEx armMotor = null;
    Servo     wrist    = null;
    Servo     claw     = null;

    LionsDcMotorEx armMotorEx   = null;
    LionsDcMotorEx extensionMotor = null;
    LionsDcMotorEx frontLeftDrive   = null;
    LionsDcMotorEx frontRightDrive   = null;
    LionsDcMotorEx backLeftDrive   = null;
    LionsDcMotorEx backRightDrive   = null;

    IMU imu;
    DistanceSensor distanceSensor;

    YawPitchRollAngles orientation;

    public Mechanum driveTrain;

    public ExampleRobot(LinearOpMode LOM) {
        super(LOM);
    }

    public void initRobotEncoders() {
        armMotorEx.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotorEx.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void initRobot() {
        armMotor = this.linearOpMode.hardwareMap.get(DcMotorEx.class, "left_arm");
        wrist = this.linearOpMode.hardwareMap.get(Servo.class, "wrist");
        claw = this.linearOpMode.hardwareMap.get(Servo.class, "claw");

        imu = this.linearOpMode.hardwareMap.get(IMU.class, "imu");
        distanceSensor = this.linearOpMode.hardwareMap.get(DistanceSensor.class, "dist");

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.LEFT;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        orientation = imu.getRobotYawPitchRollAngles();

        driveTrain = new Mechanum(linearOpMode);
    }


    public void runEveryLoop() {

    }
}
