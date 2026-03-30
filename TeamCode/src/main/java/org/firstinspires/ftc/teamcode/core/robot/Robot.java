package org.firstinspires.ftc.teamcode.core.robot;

import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.subsystems.SubsystemGroup;
import dev.nextftc.extensions.pedro.FollowPath;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

public class Robot extends SubsystemGroup {
    public static final Robot INSTANCE = new Robot();

    protected String allianceColor;

    public Robot() {
        super();

        addPaths();
    }

    public Robot(Subsystem... subsystems) {
        super(subsystems);

        addPaths();
    }

    public void setStartingPose(Pose pose) {
        follower().setStartingPose(pose);
    }

    public void setAllianceColor(String color) {
        allianceColor = color;
    }

    private void addPaths() {
        PathParser.parse();
        Commands.addCommands(PathParser.getPathCommands());
        setStartingPose(PathParser.getTrueStartPose());
    }
}
