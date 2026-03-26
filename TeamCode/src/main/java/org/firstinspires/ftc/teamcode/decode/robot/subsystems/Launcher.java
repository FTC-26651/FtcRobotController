package org.firstinspires.ftc.teamcode.decode.robot.subsystems;

import com.pedropathing.geometry.Pose;

import dev.nextftc.control2.feedback.PIDCoefficients;
import dev.nextftc.control2.feedback.PIDController;
import dev.nextftc.control2.feedforward.SimpleFFCoefficients;
import dev.nextftc.control2.feedforward.SimpleFeedforward;
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

    private PIDController pidController;
    private SimpleFeedforward feedforward;

    private double currentVelocity = 0;
    private double targetVelocity = 0;
    
    double[][] powersData = {
            {0.0, 0.0}
    };

    private double getDistance() {
        Pose robotPose = follower().getPose();
        return robotPose.distanceFrom(targetPose);
    }

    public final Command off = new LambdaCommand()
            .setUpdate(() -> targetVelocity = 0.0)
            .requires(this)
            .named("Flywheel Off");
    public final Command on = new LambdaCommand()
            .setUpdate(() -> targetVelocity = powers.get(getDistance()))
            .requires(this)
            .named("Launcher On");

    public Command setPower(double power) {
        return new LambdaCommand()
                .setUpdate(() -> targetVelocity = power)
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

        pidController = new PIDController(0.001, 0, 0);
        feedforward = new SimpleFeedforward(0.003, 0.08, 0.00);

        for (double[] data : powersData) {
            powers.putIfAbsent(data[0], data[1]);
        }
    }

    @Override
    public void periodic() {
        currentVelocity = motor.getVelocity();
        motor.setPower(
                pidController.calculateFromReference(targetVelocity, currentVelocity) +
                feedforward.calculate(targetVelocity)
        );
    }
}
