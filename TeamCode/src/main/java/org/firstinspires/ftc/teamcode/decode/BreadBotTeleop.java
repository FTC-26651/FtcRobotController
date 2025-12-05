package org.firstinspires.ftc.teamcode.decode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.robot.intakes.ServoIntake;
import org.firstinspires.ftc.teamcode.decode.robot.BreadBot;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "Bread Bot Teleop", group = "Robot")
public class BreadBotTeleop extends NextFTCOpMode {
    Command driverControlled;
    Command intakeOn;

    public BreadBotTeleop() {
        addComponents(
                new SubsystemComponent(BreadBot.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onStartButtonPressed() {
        driverControlled = BreadBot.INSTANCE.drive.driveCommand();
        driverControlled.schedule();

        intakeOn = ServoIntake.INSTANCE.on;
        intakeOn.schedule();
    }

    @Override
    public void onUpdate() {
        driverControlled.update();
        intakeOn.update();
    }
}
