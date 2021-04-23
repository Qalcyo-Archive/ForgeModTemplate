package ga.matthewtgm.template;

import ga.matthewtgm.lib.TGMLib;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = Constants.NAME, version = Constants.VER, modid = Constants.ID)
public class TemplateMod {

    @Mod.Instance(Constants.ID)
    private static TemplateMod INSTANCE;

    @Mod.EventHandler
    protected void onPreInit(FMLPreInitializationEvent event) {
        TGMLib.getInstance().onForgePreInit();
    }

    public static TemplateMod getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TemplateMod();
        return INSTANCE;
    }

}