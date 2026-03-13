package org.firstinspires.ftc.teamcode.core;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.robot.Commands;
import org.firstinspires.ftc.teamcode.core.robot.Robot;
import org.firstinspires.ftc.teamcode.core.robot.drivetrain.Constants;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
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

    private Robot robot;

    private List<String> commandList;
    private Map<String, Command> commands;
    private int commandIndex = 0;
    private Command currentCommand = null;
    boolean commandStarted;

    private final String filePath = "test.yaml";

    // Call this when a command is finished. I can't think of the right name right now
    private void finishCommand() {
        commandIndex++;
        currentCommand = null;
        commandStarted = false;
    }

    private Command getNextCommand() {
        String commandName = commandList.get(commandIndex);
        return commands.get(commandName.toLowerCase());
    }
    private String getNextCommandName() {
        return commandList.get(commandIndex).toLowerCase();
    }

    @Override public void onInit() {
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try {
            data = yaml.load(hardwareMap.appContext.getAssets().open(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        robot = new Robot(PedroComponent.follower());
        robot.setStartingPose(new Pose(56, 8, Math.toRadians(90)));
        robot.initialize();

        commandList = (List<String>) data.get("commands");
        commands = robot.commands.getCommands();
    }
    @Override public void onWaitForStart() {}
    @Override public void onStartButtonPressed() {}
    @Override public void onUpdate() {
        if (commandIndex >= commandList.size()) {
            this.telemetry.addLine("All commands complete.");
            this.telemetry.update();
            return;
        }

        if (currentCommand == null) {
            currentCommand = getNextCommand();

            if (currentCommand == null) {
                this.telemetry.addLine("Warning: unknown command '" + getNextCommandName() + "', ignoring.");
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