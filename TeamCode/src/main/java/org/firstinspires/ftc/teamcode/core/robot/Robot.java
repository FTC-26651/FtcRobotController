package org.firstinspires.ftc.teamcode.core.robot;

import com.pedropathing.geometry.Pose;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.subsystems.SubsystemGroup;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

public class Robot extends SubsystemGroup {
    public static final Robot INSTANCE = new Robot();

    protected String allianceColor;

    public Robot() {
        super();
    }

    public Robot(Subsystem... subsystems) {
        super(subsystems);
    }

    @Override
    public void initialize() {
        setAllianceColor(follower().getPose().getX() < 72 ? "blue" : "red");
    }

    public void setStartingPose(Pose pose) {
        follower().setStartingPose(pose);
    }

    public void setAllianceColor(String color) {
        allianceColor = color;
    }
}
