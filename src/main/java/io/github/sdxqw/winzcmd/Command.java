package io.github.sdxqw.winzcmd;

import io.github.sdxqw.axstlogger.Level;
import io.github.sdxqw.axstlogger.Logger;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Main class for commands use this for run or add commands
 *
 * @author sdxqw
 */
public class Command {
    private static final List<ICommand> typeList = new ArrayList<>();
    private static Thread threadRunner = null;

    /**
     * Register commands into a list, so we can easily
     * use can use other features like runner thanks to your list
     *
     * @param path String path to load every command
     *             E.G: "io.github.sdxqw.myclient"
     */
    @SneakyThrows
    public static void registerCommands(String path) {
        Reflections reflection = new Reflections(path);
        Set<Class<? extends ICommand>> clazz = reflection.getSubTypesOf(ICommand.class);
        for (Class<?> e : clazz) {
            if (e.isAnnotationPresent(CommandInfo.class)) {
                typeList.add((ICommand) e.newInstance());
            } else Logger.print("We found one class that implements ICommand please use @CommandInfo to get start", Level.ERROR);
        }
    }

    /**
     * Remove commands from the list, remember if you don't add it
     * back will never work again
     * @param command ICommand command to remove
     *                E.G: new TestCommand()
     */
    public static void unregisterCommand(ICommand command) {
        typeList.removeIf(e -> e.equals(command));
        if (typeList.stream().anyMatch(e -> e.equals(command))) Logger.print("Command removed: " + command, Level.INFO);
    }

    /**
     * Use this method to run our command everywhere you want
     *
     * @param id Int id of the command
     */
    public static void runCommandByID(int id) {
        typeList.stream().filter(e -> e.getClass().getAnnotation(CommandInfo.class).id() == id).forEach(e -> {
            threadRunner = new Thread(e::startCommand);
            threadRunner.setName("Command " + id);
            if (threadRunner.getName().equals("Command " + id)) {
                threadRunner.start();
                Logger.print("Command started: " + threadRunner.getName(), Level.INFO);
            } else Logger.print("We cant find command id (start): " + id, Level.INFO);
        });
    }

    /**
     * Use this method to stop our command everywhere you want
     *
     * @param id Int id of the command
     */
    public static void stopCommandByID(int id) {
        typeList.stream().filter(e -> e.getClass().getAnnotation(CommandInfo.class).id() == id).forEach(e -> {
            if (threadRunner.getName().equals("Command " + id)) {
                threadRunner.interrupt();
                Logger.print("Command stopped: " + threadRunner.getName(), Level.INFO);
            } else Logger.print("We cant find command id (stop): " + id, Level.INFO);
        });
    }

    /**
     * Use this method to get the command id
     *
     * @param id Int id of the command
     */
    public static void getCommandID(int id) {
        typeList.stream().filter(e -> e.getClass().getAnnotation(CommandInfo.class).id() == id).forEach(ICommand::startCommand);
    }
}
