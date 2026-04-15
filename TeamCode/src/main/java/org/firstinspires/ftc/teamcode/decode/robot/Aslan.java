package org.firstinspires.ftc.teamcode.decode.robot;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.core.robot.Robot;
import org.firstinspires.ftc.teamcode.core.robot.intakes.MotorIntake;
import org.firstinspires.ftc.teamcode.core.robot.transfers.pushers.ServoPusher;
import org.firstinspires.ftc.teamcode.decode.robot.subsystems.Launcher;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.units.Angle;
import dev.nextftc.extensions.pedro.TurnTo;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

public class Aslan extends Robot {
    public static final Aslan INSTANCE = new Aslan();

    public Pose goal;

    public Aslan() {
        super(
//                Launcher.INSTANCE,
                MotorIntake.INSTANCE
        );
    }

    @Override
    public void initialize() {
        super.initialize();
        setGoal();
    }

    private void setGoal() {
        switch (super.allianceColor) {
            case "blue":
                goal = new Pose(12, 135);
                break;
            case "red":
                goal = new Pose(132, 135);
                break;
        }
    }

    public void turnToPose(Pose targetPose) {
        Command turn = new TurnTo(Angle.fromRad(Math.atan2(
                targetPose.getY() - follower().getPose().getY(),
                targetPose.getX() - follower().getPose().getX()
        )));

        turn.schedule();
    }
}