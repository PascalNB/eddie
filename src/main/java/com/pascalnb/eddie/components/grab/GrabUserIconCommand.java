package com.pascalnb.eddie.components.grab;

import com.pascalnb.eddie.models.EddieCommand;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Objects;

public class GrabUserIconCommand extends EddieCommand<GrabComponent> {

    public GrabUserIconCommand(GrabComponent component) {
        super(component, "user-icon", "Grab a user's effective profile picture");

        addOptions(
            new OptionData(OptionType.USER, "user", "user", true)
        );
    }

    @Override
    public void accept(SlashCommandInteractionEvent event) {
        User user = Objects.requireNonNull(event.getOption("user")).getAsUser();

        String url = user.getEffectiveAvatar().getUrl(1024);
        event.reply(url).setEphemeral(true).queue();
    }

}
