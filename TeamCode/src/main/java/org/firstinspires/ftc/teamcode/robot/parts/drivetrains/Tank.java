package org.firstinspires.ftc.teamcode.robot.parts.drivetrains;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.libs.Point;
import org.firstinspires.ftc.teamcode.libs.parts.extensions.LionsDcMotorEx;
import org.firstinspires.ftc.teamcode.libs.pidLib;
import org.firstinspires.ftc.teamcode.robot.parts.location.GPS;

public class Tank extends driveTrain {
    public String type = "Tank";
    double speed;

    DcMotorEx LD = null;
    DcMotorEx RD = null;

    LionsDcMotorEx leftDrive;
    LionsDcMotorEx rightDrive;

    public Tank(LinearOpMode LOM) {
        super(LOM);
    }
    
    public void init() {
        LD = this.linearOpMode.hardwareMap.get(DcMotorEx.class, "left_front_drive");
        RD = this.linearOpMode.hardwareMap.get(DcMotorEx.class, "right_front_drive");

        leftDrive = new LionsDcMotorEx(LD);
        rightDrive = new LionsDcMotorEx(RD);

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftDrive.setPid(0.05, 0.001, 0.001);
        rightDrive.setPid(0.05, 0.001, 0.001);
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

        leftDrive.setPower(left * speed);
        rightDrive.setPower(right * speed);
    }
    
    public void stop() {
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }
    
    public void resetEncoders() {
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    
    public int getEncoder(String name) {
        switch(name) {
            case "left":
                return leftDrive.getCurrentPosition();
            case "right":
                return rightDrive.getCurrentPosition();
            default:
                return 0;
        }
    }

    public void turnTo(GPS gps, double target) {
        pidLib pid = new pidLib(1, 1, 1);

        double power = pid.getPid(target, gps.getRotation());

        leftDrive.setPower(power);
        rightDrive.setPower(-1 * power);
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
            
        leftDrive.setPower(power);
        rightDrive.setPower(power);
    }
}
