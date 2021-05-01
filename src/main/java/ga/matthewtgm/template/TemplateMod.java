package ga.matthewtgm.template;

import ga.matthewtgm.lib.TGMLib;
import ga.matthewtgm.lib.util.Notifications;
import ga.matthewtgm.template.core.TemplateModManager;
import lombok.Getter;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = TemplateMod.NAME, version = TemplateMod.VER, modid = TemplateMod.ID)
public class TemplateMod {

    public static final String NAME = "Mod Name", VER = "@VER@", ID = "modid";

    @Mod.Instance(ID)
    private static TemplateMod INSTANCE;
    @Getter private static final TemplateModManager manager = new TemplateModManager();

    @Mod.EventHandler
    protected void onPreInit(FMLPreInitializationEvent event) {
        TGMLib.getInstance().onForgePreInit();
    }

    @Mod.EventHandler
    protected void onPostInit(FMLPostInitializationEvent event) {
        if (!VER.matches(manager.getVersionChecker().getLatestVersion()) || !VER.matches(manager.getVersionChecker().getLatestBeta()))
            Notifications.push(NAME + " is outdated!", "Please update to " + manager.getVersionChecker().getLatestVersion() + "!", manager::openDownloadPage);
    }

    public static TemplateMod getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TemplateMod();
        return INSTANCE;
    }

}