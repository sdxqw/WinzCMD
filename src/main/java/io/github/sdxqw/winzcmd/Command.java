package io.github.sdxqw.winzcmd;

import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Command {
    public static final List<CommandType> typeList = new ArrayList<>();

    @SneakyThrows
    public static void addCommandByPath(String path)  {
        Reflections reflection = new Reflections(path);
        Set<Class<? extends CommandType>> clazz = reflection.getSubTypesOf(CommandType.class);
        for (Class<?> e : clazz) {
            if (e.isAnnotationPresent(CommandInfo.class)) {
                typeList.add((CommandType) e.newInstance());
            }
        }
    }

    public static void addCommandByType(CommandType type) {
        boolean e = CommandType.class.isAnnotationPresent(CommandInfo.class);
        if (e) typeList.add(type);
    }

    public static void runCommandByName(String name) {
        typeList.stream().filter(e -> e.getClass().getAnnotation(CommandInfo.class).name().equals(name)).forEach(CommandType::startCommand);
    }

    public static void runCommandByID(int id) {
        typeList.stream().filter(e -> e.getClass().getAnnotation(CommandInfo.class).id() == id).forEach(CommandType::startCommand);
    }
}
