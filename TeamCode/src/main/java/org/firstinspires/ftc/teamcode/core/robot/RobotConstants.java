package org.firstinspires.ftc.teamcode.core.robot;

import com.pedropathing.geometry.Pose;

public class RobotConstants {
    public static Pose robotPose;

    public static void setRobotPose(Pose pose) {
        robotPose = new Pose(pose.getX(), pose.getY(), pose.getHeading());
    }

    public static Pose getRobotPose() {
        return robotPose;
    }
}