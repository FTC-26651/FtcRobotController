package org.firstinspires.ftc.teamcode.decode.robot.sensors;

import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.core.ColorRange;
import org.firstinspires.ftc.teamcode.core.robot.sensors.ColorSensor;

public class GreenPurpleSensor extends ColorSensor {

    public final ColorRange ARTIFACT_GREEN = new ColorRange(
            "ARTIFACT_GREEN",
            new double[] {0.0006, 0.0015, 0.0011},
            new double[] {0.0035, 0.025, 0.016}
    );
    public final ColorRange ARTIFACT_PURPLE = new ColorRange(
            "ARTIFACT_PURPLE",
            new double[] {0.0011, 0.0013, 0.0011},
            new double[] {0.01, 0.0025, 0.025}
    );

    public GreenPurpleSensor(String name) {
        super(name);
    }

    public String getColor() {
        super.enableLed();

        NormalizedRGBA colors = super.getRGB();
        double[] color = {colors.red, colors.green, colors.blue};

        if (ARTIFACT_PURPLE.inRange(color)) {
            return "Purple";
        } else if (ARTIFACT_GREEN.inRange(color)) {
            return "Green";
        }
        return "";
    }
}
