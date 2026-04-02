package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.robot.Commands;
import org.firstinspires.ftc.teamcode.core.robot.util.PathParser;
import org.firstinspires.ftc.teamcode.core.robot.Robot;
import org.firstinspires.ftc.teamcode.core.robot.RobotConstants;
import org.firstinspires.ftc.teamcode.core.robot.drivetrain.Constants;
import org.firstinspires.ftc.teamcode.decode.robot.Aslan;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

@Autonomous(name = "Lions Autonomous", group = "Autonomous")
public class LionsOpMode extends NextFTCOpMode {
    {
        addComponents(
                new PedroComponent(Constants::createFollower)
                // Whatever other components your heart may desire
        );
    }

    private Robot robot;
    private final ElapsedTime timer = new ElapsedTime();

    private final String autoFilePath = "test.yaml";

    private Command allCommands;
    Map<String, CommandFactory> commandFactories;

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

        // Code to grab the file containing the paths from the yaml
        String pathsFilePath = (String) data.get("paths file");
        PathParser.setFilePath(pathsFilePath);

        // Parse the paths and add them to the list of commands
        PathParser.parse();
        Commands.addCommands(PathParser.getPathCommands());

        robot = Aslan.INSTANCE;
        robot.initialize();

        // Set the pose of the robot to whatever was declared in the file
        robot.setStartingPose(PathParser.getTrueStartPose());

        List<Map<String, Object>> commandList = (List<Map<String, Object>>) data.get("commands");
        commandFactories = Commands.getCommands();

        // ---------------------------------------------------------
        // BUILD ALL COMMANDS
        // ---------------------------------------------------------
        ArrayList<Command> builtCommands = new ArrayList<>();

        if (commandList == null) {
            telemetry.addLine("No commands found");
            telemetry.update();
        } else {
            for (Map<String, Object> entry : commandList) {
                Command cmd = CommandBuilder.buildCommand(entry);
                builtCommands.add(cmd);
            }
        }

        // Take all of the commands that we have built and put them in one sequential command
        allCommands = new SequentialGroup(
                builtCommands.toArray(new Command[0])
        );
    }

    @Override
    public void onWaitForStart() {
        // Here, if the robot allows for it, you may want to do vision
    }

    @Override
    public void onStartButtonPressed() {
        timer.reset();

        // Start the autonomous program
        allCommands.schedule();
    }

    @Override
    public void onUpdate() {
        robot.periodic();
        telemetry.update();

        // Save the robot's position every cycle so that we can use it in teleop
        RobotConstants.setRobotPose(follower().getPose());

        // If we are out of time, stop the auto. Don't want to accidentally go past time
        if (timer.seconds() >= 30 && !isDone) {
            telemetry.addLine("Out of time, stopping commands");
            allCommands.stop(true);
            isDone = true;
        }
    }

    @Override
    public void onStop() {
        RobotConstants.setRobotPose(follower().getPose());
    }
}