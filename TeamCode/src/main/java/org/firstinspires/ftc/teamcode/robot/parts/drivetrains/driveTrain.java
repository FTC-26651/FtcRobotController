package org.firstinspires.ftc.teamcode.robot.parts.drivetrains;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class driveTrain {
    public String type;
    protected LinearOpMode linearOpMode;

    public driveTrain(LinearOpMode LOM) {
        linearOpMode = LOM;
    }

    public abstract void init();

    public abstract void move(double x_axis, double y_axis, double tilt);
    public abstract void stop();

    public abstract void turnTo(double target);

    public abstract void resetEncoders();
    public abstract int getEncoder(String name);

    public abstract void setPower(double power);
    public abstract double getPower();
}
