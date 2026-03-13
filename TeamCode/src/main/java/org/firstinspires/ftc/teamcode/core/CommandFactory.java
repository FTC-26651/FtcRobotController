package org.firstinspires.ftc.teamcode.core;

import java.util.Map;

import dev.nextftc.core.commands.Command;

@FunctionalInterface
public interface CommandFactory {
    Command create(Map<String, Object> args);
}