package xyz.matthewtgm.template.forge;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import xyz.matthewtgm.tgmlib.TGMLibInstaller;

import java.util.Map;

public class TemplateLoadingPlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        TGMLibInstaller.ReturnValue tgmLibInitialized = TGMLibInstaller.initialize(Launch.minecraftHome);
        if (tgmLibInitialized != TGMLibInstaller.ReturnValue.SUCCESSFUL)
            System.out.println("Failed to load TGMLib.");
        else
            System.out.println("Loaded TGMLib successfully.");

        if (TGMLibInstaller.isLoaded())
            return new String[] {"xyz.matthewtgm.tgmlib.tweaker.TGMLibClassTransformer"};
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