package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class Robot {
    private char team;
    private String name;
    protected LinearOpMode linearOpMode;

    // Constructor
    public Robot(LinearOpMode LOM) {
        linearOpMode = LOM;
    }

    public abstract void initRobot();

    // Define a function that is called every time through the main loop
    public void runEveryLoop() {}

    public void setTeam(char color) {
        if (color == 'b') {
            team = 'b';
        } else if (color == 'r') {
            team = 'r';
        } else {
            linearOpMode.telemetry.addLine("Invalid Team");
        }
    }

    public char getTeam() {
        return team;
    }

    public void setName(String iName) {
        name = iName;
    }

    public String getName() {
        return name;
    }
}
