package org.firstinspires.ftc.teamcode.decode.tests;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "Odo Test", group = "Robot")
public class OdoTest extends LinearOpMode {
    GoBildaPinpointDriver odo;

    @Override
    public void runOpMode() {
        odo = this.hardwareMap.get(GoBildaPinpointDriver.class,"odo");

        // Variables, change these depending on the robot
        odo.setOffsets(-84.0, -168.0, DistanceUnit.MM);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        odo.resetPosAndIMU();

        telemetry.addLine("Test Ready.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            odo.update();

            telemetry.addData("X: ", odo.getPosX(DistanceUnit.INCH));
            telemetry.addData("Y: ", odo.getPosY(DistanceUnit.INCH));

            telemetry.update();
        }
    }
}