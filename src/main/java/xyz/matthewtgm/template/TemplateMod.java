package xyz.matthewtgm.template;

import xyz.matthewtgm.lib.util.ForgeUtils;
import xyz.matthewtgm.lib.util.Notifications;
import xyz.matthewtgm.template.core.TemplateModManager;
import xyz.matthewtgm.template.listeners.impl.ExampleListener;
import lombok.Getter;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(name = TemplateMod.NAME, version = TemplateMod.VER, modid = TemplateMod.ID)
public class TemplateMod {

    public static final String NAME = "Mod Name", VER = "@VER@", ID = "modid";

    @Mod.Instance(ID) private static TemplateMod INSTANCE;
    @Getter private static final TemplateModManager manager = new TemplateModManager();

    @Mod.EventHandler
    protected void onInit(FMLInitializationEvent event) {
        ForgeUtils.registerEventListeners(new ExampleListener());
    }

    @Mod.EventHandler
    protected void onPostInit(FMLPostInitializationEvent event) {
        if (!manager.isLatestVersionOrBeta())
            Notifications.push(NAME + " is outdated!", "Please update to " + manager.getVersionChecker().getLatestVersion() + "!", manager::openDownloadPage);
    }

    public static TemplateMod getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TemplateMod();
        return INSTANCE;
    }

}