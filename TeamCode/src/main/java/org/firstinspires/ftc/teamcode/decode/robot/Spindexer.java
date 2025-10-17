package org.firstinspires.ftc.teamcode.decode.robot;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.units.Angle;
import dev.nextftc.hardware.impl.ServoEx;

public class Spindexer implements Subsystem {
    public static final Spindexer INSTANCE = new Spindexer();
    private Spindexer() { }

    public static class Params {
        private final Angle thirdCircle = Angle.fromRev(1 / 3);

        private final int INDEX_SIZE = 3;

        private final int inputLocation = 0;
        private final int outputLocation = 0;

        private final String name = "spindex_servo";
    }

    public Spindexer.Params PARAMS = new Spindexer.Params();

    private final ServoEx spindexMotor = new ServoEx(PARAMS.name);
    private final String[] INDEX = new String[PARAMS.INDEX_SIZE];

    public Command rotateLeft = new InstantCommand(() -> {
        spindexMotor.setPosition(spindexMotor.getPosition() + PARAMS.thirdCircle.inRev);
        String[] tempIndex = INDEX;

        INDEX[0] = tempIndex[2];
        INDEX[1] = tempIndex[0];
        INDEX[2] = tempIndex[1];
    });
    public Command rotateRight = new InstantCommand(() -> {
        spindexMotor.setPosition(spindexMotor.getPosition() - PARAMS.thirdCircle.inRev);
        String[] tempIndex = INDEX;

        INDEX[0] = tempIndex[1];
        INDEX[1] = tempIndex[2];
        INDEX[2] = tempIndex[0];
    });

    public Command addOrb(String color) {
        return new InstantCommand(() -> INDEX[PARAMS.inputLocation] = color);
    }
    public Command removeOrb() {
        return new InstantCommand(() -> INDEX[PARAMS.outputLocation] = "");
    }

    public String getOrbColor(int pos) {
        return INDEX[pos];
    }

    public boolean isFull() {
        for (String color : INDEX) {
            if (color.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
