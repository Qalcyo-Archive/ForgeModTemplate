package xyz.matthewtgm.template.mixins.client;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.matthewtgm.template.TemplateMod;

@Mixin({Minecraft.class})
public class MinecraftMixin {

    @Inject(method = "startGame", at = @At("HEAD"))
    private void onGameStarted(CallbackInfo ci) {
        LogManager.getLogger(TemplateMod.NAME).info("Game started!");
    }

}