package org.firstinspires.ftc.teamcode.decode;

import static dev.nextftc.bindings.Bindings.*;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.robot.flywheels.SingleFlywheel;
import org.firstinspires.ftc.teamcode.core.robot.intakes.ServoIntake;
import org.firstinspires.ftc.teamcode.core.robot.transfers.wheels.SingleMotorTransfer;
import org.firstinspires.ftc.teamcode.decode.robot.BreadBot;

import dev.nextftc.bindings.Button;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "Bread Bot Teleop 1P", group = "Robot")
public class BreadBotTeleop1P extends NextFTCOpMode {
    Command driverControlled;
    Button flywheel;

    boolean flywheelToggle = false;

    public BreadBotTeleop1P() {
        addComponents(
                new SubsystemComponent(BreadBot.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onStartButtonPressed() {
        driverControlled = BreadBot.INSTANCE.drive.driveCommand();

        flywheel = button(() -> gamepad1.b);
    }

    @Override
    public void onUpdate() {
        driverControlled.schedule();

        if (gamepad1.a) {
            SingleMotorTransfer.INSTANCE.forward.update();
        } else if (gamepad1.y) {
            SingleMotorTransfer.INSTANCE.back.update();
        } else {
            SingleMotorTransfer.INSTANCE.off.update();
        }

        if (gamepad1.dpad_down) {
            SingleFlywheel.INSTANCE.off.update();
        } else if (gamepad1.dpad_up) {
            SingleFlywheel.INSTANCE.on.update();
        }

        if (gamepad1.left_bumper) {
            ServoIntake.INSTANCE.off.update();
        } else if (gamepad1.right_bumper) {
            ServoIntake.INSTANCE.on.update();
        }

        telemetry.update();
        SingleMotorTransfer.INSTANCE.periodic();
    }
}
