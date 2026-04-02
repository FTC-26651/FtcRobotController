package org.firstinspires.ftc.teamcode.core.robot.util;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;

import org.firstinspires.ftc.teamcode.core.CommandFactory;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dev.nextftc.extensions.pedro.FollowPath;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

public class PathParser {
    private static String filePath = "paths.yaml"; // Default to paths.yaml if nothing else is provided
    private static final Yaml yaml = new Yaml();

    private static Map<String, Object> root;

    private static final Map<String, CommandFactory> pathCommands = new HashMap<>();

    private static Pose startPose;
    private static Pose endPose;
    private static Pose trueStartPose;

    private static Pose extractPose(Map<String, Object> map) {
        double x = ((Number) Objects.requireNonNull(map.get("x"))).doubleValue();
        double y = ((Number) Objects.requireNonNull(map.get("y"))).doubleValue();
        return new Pose(x, y);
    }

    private static Pose extractStartPose(Map<String, Object> root) {
        Map<String, Object> startPoint = (Map<String, Object>) root.get("startPoint");
        assert startPoint != null;
        return extractPose(startPoint);
    }

    private static void applyHeadingInterpolation(PathBuilder builder, Map<String, Object> endPoint) {
        String heading = (String) endPoint.get("heading");

        /*
         * There are different rules depending on what type of heading we're using.
         * We switch over the three kinds and adjust the heading based on that.
         */
        switch (Objects.requireNonNull(heading)) {
            case "constant":
                double deg = ((Number) Objects.requireNonNull(endPoint.get("degrees"))).doubleValue();
                builder.setConstantHeadingInterpolation(Math.toRadians(deg));
                break;

            case "tangential":
                builder.setTangentHeadingInterpolation();
                Object reverse = endPoint.get("reverse");
                if (reverse instanceof Boolean && (Boolean) reverse) {
                    builder.setReversed();
                }
                break;

            case "linear":
                double startDeg = ((Number) Objects.requireNonNull(endPoint.get("startDeg"))).doubleValue();
                double endDeg = ((Number) Objects.requireNonNull(endPoint.get("endDeg"))).doubleValue();
                builder.setLinearHeadingInterpolation(
                        Math.toRadians(startDeg),
                        Math.toRadians(endDeg)
                );
                break;

            // no specified heading interpolation
            default:
                break;
        }
    }

    public static void parse() {
        root = yaml.load(filePath);
        List<Map<String, Object>> lines = (List<Map<String, Object>>) root.get("lines");

        /*
         * Check to see if the pose has been initialized or not. If it's not been initialized it'll start at 0, 0
         * We check to see if it is under 1 on both x and y because of floating point errors
         * and on the off-chance that it got moved a bit before we get to this point in the code
         */
        if (follower().getPose().getX() < 1 && follower().getPose().getY() < 1) {
            trueStartPose = extractStartPose(root);
        } else {
            trueStartPose = follower().getPose();
        }
        startPose = trueStartPose;

        assert lines != null;
        for (Map<String, Object> line : lines) {
            String id = (String) line.get("id"); // Keep the id in case we want to use the sequence
            String name = (String) line.get("name");

            Map<String, Object> endPoint = (Map<String, Object>) line.get("endPoint");
            List<Map<String, Object>> controlPoints =
                    (List<Map<String, Object>>) line.get("controlPoints");

            // Use the command factory to build the paths
            CommandFactory factory = args -> {
                PathBuilder builder = follower().pathBuilder();

                assert endPoint != null;
                endPose = extractPose(endPoint);

                // If there are no control points, we use a BezierLine. Otherwise we use a Bezier Curve
                assert controlPoints != null;
                if (controlPoints.isEmpty()) {
                    builder.addPath(new BezierLine(startPose, endPose));
                } else {
                    List<Pose> poses = new ArrayList<>();

                    poses.add(startPose);
                    for (Map<String, Object> controlPoint : controlPoints) {
                        poses.add(extractPose(controlPoint));
                    }
                    poses.add(endPose);

                    builder.addPath(new BezierCurve(poses.toArray(new Pose[0])));
                }

                applyHeadingInterpolation(builder, endPoint);

                boolean holdEnd = (boolean) args.getOrDefault("hold end", follower().constants.automaticHoldEnd);
                double  maxPower = (double) args.getOrDefault("max power", follower().getMaxPowerScaling());

                return new FollowPath(builder.build(), holdEnd, maxPower);
            };

            pathCommands.put(name, factory);
            startPose = endPose;
        }
    }

    public static void setFilePath(String path) {
        filePath = path;
    }

    public static Map<String, CommandFactory> getPathCommands() {
        return pathCommands;
    }

    public static Pose getTrueStartPose() {
        return trueStartPose;
    }
}
