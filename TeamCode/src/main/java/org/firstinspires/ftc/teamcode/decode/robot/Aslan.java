package org.firstinspires.ftc.teamcode.decode.robot;

import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.core.robot.Robot;
import org.firstinspires.ftc.teamcode.core.robot.intakes.MotorIntake;
import org.firstinspires.ftc.teamcode.core.robot.transfers.pushers.ServoPusher;
import org.firstinspires.ftc.teamcode.decode.robot.subsystems.Launcher;


public class Aslan extends Robot {
    public Aslan(Follower follower) {
        super(follower,
                Launcher.INSTANCE,
                MotorIntake.INSTANCE,
                ServoPusher.INSTANCE
        );
    }
}