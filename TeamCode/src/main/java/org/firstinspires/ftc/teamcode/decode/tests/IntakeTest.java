package org.firstinspires.ftc.teamcode.decode.tests;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.robot.flywheels.SingleFlywheel;
import org.firstinspires.ftc.teamcode.core.robot.intakes.MotorIntake;
import org.firstinspires.ftc.teamcode.core.robot.transfers.pushers.ServoPusher;
import org.firstinspires.ftc.teamcode.decode.robot.Aslan;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "Intake Test", group = "Robot")
public class IntakeTest extends NextFTCOpMode {
    TrajectoryActionBuilder move;
    Command driveCommand;

    ElapsedTime timer = new ElapsedTime();

    public IntakeTest() {
        addComponents(
                new SubsystemComponent(Aslan.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onStartButtonPressed() {
        timer.reset();
        MotorIntake.INSTANCE.forward.update();
    }

    @Override
    public void onUpdate() {
        if (timer.seconds() > 5) {
            ServoPusher.INSTANCE.on.update();
        }
        MotorIntake.INSTANCE.forward.update();
        SingleFlywheel.INSTANCE.on.update();
        telemetry.update();
    }
}