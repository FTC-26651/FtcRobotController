package org.firstinspires.ftc.teamcode.core.robot.sensors;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorSensor {
//    public static class ColorRange {
//        protected final String name;
//        protected final double[] min;
//        protected final double[] max;
//
//        public ColorRange(String name, double[] min, double[] max) {
//            this.name = name;
//            this.min = min;
//            this.max = max;
//        }
//    }

    private final RevColorSensorV3 colorSensorV3;

    public double[] rgb;
    public double[] YCbCr;

    public ColorSensor(HardwareMap hardwareMap, String name) {
        colorSensorV3 = (RevColorSensorV3) hardwareMap.colorSensor.get(name);
    }

    public double getDistance(DistanceUnit unit) {
        return colorSensorV3.getDistance(unit);
    }

    public double[] getRGB() {
        rgb[0] = colorSensorV3.red();
        rgb[1] = colorSensorV3.green();
        rgb[2] = colorSensorV3.blue();

        return rgb;
    }

    public double[] getYCbCr() {
        getRGB();

        YCbCr[0] = (0.299 * rgb[0] + 0.587 * rgb[1] + 0.114 * rgb[2]);
        YCbCr[1] = (128 - 0.169 * rgb[0] - 0.331 * rgb[1] + 0.500 * rgb[2]);
        YCbCr[2] = (128 + 0.500 * rgb[0] - 0.419 * rgb[1] - 0.081 * rgb[2]);

        return YCbCr;
    }

    public void enableLed() {
        colorSensorV3.enableLed(true);
    }
}

//    InternalSensor internalSensor;
//
//    public final ColorRange ARTIFACT_GREEN = new ColorRange(
//            "ARTIFACT_GREEN",
//            new double[] {30, 50, 118},
//            new double[] {255, 105, 145}
//    );
//    public final ColorRange ARTIFACT_PURPLE = new ColorRange(
//            "ARTIFACT_PURPLE",
//            new double[] {32, 135, 135},
//            new double[] {255, 155, 169}
//    );
//
//    public ColorSensor(HardwareMap hardwareMap, String name) {
//        internalSensor = new InternalSensor(hardwareMap, name);
//    }


