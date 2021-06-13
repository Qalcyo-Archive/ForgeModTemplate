package xyz.matthewtgm.template;

import lombok.Getter;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import xyz.matthewtgm.lib.util.ForgeUtils;
import xyz.matthewtgm.template.core.TemplateModManager;
import xyz.matthewtgm.template.listeners.impl.ExampleListener;

@Mod(name = TemplateMod.NAME, version = TemplateMod.VER, modid = TemplateMod.ID)
public class TemplateMod {

    public static final String NAME = "Mod Name", VER = "@VER@", ID = "modid";

    @Mod.Instance(ID)
    private static TemplateMod instance;
    @Getter
    private static final TemplateModManager manager = new TemplateModManager();

    @Mod.EventHandler
    protected void onInit(FMLInitializationEvent event) {
        ForgeUtils.registerEventListeners(new ExampleListener());
    }

    public static TemplateMod getInstance() {
        return instance;
    }

}