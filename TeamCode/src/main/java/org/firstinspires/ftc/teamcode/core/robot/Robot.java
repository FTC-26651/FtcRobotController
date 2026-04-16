package org.firstinspires.ftc.teamcode.core.robot;

import com.pedropathing.geometry.Pose;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.subsystems.SubsystemGroup;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

public class Robot extends SubsystemGroup {
    protected String allianceColor;

    public Robot(Subsystem... subsystems) {
        super(subsystems);
    }

    public void init() {
        setAllianceColor(follower().getPose().getX() < 72 ? "blue" : "red");
    }

    public void setStartingPose(Pose pose) {
        follower().setStartingPose(pose);
    }

    public void setAllianceColor(String color) {
        allianceColor = color;
    }
}
