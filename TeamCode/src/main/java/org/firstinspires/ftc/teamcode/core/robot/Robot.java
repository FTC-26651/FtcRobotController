package org.firstinspires.ftc.teamcode.core.robot;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.CommandFactory;
import org.firstinspires.ftc.teamcode.core.robot.transfers.pushers.ServoPusher;

import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.subsystems.SubsystemGroup;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.ftc.ActiveOpMode;

public class Robot extends SubsystemGroup {
    public Commands commands = new Commands();
    Follower follower;

    private final Paths paths;

    private static Command getCommand(PathChain path) {
        return new FollowPath(path);
    }
    private static Command getCommand(PathChain path, boolean holdEnd) {
        return new FollowPath(path, holdEnd);
    }
    private static Command getCommand(PathChain path, boolean holdEnd, double maxPower) {
        return new FollowPath(path, holdEnd, maxPower);
    }

    public Robot(Follower follower) {
        super(
                ServoPusher.INSTANCE
        );

        ServoPusher.INSTANCE.initialize();

        this.follower = follower;
        paths = new Paths(follower);
        addPaths();
    }

    public Robot(Follower follower, Subsystem... subsystems) {
        super(subsystems);

        this.follower = follower;
        paths = new Paths(follower);
        addPaths();
    }

    public void setStartingPose(Pose pose) {
        follower.setStartingPose(pose);
    }

    // When using pedro's built in visualizer (https://visualizer.pedropathing.com/) export the path
    // as java code. Then select the export mode of Coordinates Only. Paste the paths in the paths
    // class, editing as needed. Once done, go to the addPaths function, and add the paths to the map.
    // Follow the scheme of "pathName", getCommand(paths.pathName). If you want, you can adjust HoldEnd
    // and MaxPower. Once done, you can call the paths in the yaml.
    private void addPaths() {
        Map<String, CommandFactory> pathCommands = Map.of(
                "follow path 1", args -> getCommand(paths.Path1),
                "follow path 2", args -> getCommand(paths.Path2),
                "follow path 3", args -> getCommand(paths.Path3),
                "go to first row", args -> getCommand(paths.FirstRow),
                "go to second row", args -> getCommand(paths.SecondRow),
                "go to third row", args -> getCommand(paths.ThirdRow),
                "go to launch zone", args -> getCommand(paths.LaunchZone),
                "go to park", args -> getCommand(paths.Park)
        );
        commands.addCommands(pathCommands);
    }

    private static class Paths {
        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;

        public PathChain FirstRow;
        public PathChain SecondRow;
        public PathChain ThirdRow;
        public PathChain LaunchZone;
        public PathChain Park;

        public Paths(Follower follower) {
            Path1 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(56.000, 8.000),
                                    new Pose(64.000, 28.098)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                    .build();

            Path2 = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(64.000, 28.098),
                                    new Pose(53.512, 34.683),
                                    new Pose(53.195, 44.244),
                                    new Pose(71.146, 30.732),
                                    new Pose(60.146, 57.122)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(90))
                    .build();

            Path3 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(60.146, 57.122),
                                    new Pose(65.341, 77.854)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            FirstRow = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(56.000, 8.000),
                                    new Pose(54.000, 35.000),
                                    new Pose(30.000, 35.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                    .build();

            SecondRow = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(60.000, 15.000),
                                    new Pose(53.000, 60.000),
                                    new Pose(30.000, 60.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(120), Math.toRadians(180))
                    .build();

            ThirdRow = follower.pathBuilder()
                    .addPath(
                            new BezierCurve(
                                    new Pose(60.000, 15.000),
                                    new Pose(53.000, 75.000),
                                    new Pose(30.000, 84.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(120), Math.toRadians(180))
                    .build();

            LaunchZone = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(30.000, 84.000),
                                    new Pose(60.000, 15.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(120))
                    .build();

            Park = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(60.000, 15.000),
                                    new Pose(39.000, 33.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(120), Math.toRadians(90))
                    .build();
        }
    }
}
