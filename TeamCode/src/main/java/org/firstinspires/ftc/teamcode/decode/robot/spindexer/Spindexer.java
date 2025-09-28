package org.firstinspires.ftc.teamcode.decode.robot.spindexer;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Spindexer {
    private final Servo spindexMotor;
    private final double pos = 0;

    public Spindexer(HardwareMap hardwareMap) {
        spindexMotor = hardwareMap.get(Servo.class, "spindex");
    }

    public class RotateLeft implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            spindexMotor.setPosition(spindexMotor.getPosition() + pos); // Position TBD
            SpindexIndex.rotateLeft();
            return false;
        }
    }
    public Action rotateLeft() {
        return new RotateLeft();
    }

    public class RotateRight implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            spindexMotor.setPosition(spindexMotor.getPosition() - pos); // Position TBD
            SpindexIndex.rotateRight();
            return false;
        }
    }
    public Action rotateRight() {
        return new RotateRight();
    }
}
