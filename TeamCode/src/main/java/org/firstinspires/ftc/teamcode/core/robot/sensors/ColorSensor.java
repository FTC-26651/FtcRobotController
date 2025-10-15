package org.firstinspires.ftc.teamcode.core.robot.sensors;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import dev.nextftc.ftc.ActiveOpMode;

public class ColorSensor {
    private final RevColorSensorV3 colorSensorV3;

    public ColorSensor(String deviceName) {
        colorSensorV3 = (RevColorSensorV3)ActiveOpMode.hardwareMap().colorSensor.get(deviceName);
    }

    public double getDistance(DistanceUnit unit) {
        return colorSensorV3.getDistance(unit);
    }

    public NormalizedRGBA getRGB() {
        return colorSensorV3.getNormalizedColors();
    }

    public void enableLed() {
        colorSensorV3.enableLed(true);
    }
}