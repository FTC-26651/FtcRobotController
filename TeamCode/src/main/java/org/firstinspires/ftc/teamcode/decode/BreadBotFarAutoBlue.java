package org.firstinspires.ftc.teamcode.decode;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.decode.robot.BreadBot;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "Bread Bot Move", group = "Robot")
public class BreadBotFarAutoBlue extends NextFTCOpMode {
    TrajectoryActionBuilder move;

    public BreadBotFarAutoBlue() {
        addComponents(
                new SubsystemComponent(BreadBot.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onInit() {
        move = BreadBot.INSTANCE.drive.actionBuilder(new Pose2d(4, -65, Math.toRadians(0.0)))
                .splineTo(new Vector2d(4, -42), Math.toRadians(90));
    }

    @Override
    public void onStartButtonPressed() {
        Action trajectoryActionChosen = move.build();

        Actions.runBlocking(trajectoryActionChosen);
    }
}

