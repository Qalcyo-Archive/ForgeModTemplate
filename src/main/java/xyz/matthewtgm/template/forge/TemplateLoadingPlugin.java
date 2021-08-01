package xyz.matthewtgm.template.forge;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import xyz.matthewtgm.tgmlib.launchwrapper.TGMLibLaunchwrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TemplateLoadingPlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        List<String> classTransformers = new ArrayList<>();
        TGMLibLaunchwrapper.quickInject(classTransformers);
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
    public void injectData(Map<String, Object> map) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}