package ga.matthewtgm.template.listeners;

import ga.matthewtgm.template.TemplateMod;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TemplateListener {
    protected final Minecraft mc = Minecraft.getMinecraft();
    protected final Logger logger = LogManager.getLogger(TemplateMod.NAME + " (" + getClass().getSimpleName() + ")");
}