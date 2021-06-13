package xyz.matthewtgm.template.forge;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import xyz.matthewtgm.lib.TGMLibInstaller;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

public class TemplateLoadingPlugin implements IFMLLoadingPlugin {

    public TemplateLoadingPlugin() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.template.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override
    public String[] getASMTransformerClass() {
        TGMLibInstaller.ReturnValue tgmLibInitialized = TGMLibInstaller.initialize(Launch.minecraftHome);
        if (tgmLibInitialized != TGMLibInstaller.ReturnValue.SUCCESSFUL) System.out.println("Failed to load TGMLib.");
        else System.out.println("Loaded TGMLib successfully.");

        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> map) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}