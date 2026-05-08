package org.firstinspires.ftc.teamcode.core.interpreter;

import org.firstinspires.ftc.teamcode.core.robot.CoreCommandDefinitions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.NullCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

public class CommandRegistry implements Subsystem {
    public static CommandRegistry INSTANCE = new CommandRegistry();

    private final Map<String, Method> commandMethods = new HashMap<>();
    private final Map<String, Object> instances = new HashMap<>();

    private ArrayList<Object> commandObjects = new ArrayList<>();

    public CommandRegistry() {
        commandObjects.add(new CoreCommandDefinitions());
        register(new CoreCommandDefinitions());
        register(PathParser.class);
    }

    public void addCommandObject(Object o) {
        commandObjects.add(o);
    }

    @Override
    public void initialize() {
        for (Object commandObject : commandObjects) {
            register(commandObject);
        }
    }

    private void register(Object obj) {
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(CommandFactory.class)) {
                String name = method.getAnnotation(CommandFactory.class).value();
                commandMethods.put(name, method);
                instances.put(name, obj);
            }
        }
    }

    public Command create(String name, Map<String, Object> args) {
        Method method = commandMethods.get(name);
        Object instance = instances.get(name);

        if (method == null) {
            ActiveOpMode.telemetry().addLine(
                    "Unknown Command: " + name + ". Adding null command"
            );
            ActiveOpMode.telemetry().update();
            return new NullCommand();
        }

        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] invokeArgs = new Object[paramTypes.length];

        Map<String, Object> remaining = new HashMap<>(args);

        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> expectedType = paramTypes[i];
            Object matched = null;
            String matchedKey = null;

            for (Map.Entry<String, Object> entry : remaining.entrySet()) {
                Object value = entry.getValue();

                if (value != null && expectedType.isAssignableFrom(value.getClass())) {
                    matched = value;
                    matchedKey = entry.getKey();
                    break;
                }
            }

            if (matched == null) {
                throw new RuntimeException(
                        "Missing or wrong type for parameter " + i + " in command '" + name + "', expected " + expectedType.getSimpleName()
                );
            }

            invokeArgs[i] = matched;
            remaining.remove(matchedKey);
        }

        try {
            return (Command) method.invoke(instance, invokeArgs);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke command: " + name, e);
        }
    }
}
