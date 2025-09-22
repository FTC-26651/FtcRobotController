package org.firstinspires.ftc.teamcode.decode.robot.sensors;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.core.ColorRange;
import org.firstinspires.ftc.teamcode.core.robot.sensors.ColorSensor;

public class GreenPurpleSensor extends ColorSensor {

    public final ColorRange ARTIFACT_GREEN = new ColorRange(
            "ARTIFACT_GREEN",
            new double[] {30, 50, 118},
            new double[] {255, 105, 145}
    );
    public final ColorRange ARTIFACT_PURPLE = new ColorRange(
            "ARTIFACT_PURPLE",
            new double[] {32, 135, 135},
            new double[] {255, 155, 169}
    );

    public GreenPurpleSensor(HardwareMap hardwareMap, String name) {
        super(hardwareMap, name);
    }

    public String getColor() {
        super.enableLed();

        NormalizedRGBA colors = super.getRGB();
        double[] color = {colors.red, colors.green, colors.blue};

        if (ARTIFACT_GREEN.inRange(color)) {
            return "Green";
        } else if (ARTIFACT_PURPLE.inRange(color)) {
            return "Purple";
        }
        return "";
    }
}
