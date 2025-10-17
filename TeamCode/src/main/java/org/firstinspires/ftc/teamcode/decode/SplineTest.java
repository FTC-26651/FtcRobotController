package org.firstinspires.ftc.teamcode.decode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.robot.drivetrain.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.NextFTCOpMode;

@Autonomous(name = "Spline Test", group = "Robot")
public class SplineTest extends NextFTCOpMode {
    private final Pose2d startPose = new Pose2d(9.0, 111.0, Math.toRadians(-90.0));
    private final Pose2d scorePose = new Pose2d(16.0, 128.0, Math.toRadians(-45.0));

    MecanumDrive drive;
    Command driveCommand;

    @Override
    public void onInit() {
        drive = new MecanumDrive(hardwareMap, startPose);

        driveCommand = drive.commandBuilder(startPose)
                .splineToLinearHeading(scorePose, Math.PI / 2)
                .build();

        // 2.splineTo(scorePose.position, scorePose.heading)
    }

    @Override
    public void onStartButtonPressed() {
        driveCommand.schedule();
    }
}
