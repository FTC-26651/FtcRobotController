package org.firstinspires.ftc.teamcode.robot.parts.location;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.libs.Point;
import org.firstinspires.ftc.teamcode.libs.parts.GoBildaPinpointDriver;

public class ODO {
    GoBildaPinpointDriver odo;

    private final LinearOpMode linearOpMode;

    // Used to decide which sensors to trust when they report different locations
    // Lower number is more accurate
    static double accuracy = 1;

    private Point startPoint = new Point(0, 0);



    public ODO(LinearOpMode LOM) {
        linearOpMode = LOM;
    }

    public void init() {
        odo = linearOpMode.hardwareMap.get(GoBildaPinpointDriver.class,"odo");

        // Variables, change these depending on the robot
        odo.setOffsets(-84.0, -168.0, DistanceUnit.MM);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        odo.resetPosAndIMU();
    }

    public void setStartingPoint(Point point) {
        startPoint = point;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void updatePos(double x_axis, double y_axis) {
        startPoint.movePoint(startPoint.getX() + x_axis, startPoint.getY() + y_axis);
    }

    public Point getPos() {
        odo.update();

        return new Point(
                startPoint.getX() + odo.getPosX(DistanceUnit.INCH),
                startPoint.getY() + odo.getPosY(DistanceUnit.INCH)
        );
    }
}
