package com.pascalnb.eddie.components.grab;

import com.pascalnb.eddie.EmbedUtil;
import com.pascalnb.eddie.models.EddieCommand;
import net.dv8tion.jda.api.entities.emoji.CustomEmoji;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Objects;

public class GrabEmojiCommand extends EddieCommand<GrabComponent> {

    public GrabEmojiCommand(GrabComponent component) {
        super(component, "emoji", "Grab an emoji");

        addOptions(
            new OptionData(OptionType.STRING, "emoji", "emoji", true)
        );
    }

    @Override
    public void accept(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue(hook -> {
            String string = Objects.requireNonNull(event.getOption("emoji")).getAsString().trim();
            if (!string.matches("^<a?:[a-z\\dA-Z_]+:\\d+>$")) {
                hook.editOriginalEmbeds(EmbedUtil.error("Please select a proper emoji").build()).queue();
                return;
            }

            CustomEmoji emoji = Emoji.fromFormatted(string).asCustom();
            String url = emoji.getImage().getUrl(1024);
            hook.editOriginal(url).queue();
        });
    }

}
