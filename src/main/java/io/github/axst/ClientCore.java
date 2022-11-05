package io.github.axst;

import io.github.sdxqw.winzcmd.Command;
import lombok.Getter;

public class ClientCore {

    @Getter
    public static final ClientCore instance = new ClientCore();

    public void initializeClient() {
        Command.addCommandByPath("io.github.axst");
        System.out.println(Command.typeList);
    }
}
