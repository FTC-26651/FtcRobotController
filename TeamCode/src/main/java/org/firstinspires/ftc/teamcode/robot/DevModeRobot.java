package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.libs.parts.extensions.LionsDcMotorEx;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robot.parts.drivetrains.Mechanum;
import org.firstinspires.ftc.teamcode.robot.parts.drivetrains.Telemetric;
import org.firstinspires.ftc.teamcode.robot.parts.location.GPS;

public class DevModeRobot extends Robot {
    LionsDcMotorEx armMotorEx   = null;
    LionsDcMotorEx extensionMotor = null;

    public Telemetric driveTrain;
    public GPS gps;

    public DevModeRobot(LinearOpMode LOM) {
        super(LOM);

        driveTrain = new Telemetric(linearOpMode);
        gps = new GPS(linearOpMode);
    }

    public void initRobotEncoders() {
        armMotorEx.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotorEx.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void initRobot() {
        driveTrain.init();
        gps.init();
    }
}
