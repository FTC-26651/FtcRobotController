package org.firstinspires.ftc.teamcode.core;

public class ColorRange {
    protected final String name;
    protected final double[] min;
    protected final double[] max;

    public ColorRange(String name, double[] min, double[] max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }

    public boolean inRange(double[] color) {
        for (int i = 0; i < color.length; i++) {
            if ((color[i] <= min[i]) || (color[i] >= max[i])) {
                return false;
            }
        }
        return true;
    }
}
