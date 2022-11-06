package io.github.sdxqw.dev;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Tweaker implements ITweaker {
    private static final List<String> args = new ArrayList<>();

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        Tweaker.args.addAll(args);
        if(gameDir != null) {
            Tweaker.args.add("--gameDir");
            Tweaker.args.add(gameDir.getAbsolutePath());
        }
        if(assetsDir != null) {
            Tweaker.args.add("--assetsDir");
            Tweaker.args.add(assetsDir.getAbsolutePath());
        }
        if(profile != null) {
            Tweaker.args.add("--version");
            Tweaker.args.add(profile);
        }
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.winzcmd.json");

        MixinEnvironment environment = MixinEnvironment.getDefaultEnvironment();

        if(environment.getObfuscationContext() == null) {
            environment.setObfuscationContext("notch");
        }

        environment.setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override
    public String getLaunchTarget() {
        return MixinBootstrap.getPlatform().getLaunchTarget();
    }

    @Override
    public String[] getLaunchArguments() {
        return Tweaker.args.toArray(new String[0]);
    }
}
