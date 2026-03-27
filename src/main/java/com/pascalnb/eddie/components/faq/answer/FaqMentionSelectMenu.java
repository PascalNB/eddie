package com.pascalnb.eddie.components.faq.answer;

import com.pascalnb.eddie.models.dynamic.UpdatingEntitySelector;
import net.dv8tion.jda.api.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.jetbrains.annotations.Nullable;

public class FaqMentionSelectMenu extends UpdatingEntitySelector<FaqAnswerComponent> {

    public FaqMentionSelectMenu(FaqAnswerComponent component, String id) {
        super(component, id);
    }

    @Override
    public EntitySelectMenu getEntity() {
        EntitySelectMenu.Builder builder = EntitySelectMenu.create(getId(), EntitySelectMenu.SelectTarget.USER)
            .setMinValues(0)
            .setMaxValues(1);

        if (getComponent().getMention() != null) {
            builder.setDefaultValues(EntitySelectMenu.DefaultValue.user(getComponent().getMention().getId()));
        }

        return builder.build();
    }

    @Override
    public @Nullable FaqAnswerComponent apply(EntitySelectInteractionEvent event, InteractionHook hook) {
        if (event.getValues().isEmpty()) {
            return createComponent(getComponent().factory(
                getComponent().getQuestions(),
                getComponent().getSelectedQuestion(),
                null
            ));
        }

        IMentionable mention =  event.getValues().getFirst();

        return createComponent(getComponent().factory(
            getComponent().getQuestions(),
            getComponent().getSelectedQuestion(),
            mention
        ));
    }

}
