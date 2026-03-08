package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.robot.Commands;
import org.firstinspires.ftc.teamcode.core.robot.PathsToCommands;
import org.firstinspires.ftc.teamcode.core.robot.drivetrain.Constants;
import org.firstinspires.ftc.teamcode.core.robot.intakes.MotorIntake;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;

@Autonomous(name = "Lions Autonomous", group = "Autonomous")
public class LionsOpMode extends NextFTCOpMode {
    {
        addComponents(
                new PedroComponent(Constants::createFollower)
                // Whatever other components you may desire
                );
    }

    Yaml yaml;
    Map<String, Object> data;
    List<String> commandList;
    Map<String, Command> commands;
    int commandIndex = 0;
    Command currentCommand = null;
    boolean commandStarted;

    // Call this when a command is finished. I can't think of the right name right now
    private void finishCommand() {
        commandIndex++;
        currentCommand = null;
        commandStarted = false;
    }

    private Command getNextCommand() {
        String commandName = commandList.get(commandIndex);
        return commands.get(commandName);
    }
    private String getNextCommandName() {
        return commandList.get(commandIndex);
    }

    @Override public void onInit() {
        yaml = new Yaml();

        try {
            data = yaml.load(hardwareMap.appContext.getAssets().open("test.yaml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        commandList = (List<String>) data.get("commands");
        commands = Commands.getCommands();

        new PathsToCommands(PedroComponent.follower());
    }
    @Override public void onWaitForStart() {}
    @Override public void onStartButtonPressed() {}
    @Override public void onUpdate() {
        if (commandIndex >= commandList.size()) {
            this.telemetry.addLine("All actions complete.");
            this.telemetry.update();
            return;
        }

        if (currentCommand == null) {
            currentCommand = getNextCommand();

            if (currentCommand == null) {
                this.telemetry.addLine("Warning: unknown action '" + getNextCommandName() + "', ignoring.");
                finishCommand();
            } else {
                currentCommand.schedule();
                commandStarted = true;
            }
        } else {
            currentCommand.update();

            this.telemetry.addLine("Current command: " + currentCommand.name());
            this.telemetry.update();
            if (currentCommand.isDone()) {
                finishCommand();
            }
        }

        this.telemetry.update();

    }
    @Override public void onStop() {}
}