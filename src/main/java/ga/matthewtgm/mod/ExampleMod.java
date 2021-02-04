package ga.matthewtgm.mod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = Constants.NAME, version = Constants.VER, modid = Constants.ID)
public class ExampleMod {

    @Mod.Instance(Constants.ID)
    private static ExampleMod INSTANCE;

    public static ExampleMod getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ExampleMod();
        return INSTANCE;
    }

    @Mod.EventHandler
    protected void onPreInit(FMLPreInitializationEvent event) {

    }

}