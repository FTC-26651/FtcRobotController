package org.firstinspires.ftc.teamcode.decode;

import static dev.nextftc.bindings.Bindings.*;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.robot.flywheels.SingleFlywheel;
import org.firstinspires.ftc.teamcode.core.robot.intakes.MotorIntake;
import org.firstinspires.ftc.teamcode.core.robot.transfers.pushers.ServoPusher;
import org.firstinspires.ftc.teamcode.decode.robot.Aslan;

import dev.nextftc.bindings.Button;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "Aslan Teleop", group = "Robot")
public class AslanTeleop extends NextFTCOpMode {
    Command driverControlled;
    Button flywheel;

    boolean flywheelToggle = false;

    public AslanTeleop() {
        addComponents(
                new SubsystemComponent(Aslan.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onStartButtonPressed() {
        driverControlled = Aslan.INSTANCE.drive.driveCommand();

        flywheel = button(() -> gamepad1.b);
    }

    @Override
    public void onUpdate() {
        driverControlled.schedule();

        if (gamepad2.a) {
            ServoPusher.INSTANCE.on.update();
        } else {
            ServoPusher.INSTANCE.off.update();
        }

        if (gamepad2.dpad_down) {
            SingleFlywheel.INSTANCE.off.update();
        } else if (gamepad2.dpad_up) {
            SingleFlywheel.INSTANCE.on.update();
        } else if (gamepad2.y) {
            SingleFlywheel.INSTANCE.onHigh.update();;
        }

        if (gamepad2.left_bumper) {
            MotorIntake.INSTANCE.off.update();
        } else if (gamepad2.right_bumper) {
            MotorIntake.INSTANCE.forward.update();
        }

        telemetry.update();
    }
}
