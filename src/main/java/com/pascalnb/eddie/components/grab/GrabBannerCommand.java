package com.pascalnb.eddie.components.grab;

import com.pascalnb.eddie.EmbedUtil;
import com.pascalnb.eddie.models.EddieCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.ImageProxy;

import java.util.Objects;

public class GrabBannerCommand extends EddieCommand<GrabComponent> {

    public GrabBannerCommand(GrabComponent component) {
        super(component, "banner", "Grab the server's banner image");
    }

    @Override
    public void accept(SlashCommandInteractionEvent event) {
        ImageProxy banner = Objects.requireNonNull(event.getGuild()).getBanner();
        if (banner != null) {
            String url = banner.getUrl(1024);
            event.reply(url).setEphemeral(true).queue();
        } else {
            event.replyEmbeds(EmbedUtil.error("This server has no banner image").build()).setEphemeral(true).queue();
        }
    }

}
