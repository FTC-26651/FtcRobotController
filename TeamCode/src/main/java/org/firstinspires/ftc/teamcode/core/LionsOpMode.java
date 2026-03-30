package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.robot.Commands;
import org.firstinspires.ftc.teamcode.core.robot.PathParser;
import org.firstinspires.ftc.teamcode.core.robot.Robot;
import org.firstinspires.ftc.teamcode.core.robot.drivetrain.Constants;
import org.firstinspires.ftc.teamcode.decode.robot.Aslan;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.NullCommand;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

@Autonomous(name = "Lions Autonomous", group = "Autonomous")
public class LionsOpMode extends NextFTCOpMode {
    {
        addComponents(
                new PedroComponent(Constants::createFollower)
        );
    }

    private Robot robot;
    private final ElapsedTime timer = new ElapsedTime();

    private final String autoFilePath = "test.yaml";

    private Command allCommands;

    boolean isDone = false;

    @Override
    public void onInit() {
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try {
            data = yaml.load(hardwareMap.appContext.getAssets().open(autoFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String pathsFilePath = (String) data.get("Paths File");
        PathParser.setFilePath(pathsFilePath);

        robot = Aslan.INSTANCE;
        robot.setAllianceColor(follower().getPose().getX() < 72 ? "blue" : "red");
        robot.initialize();

        List<Map<String, Object>> commandList = (List<Map<String, Object>>) data.get("commands");
        Map<String, CommandFactory> commandFactories = Commands.getCommands();

        // ---------------------------------------------------------
        // BUILD ALL COMMANDS HERE
        // ---------------------------------------------------------
        ArrayList<Command> builtCommands = new ArrayList<>();

        for (Map<String, Object> entry : commandList) {
            String name = ((String) entry.get("name")).toLowerCase();

            telemetry.addData("Parsing command", name);
            telemetry.update();

            CommandFactory factory = commandFactories.get(name);

            if (factory == null) {
                telemetry.addLine("Unknown Command: " + name + ". Adding null command");
                telemetry.update();
                
                builtCommands.add(new NullCommand());
            } else {
                builtCommands.add(factory.create(entry));
            }
        }

        allCommands = new SequentialGroup(
                builtCommands.toArray(new Command[0])
        );
    }

    @Override
    public void onWaitForStart() {}

    @Override
    public void onStartButtonPressed() {
        timer.reset();
        allCommands.run();
    }

    @Override
    public void onUpdate() {
        robot.periodic();
        telemetry.update();

        if (timer.seconds() >= 30 && !isDone) {
            telemetry.addLine("Out of time, stopping commands");
            allCommands.stop(true);
            isDone = true;
        }
    }

    @Override
    public void onStop() {}
}