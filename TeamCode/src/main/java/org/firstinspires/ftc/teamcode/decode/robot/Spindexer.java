package org.firstinspires.ftc.teamcode.decode.robot;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.units.Angle;
import dev.nextftc.hardware.impl.ServoEx;

// The spindexer class assumes that spindexer index counts upwards, counter clockwise.
public class Spindexer implements Subsystem {
    public static final Spindexer INSTANCE = new Spindexer();
    private Spindexer() { }

    public static class Params {
        private final Angle thirdCircle = Angle.fromRev(1 / 3);

        private final int INDEX_SIZE = 3;
        private final int PATTERN_SIZE = 3;

        // TODO: Adjust these values based off of what the robot will be.
        private final int inputLocation = 0;
        private final int outputLocation = 0;

        private final String name = "spindex_servo";
    }

    private final Params PARAMS = new Params();

    private final ServoEx spindexMotor = new ServoEx(PARAMS.name);

    private final String[] INDEX = new String[PARAMS.INDEX_SIZE];
    private String[] PATTERN = new String[PARAMS.PATTERN_SIZE];
    private int patternIncrement = 0;

    private boolean indexing = false;

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
        if (patternIncrement > PARAMS.PATTERN_SIZE) {
            patternIncrement = 0;
        }
        return PATTERN[patternIncrement];
    }

    private void setOrbToShoot(String color) {
        int indexOfColor = getIndexOf(color);

        if (indexOfColor == -1) {
            return;
        }

        if (indexOfColor != PARAMS.outputLocation) {
            int rightDistance = (PARAMS.outputLocation - indexOfColor + PARAMS.INDEX_SIZE) % PARAMS.INDEX_SIZE;
            int leftDistance = (indexOfColor - PARAMS.outputLocation + PARAMS.INDEX_SIZE) % PARAMS.INDEX_SIZE;

            if (rightDistance <= leftDistance && rightDistance != 0) {
                INSTANCE.rotateRight.schedule();
            } else if (leftDistance < rightDistance && leftDistance != 0) {
                INSTANCE.rotateLeft.schedule();
            }
        } else {
            indexing = false;
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
        indexing = true;
    });

    @Override
    public void periodic() {
        if (indexing) {
            setOrbToShoot(getOrbToShoot());
        }
    }
}
