package org.firstinspires.ftc.teamcode.decode.robot;

import org.firstinspires.ftc.teamcode.core.robot.intakes.MotorIntake;
import org.firstinspires.ftc.teamcode.decode.robot.sensors.GreenPurpleSensor;

import dev.nextftc.core.subsystems.SubsystemGroup;


public class Intake extends SubsystemGroup {
    public static final Intake INSTANCE = new Intake();
    private Intake() {
        super(
                MotorIntake.INSTANCE,
                Spindexer.INSTANCE
        );
    }

    private final GreenPurpleSensor color = new GreenPurpleSensor("color");

    @Override
    public void periodic() {
        if (Spindexer.INSTANCE.isFull()) {
            MotorIntake.INSTANCE.off.schedule();
        } else {
            MotorIntake.INSTANCE.on.schedule();
            if (!color.getColor().isEmpty()) {
                Spindexer.INSTANCE.addOrb(color.getColor());
            }
        }
    }
}
