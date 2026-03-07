package org.firstinspires.ftc.teamcode.core;

import org.firstinspires.ftc.teamcode.core.robot.Commands;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.NextFTCOpMode;

public class LionsOpMode extends NextFTCOpMode {
    {
        addComponents(/* vararg components */);
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

    @Override public void onInit() {
        yaml = new Yaml();
        data = yaml.load("HelloWorld");
        commandList = (List<String>) data.get("commands");
        commands = Commands.getCommands();
    }
    @Override public void onWaitForStart() {}
    @Override public void onStartButtonPressed() {}
    @Override public void onUpdate() {
        if (commandIndex >= commandList.size()) {
            telemetry.addLine("All actions complete.");
            return;
        }

        if (currentCommand == null) {
            String commandName = commandList.get(commandIndex);
            Command currentCommand = commands.get(commandName);

            if (currentCommand == null) {
                this.telemetry.addLine("Warning: unknown action '" + commandName + "', ignoring.");
                finishCommand();
            } else {
                currentCommand.run();
                commandStarted = true;
            }
        }

        currentCommand.update();

        if (currentCommand.isDone()) {
            finishCommand();
        }
    }
    @Override public void onStop() { }
}