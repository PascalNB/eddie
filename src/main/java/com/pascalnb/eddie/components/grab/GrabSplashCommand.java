package com.pascalnb.eddie.components.grab;

import com.pascalnb.eddie.EmbedUtil;
import com.pascalnb.eddie.models.EddieCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.ImageProxy;

import java.util.Objects;

public class GrabSplashCommand extends EddieCommand<GrabComponent> {

    public GrabSplashCommand(GrabComponent component) {
        super(component, "splash", "Grab the server's splash image");
    }

    @Override
    public void accept(SlashCommandInteractionEvent event) {
        ImageProxy splash = Objects.requireNonNull(event.getGuild()).getSplash();
        if (splash != null) {
            String url = splash.getUrl(1024);
            event.reply(url).setEphemeral(true).queue();
        } else {
            event.replyEmbeds(EmbedUtil.error("This server has no splash image").build()).setEphemeral(true).queue();
        }
    }

}
