package org.firstinspires.ftc.teamcode.decode.robot;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.units.Angle;
import dev.nextftc.hardware.impl.ServoEx;

public class Spindexer implements Subsystem {
    public static final Spindexer INSTANCE = new Spindexer();

    Angle thirdCircle = Angle.fromRev(1 / 3);
    private final ServoEx spindexMotor = new ServoEx("claw_servo");

    private static final int INDEX_SIZE = 3;
    private static final String[] INDEX = new String[INDEX_SIZE];

    private Spindexer() { }

    public Command rotateLeft = new InstantCommand(() -> {
        spindexMotor.setPosition(spindexMotor.getPosition() + thirdCircle.inRev);
        String[] tempIndex = INDEX;

        INDEX[0] = tempIndex[2];
        INDEX[1] = tempIndex[0];
        INDEX[2] = tempIndex[1];
    });


    public Command rotateRight = new InstantCommand(() -> {
            spindexMotor.setPosition(spindexMotor.getPosition() - thirdCircle.inRev);
            String[] tempIndex = INDEX;

            INDEX[0] = tempIndex[1];
            INDEX[1] = tempIndex[2];
            INDEX[2] = tempIndex[0];
    });


    public Command addOrb(int pos, String color) {
        return new InstantCommand(() -> INDEX[pos] = color);
    }

    public Command removeOrb(int pos) {
        return new InstantCommand(() -> INDEX[pos] = "");
    }

    public String getOrbColor(int pos) {
        return INDEX[pos];
    }
}
