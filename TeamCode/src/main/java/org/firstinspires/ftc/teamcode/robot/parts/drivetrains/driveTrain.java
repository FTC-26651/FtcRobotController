package org.firstinspires.ftc.teamcode.robot.parts.drivetrains;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.libs.Point;
import org.firstinspires.ftc.teamcode.robot.parts.location.GPS;

public abstract class driveTrain {
    public String type;
    protected LinearOpMode linearOpMode;

    public driveTrain(LinearOpMode LOM) {
        linearOpMode = LOM;
    }

    public abstract void init();

    public void runEveryLoop() {}

    public abstract void move(double x_axis, double y_axis, double tilt);
    public abstract void stop();

    public abstract void turnTo(GPS gps, double target);
    public abstract void moveToPoint(GPS gps, Point target);

    public abstract void resetEncoders();
    public abstract int getEncoder(String name);

    public abstract void setPower(double power);
    public abstract double getPower();

}
