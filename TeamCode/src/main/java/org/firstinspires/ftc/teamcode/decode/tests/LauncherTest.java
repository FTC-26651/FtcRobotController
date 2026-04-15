package org.firstinspires.ftc.teamcode.decode.tests;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.robot.Robot;
import org.firstinspires.ftc.teamcode.core.robot.drivetrain.Constants;
import org.firstinspires.ftc.teamcode.decode.robot.Aslan;
import org.firstinspires.ftc.teamcode.decode.robot.subsystems.Launcher;

import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;

@TeleOp(name = "Launcher Test", group = "Tests")
public class LauncherTest extends NextFTCOpMode {
    {
        addComponents(new PedroComponent(Constants::createFollower));
    }

    private Robot robot;
    double launcherPower = 0;

    @Override public void onInit() {
        robot = new Aslan();
        robot.setStartingPose(new Pose(56, 8, Math.toRadians(90)));
        robot.initialize();
    }
    @Override public void onWaitForStart() { }
    @Override public void onStartButtonPressed() { }
    @Override public void onUpdate() {
        if (Gamepads.gamepad1().dpadUp().toggleOnBecomesTrue().get()) {
            launcherPower += 100.0;
        } else if (Gamepads.gamepad1().dpadDown().toggleOnBecomesTrue().get()) {
            launcherPower -= 100.0;
        }
        Launcher.INSTANCE.setPower(launcherPower);
        robot.periodic();

        telemetry.addData("Current Power", launcherPower);
        telemetry.update();
    }
    @Override public void onStop() { }
}
