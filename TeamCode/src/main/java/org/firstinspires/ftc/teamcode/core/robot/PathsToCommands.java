package org.firstinspires.ftc.teamcode.core.robot;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.extensions.pedro.FollowPath;

public class PathsToCommands {
    Paths paths;
    public static Command getCommand(PathChain path) {
        return new FollowPath(path);
    }
    public static Command getCommand(PathChain path, boolean holdEnd) {
        return new FollowPath(path, holdEnd);
    }
    public static Command getCommand(PathChain path, boolean holdEnd, double maxPower) {
        return new FollowPath(path, holdEnd, maxPower);
    }

    public PathsToCommands(Follower follower) {
        paths = new Paths(follower);
    }

    // When using pedro's built in visualizer (https://visualizer.pedropathing.com/) export the path
    // as java code. Then select the export mode of Coordinates Only. Paste the paths in the paths
    // class, editing as needed. Once done, go to the addPaths function, and add the paths to the map.
    // Follow the scheme of "pathName", getCommand(paths.pathName). If you want, you can adjust HoldEnd
    // and MaxPower. Once done, you can call the paths in the yaml.
    public void addPaths() {
        Map<String, Command> pathCommands = Map.of(
                "ExamplePath", getCommand(paths.ExamplePath)
        );
        Commands.addCommands(pathCommands);
    }

    public static class Paths {
        public PathChain ExamplePath;

        public Paths(Follower follower) {
            ExamplePath = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(56.000, 8.000),
                                    new Pose(64.000, 28.098)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                    .build();
        }
    }
}
