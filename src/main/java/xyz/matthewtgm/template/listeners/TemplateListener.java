package xyz.matthewtgm.template.listeners;

import xyz.matthewtgm.template.TemplateMod;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Basic listener that adds loggers and an easily accessible instance of the main {@link Minecraft} class.
 */
public abstract class TemplateListener {
    protected final Minecraft mc = Minecraft.getMinecraft();
    protected final Logger logger = LogManager.getLogger(TemplateMod.NAME + " (" + getClass().getSimpleName() + ")");
}