package org.firstinspires.ftc.teamcode.robot.parts.location;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.libs.Point;

public class location {
    private final LinearOpMode linearOpMode;

    double angle;
    Point location;

    imu imu;
    odo odo;

    public location(LinearOpMode LOM) {
        linearOpMode = LOM;
        imu = new imu(LOM);
        odo = new odo(LOM);
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
