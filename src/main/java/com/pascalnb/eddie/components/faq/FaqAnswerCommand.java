package com.pascalnb.eddie.components.faq;

import com.pascalnb.eddie.components.faq.answer.FaqAnswerComponent;
import com.pascalnb.eddie.models.EddieCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class FaqAnswerCommand extends EddieCommand<FaqComponent> {

    public FaqAnswerCommand(FaqComponent component) {
        super(component, "answer", "Send an FAQ answer in chat.");
    }

    @Override
    public void accept(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue(hook -> {
            FaqAnswerComponent answerMenu = getComponent().createAnswerMenu();
            hook.sendMessage(answerMenu.getMessage()).queue();
        });
    }

}
