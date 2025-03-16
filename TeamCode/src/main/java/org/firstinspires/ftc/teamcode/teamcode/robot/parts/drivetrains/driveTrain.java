package org.firstinspires.ftc.teamcode.robot.parts.drivetrains;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class driveTrain {
    protected LinearOpMode OpMode;

    public driveTrain(LinearOpMode LOM) {
        OpMode = LOM;
    }

    public abstract void init();

    public abstract void move(double x_axis, double y_axis, double tilt);
    public abstract void stop();

    public abstract void resetEncoders();
    public abstract int getEncoder(String name);
}
