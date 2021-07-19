package xyz.matthewtgm.template;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import xyz.matthewtgm.template.core.TemplateModManager;
import xyz.matthewtgm.template.listeners.impl.ExampleListener;
import xyz.matthewtgm.tgmlib.TGMLibInstaller;
import xyz.matthewtgm.tgmlib.util.ForgeHelper;

@Mod(name = TemplateMod.NAME, version = TemplateMod.VER, modid = TemplateMod.ID)
public class TemplateMod {

    public static final String NAME = "@NAME@", VER = "@VER@", ID = "@ID@";

    @Mod.Instance(ID)
    private static TemplateMod instance;
    @Getter
    private static final TemplateModManager manager = new TemplateModManager();

    @Mod.EventHandler
    protected void initialize(FMLInitializationEvent event) {
        TGMLibInstaller.load(Minecraft.getMinecraft().mcDataDir);
        ForgeHelper.registerEventListeners(new ExampleListener());
    }

    public static TemplateMod getInstance() {
        return instance;
    }

}