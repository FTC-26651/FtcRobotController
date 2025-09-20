package org.firstinspires.ftc.teamcode.robot.sensors;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorSensor {
    private final RevColorSensorV3 colorSensorV3;

    public double[] rgb;
    public double[] YCbCr;

    public ColorSensor(HardwareMap hardwareMap, String name) {
        colorSensorV3 = (RevColorSensorV3) hardwareMap.colorSensor.get(name);
    }

    public void getDistance(DistanceUnit unit) {
        colorSensorV3.getDistance(unit);
    }

    public void getRGB() {
        rgb[0] = colorSensorV3.red();
        rgb[1] = colorSensorV3.green();
        rgb[2] = colorSensorV3.blue();
    }

    public void getYCbCr() {
        getRGB();

        YCbCr[0] = (0.299 * rgb[0] + 0.587 * rgb[1] + 0.114 * rgb[2]);
        YCbCr[1] = (128 - 0.169 * rgb[0] - 0.331 * rgb[1] + 0.500 * rgb[2]);
        YCbCr[2] = (128 + 0.500 * rgb[0] -0.419 * rgb[1] - 0.081 * rgb[2]);
    }

    public void enableLed() {
        colorSensorV3.enableLed(true);
    }
}