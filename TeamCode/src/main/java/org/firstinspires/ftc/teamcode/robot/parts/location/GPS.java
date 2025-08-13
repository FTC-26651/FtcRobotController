package org.firstinspires.ftc.teamcode.robot.parts.location;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.libs.Point;

public class GPS {
    private final LinearOpMode linearOpMode;

    double angle;
    Point location;

    IMU imu;
    ODO odo;

    public GPS(LinearOpMode LOM) {
        linearOpMode = LOM;
        imu = new IMU(LOM);
        odo = new ODO(LOM);
    }

    public void init() {
        imu.init();
        odo.init();
    }

    public Point getLocation() {
        location = odo.getPos();
        return location;
    }
    public double getRotation() {
        angle = imu.getYaw();
        return angle;
    }
}
