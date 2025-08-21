package org.firstinspires.ftc.teamcode.robot.parts.drivetrains;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.libs.Point;
import org.firstinspires.ftc.teamcode.libs.parts.extensions.LionsDcMotorEx;
import org.firstinspires.ftc.teamcode.libs.pidLib;
import org.firstinspires.ftc.teamcode.robot.parts.location.GPS;

public class Telemetric extends driveTrain {
    public String type = "Telemetric";
    double speed;

    Double leftDrive;
    Double rightDrive;



    public Telemetric(LinearOpMode LOM) {
        super(LOM);
    }

    public void init() {

    }

    public void runEveryLoop() {
        linearOpMode.telemetry.update();
    }

    public void setPower(double power) {
        speed = power;
    }

    public double getPower() {
        return speed;
    }

    public void move(double x_axis, double y_axis, double tilt) {
        double left = Range.clip(y_axis  + tilt, -1.0, 1.0);
        double right = Range.clip(y_axis - tilt, -1.0, 1.0);

        linearOpMode.telemetry.addData("LeftPower: ", left * speed);
        linearOpMode.telemetry.addData("RightPower: ", right * speed);
    }

    public void stop() {
        linearOpMode.telemetry.addData("LeftPower: ", 0);
        linearOpMode.telemetry.addData("RightPower: ", 0);
    }

    public void resetEncoders() {

    }

    public int getEncoder(String name) {
        return 0;
    }

    public void turnTo(GPS gps, double target) {
        pidLib pid = new pidLib(1, 1, 1);

        double power = pid.getPid(target, gps.getRotation());

        linearOpMode.telemetry.addData("LeftPower: ", power);
        linearOpMode.telemetry.addData("RightPower: ", -1 * power);
    }

    public void moveToPoint(GPS gps, Point target) {
        double targetAngle = Math.atan(target.getY() - gps.getLocation().getY() / target.getX() - gps.getLocation().getX());
        turnTo(gps, targetAngle);


        pidLib pid = new pidLib(1, 1, 1);
        double dist = Math.sqrt(
                Math.pow(target.getY() - gps.getLocation().getY(), 2) +
                Math.pow(target.getX() - gps.getLocation().getX(), 2)
        );

        double power = pid.getPid(dist);

        linearOpMode.telemetry.addData("LeftPower: ", power);
        linearOpMode.telemetry.addData("RightPower: ", power);
    }
}
