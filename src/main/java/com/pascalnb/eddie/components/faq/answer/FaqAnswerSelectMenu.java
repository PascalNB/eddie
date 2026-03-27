package com.pascalnb.eddie.components.faq.answer;

import com.pascalnb.eddie.components.faq.FaqComponent;
import com.pascalnb.eddie.components.faq.FaqSelector;
import com.pascalnb.eddie.models.dynamic.UpdatingStringSelector;
import net.dv8tion.jda.api.components.selections.SelectOption;
import net.dv8tion.jda.api.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class FaqAnswerSelectMenu extends UpdatingStringSelector<FaqAnswerComponent> {

    private final Map<String, FaqComponent.Question> mappedQuestions;
    private final List<SelectOption> options;

    public FaqAnswerSelectMenu(FaqAnswerComponent component, String id) {
        super(component, id);

        this.mappedQuestions = FaqSelector.mapQuestions(getComponent().getQuestions());
        this.options = FaqSelector.createOptions(mappedQuestions);
    }

    @Override
    public StringSelectMenu getEntity() {
        StringSelectMenu.Builder builder = StringSelectMenu.create(getId())
            .addOptions(options)
            .setMinValues(1)
            .setMaxValues(1);

        if (getComponent().getSelectedQuestion() != null) {
            String selectedValue = mappedQuestions.entrySet().stream()
                .filter(entry -> entry.getValue().equals(getComponent().getSelectedQuestion()))
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow();
            builder.setDefaultValues(selectedValue);
        }

        return builder.build();
    }

    @Override
    public @Nullable FaqAnswerComponent apply(StringSelectInteractionEvent event, InteractionHook hook) {
        if (event.getValues().isEmpty()) {
            return createComponent(getComponent().factory(
                getComponent().getQuestions(),
                null,
                getComponent().getMention()
            ));
        }

        String value = event.getValues().getFirst();
        FaqComponent.Question question = mappedQuestions.get(value);

        return createComponent(getComponent().factory(
            getComponent().getQuestions(),
            question,
            getComponent().getMention()
        ));
    }

}
