package org.firstinspires.ftc.teamcode.decode.robot;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.units.Angle;
import dev.nextftc.hardware.impl.ServoEx;

// When I was writing this, only I and God knew what this does. Now only God knows what it does
// Jk it's not that bad, but it is the most complicated thing I've written so far
public class Spindexer implements Subsystem {
    public static final Spindexer INSTANCE = new Spindexer();
    private Spindexer() { }

    public static class Params {
        private final Angle thirdCircle = Angle.fromRev(1 / 3);

        private final int INDEX_SIZE = 3;
        private final int PATTERN_SIZE = 3;

        private final int inputLocation = 0;
        private final int outputLocation = 0;

        private final String name = "spindex_servo";
    }

    private final Params PARAMS = new Params();

    private final ServoEx spindexMotor = new ServoEx(PARAMS.name);

    private final String[] INDEX = new String[PARAMS.INDEX_SIZE];
    private String[] PATTERN = new String[PARAMS.PATTERN_SIZE];
    private int patternIncrement = 0;

    private int getIndexOf(String color) {
        for (int i = 0;  i < PARAMS.INDEX_SIZE; i++) {
            if (color.equals(INDEX[i])) {
                return i;
            }
        }
        return -1;
    }

    private String getOrbToShoot() {
        patternIncrement++;
        return PATTERN[patternIncrement];
    }

    private void setOrbToShoot(String color) {
        int indexOfColor = getIndexOf(color);
        while (indexOfColor != PARAMS.outputLocation) {
            if (indexOfColor > PARAMS.outputLocation) {
                INSTANCE.rotateLeft.schedule();
            }
            if (indexOfColor < PARAMS.outputLocation) {
                INSTANCE.rotateRight.schedule();
            }
            indexOfColor = getIndexOf(color);
        }
    }



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

    public boolean isFull() {
        for (String color : INDEX) {
            if (color.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void setPattern(String[] pattern) {
        if (pattern.length == PARAMS.PATTERN_SIZE) {
            PATTERN = pattern;
        }
    }

    public Command index = new InstantCommand(() -> {
        setOrbToShoot(getOrbToShoot());
    });
}
