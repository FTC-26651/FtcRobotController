package org.firstinspires.ftc.teamcode.decode.robot.subsystems;

import com.pedropathing.geometry.Pose;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control2.util.InterpolatingMap;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

public class Launcher implements Subsystem {
    public static final Launcher INSTANCE = new Launcher();
    private Launcher() { }

    private final MotorEx motor = new MotorEx("flywheel_motor");

    private final InterpolatingMap<Double> powers = InterpolatingMap.spline();
    private Pose targetPose = new Pose(0,0, Math.toRadians(0));

    private ControlSystem controller;

    double[][] powersData = {
            {0.0, 0.0}
    };

    private double getDistance() {
        Pose robotPose = follower().getPose();
        return robotPose.distanceFrom(targetPose);
    }

    private void setControllerGoal(double goal) {
        controller.setGoal(new KineticState(0.0, goal));
    }

    public final Command off = new LambdaCommand()
            .setUpdate(() -> setControllerGoal(0.0))
            .requires(this)
            .named("Flywheel Off");
    public final Command on = new LambdaCommand()
            .setUpdate(() -> setControllerGoal(powers.get(getDistance())))
            .requires(this)
            .named("Launcher On");

    public Command setPower(double power) {
        return new LambdaCommand()
                .setUpdate(() -> setControllerGoal(power))
                .requires(this)
                .named("Launcher On To Power");
    }

    public Command useLauncher(String action) {
        return new LambdaCommand().setStart(() -> {
            switch (action) {
                case "on":
                    on.schedule();
                case "off":
                    off.schedule();
            }
        });
    }

    public void setTarget(Pose target) {
        targetPose = target;
    }

    @Override
    public void initialize() {
        motor.setPower(0);

        controller = ControlSystem.builder()
                .velPid(0.001, 0, 0)
                .basicFF(0.003, 0.08, 0.00)
                .build();
        controller.setGoal(new KineticState(0.0, 0.0));

        for (double[] data : powersData) {
            powers.putIfAbsent(data[0], data[1]);
        }
    }

    @Override
    public void periodic() {
        motor.setPower(controller.calculate(new KineticState(
                motor.getCurrentPosition(),
                motor.getVelocity()))
        );
    }
}
