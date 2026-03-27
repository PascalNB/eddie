package com.pascalnb.eddie.components.faq.answer;

import com.pascalnb.eddie.ColorUtil;
import com.pascalnb.eddie.EmbedUtil;
import com.pascalnb.eddie.components.faq.FaqAnswerMessage;
import com.pascalnb.eddie.components.faq.FaqComponent;
import com.pascalnb.eddie.models.EddieButton;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.components.container.ContainerChildComponent;
import net.dv8tion.jda.api.components.container.ContainerChildComponentUnion;
import net.dv8tion.jda.api.components.separator.Separator;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import net.dv8tion.jda.api.utils.messages.MessageEditBuilder;

import java.util.ArrayList;
import java.util.List;

public class FaqAnswerSubmitButton extends EddieButton<FaqAnswerComponent> {

    public FaqAnswerSubmitButton(FaqAnswerComponent component, String id) {
        super(component, id);
    }

    @Override
    public Button getEntity() {
        return Button.success(getId(), "Send Answer").withEmoji(Emoji.fromUnicode("✔️"));
    }

    @Override
    public void accept(ButtonInteractionEvent event) {
        event.deferEdit().queue(hook -> {
            if (getComponent().getSelectedQuestion() == null) {
                hook.editOriginalEmbeds(EmbedUtil.error("No question selected").build()).queue();
                return;
            }

            FaqComponent.Question question = getComponent().getSelectedQuestion();
            FaqAnswerMessage answerMessage = new FaqAnswerMessage(getComponent().getParentComponent(), question, false);

            MessageCreateBuilder builder = MessageCreateBuilder.from(answerMessage.getEntity());
            MessageCreateData data;

            if (getComponent().getMention() == null) {
                data = builder.useComponentsV2().build();
            } else {
                Container container = builder.getComponentTree().getComponents().getFirst().asContainer();
                List<ContainerChildComponentUnion> currentComponents = container.getComponents();
                List<ContainerChildComponent> newComponents = new ArrayList<>(currentComponents);
                newComponents.addAll(List.of(
                    Separator.createDivider(Separator.Spacing.SMALL),
                    TextDisplay.of(getComponent().getMention().getAsMention())
                ));

                data = builder.useComponentsV2()
                    .setComponents(Container.of(newComponents))
                    .setAllowedMentions(List.of())
                    .mention(getComponent().getMention())
                    .build();
            }

            event.getChannel().sendMessage(data).useComponentsV2().queue(callback ->
                hook.editOriginal(new MessageEditBuilder()
                    .useComponentsV2()
                    .setComponents(
                        Container.of(
                            TextDisplay.of("FAQ answer sent")
                        ).withAccentColor(ColorUtil.GREEN)
                    )
                    .build()).useComponentsV2().queue(callback2 -> getComponent().unmount())
            );
        });
    }

}
