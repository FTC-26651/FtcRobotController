package org.firstinspires.ftc.teamcode.libs.pathfinder;

import org.firstinspires.ftc.teamcode.libs.field.Field;
import org.firstinspires.ftc.teamcode.libs.field.Object;

public class ObjectLoader {

    public void loadObjects() {
        for (Object object : Field.elements) {
            if (object.hasFlag("Obstacle")) {
                Pathfinder.loadObject(object.getLocation());
            }
        }
    }
}
