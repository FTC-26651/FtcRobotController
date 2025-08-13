package org.firstinspires.ftc.teamcode.robot.parts.location;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class IMU {
    com.qualcomm.robotcore.hardware.IMU imu;

    private final LinearOpMode linearOpMode;

    // Used to decide which sensors to trust when they report different locations
    // Lower number is more accurate
    static double accuracy = 0; // The imu should always be correct

    YawPitchRollAngles orientation;



    public IMU(LinearOpMode LOM) {
        linearOpMode = LOM;
    }

    public void init() {
        imu = this.linearOpMode.hardwareMap.get(com.qualcomm.robotcore.hardware.IMU.class, "imu");

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.LEFT;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu.initialize(new com.qualcomm.robotcore.hardware.IMU.Parameters(orientationOnRobot));

        orientation = imu.getRobotYawPitchRollAngles();
    }

    public void resetYaw() {
        imu.resetYaw();
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getYaw() {
        orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getYaw(AngleUnit.DEGREES);
    }
    public double getPitch() {
        orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getPitch(AngleUnit.DEGREES);
    }
    public double getRoll() {
        orientation = imu.getRobotYawPitchRollAngles();
        return orientation.getRoll(AngleUnit.DEGREES);
    }
}
