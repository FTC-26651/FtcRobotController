package org.firstinspires.ftc.teamcode.decode.robot.spindexer;

public class index {
    private static final int INDEX_SIZE = 3;
    private static final String[] INDEX = new String[INDEX_SIZE];

    static void addOrb(int pos, String color) {
        INDEX[pos] = color;
    }

    static void removeOrb(int pos) {
        INDEX[pos] = "";
    }

    static void rotateLeft() {
        String[] tempIndex = INDEX;

        INDEX[0] = tempIndex[2];
        INDEX[1] = tempIndex[0];
        INDEX[2] = tempIndex[1];
    }

    static void rotateRight() {
        String[] tempIndex = INDEX;

        INDEX[0] = tempIndex[1];
        INDEX[1] = tempIndex[2];
        INDEX[2] = tempIndex[0];
    }

    static String[] getIndex() {
        return INDEX;
    }
}
