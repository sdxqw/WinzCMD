package io.github.axst;

import io.github.sdxqw.winzcmd.CommandInfo;
import io.github.sdxqw.winzcmd.CommandType;

@CommandInfo(name = "test", id = 1)
public class CommandType2 implements CommandType {
    @Override
    public void startCommand() {
        System.out.println("test");
    }
}
