package org.firstinspires.ftc.teamcode.core.robot;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.teamcode.core.CommandFactory;
import org.firstinspires.ftc.teamcode.core.robot.transfers.pushers.ServoPusher;

import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.subsystems.SubsystemGroup;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.ftc.ActiveOpMode;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

public class Robot extends SubsystemGroup {
    public static final Robot INSTANCE = new Robot();

    Follower follower;

    protected String allianceColor;

    private static Command getCommand(PathChain path) {
        return new FollowPath(path);
    }
    private static Command getCommand(PathChain path, boolean holdEnd) {
        return new FollowPath(path, holdEnd);
    }
    private static Command getCommand(PathChain path, boolean holdEnd, double maxPower) {
        return new FollowPath(path, holdEnd, maxPower);
    }

    public Robot() {
        super();

        this.follower = follower();
    }

    public Robot(Subsystem... subsystems) {
        super(subsystems);

        this.follower = follower();
    }

    @Override
    public void initialize() {
        addPaths();
    }

    public void setStartingPose(Pose pose) {
        follower.setStartingPose(pose);
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
