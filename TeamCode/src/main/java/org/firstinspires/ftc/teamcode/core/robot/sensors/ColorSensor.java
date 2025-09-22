package org.firstinspires.ftc.teamcode.core.robot.sensors;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorSensor {

    private final RevColorSensorV3 colorSensorV3;


    public ColorSensor(HardwareMap hardwareMap, String name) {
        colorSensorV3 = (RevColorSensorV3) hardwareMap.colorSensor.get(name);
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