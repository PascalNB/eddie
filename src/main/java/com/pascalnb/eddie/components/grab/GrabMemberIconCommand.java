package com.pascalnb.eddie.components.grab;

import com.pascalnb.eddie.EmbedUtil;
import com.pascalnb.eddie.models.EddieCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Objects;

public class GrabMemberIconCommand extends EddieCommand<GrabComponent> {

    public GrabMemberIconCommand(GrabComponent component) {
        super(component, "member-icon", "Grab a member's effective profile picture");

        addOptions(
            new OptionData(OptionType.USER, "user", "user", true)
        );
    }

    @Override
    public void accept(SlashCommandInteractionEvent event) {
        Member member = Objects.requireNonNull(event.getOption("user")).getAsMember();
        if (member == null) {
            event.replyEmbeds(EmbedUtil.error("Member not found").build()).setEphemeral(true).queue();
            return;
        }

        String url = member.getEffectiveAvatar().getUrl(1024);
        event.reply(url).setEphemeral(true).queue();
    }

}
