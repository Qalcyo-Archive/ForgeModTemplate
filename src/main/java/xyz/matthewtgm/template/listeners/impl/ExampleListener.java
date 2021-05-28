package xyz.matthewtgm.template.listeners.impl;

import xyz.matthewtgm.template.listeners.TemplateListener;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ExampleListener extends TemplateListener {

    @SubscribeEvent
    protected void onChatReceived(ClientChatReceivedEvent event) {
        logger.info("Chat message received!\t" + event.message.getUnformattedText());
    }

}