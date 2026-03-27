package com.pascalnb.eddie.components.faq.answer;

import com.pascalnb.eddie.components.faq.FaqComponent;
import com.pascalnb.eddie.models.ComponentConfig;
import com.pascalnb.eddie.models.EddieComponentFactory;
import com.pascalnb.eddie.models.dynamic.DynamicComponent;
import com.pascalnb.eddie.models.dynamic.DynamicRegister;
import com.pascalnb.eddie.models.dynamic.UpdatingComponent;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.actionrow.ActionRowChildComponent;
import net.dv8tion.jda.api.components.container.Container;
import net.dv8tion.jda.api.components.container.ContainerChildComponent;
import net.dv8tion.jda.api.components.separator.Separator;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FaqAnswerComponent extends DynamicComponent<FaqAnswerComponent>
    implements UpdatingComponent<FaqAnswerComponent> {

    private final FaqComponent component;
    private final Collection<FaqComponent.Question> questions;
    private final FaqComponent.Question selectedQuestion;
    private final IMentionable mention;

    private FaqAnswerSelectMenu questionSelectMenu;
    private FaqMentionSelectMenu mentionSelectMenu;
    private FaqAnswerSubmitButton submitButton;
    private FaqAnswerCancelButton cancelButton;

    public FaqAnswerComponent(ComponentConfig config, FaqComponent component, DynamicRegister dynamicRegister,
        Collection<FaqComponent.Question> questions) {
        this(config, component, dynamicRegister, questions, null, null);
    }

    public FaqAnswerComponent(ComponentConfig config, FaqComponent component, DynamicRegister dynamicRegister,
        Collection<FaqComponent.Question> questions, @Nullable FaqComponent.Question selectedQuestion,
        @Nullable IMentionable mention
    ) {
        super(config, dynamicRegister);
        this.component = component;
        this.questions = new ArrayList<>(questions);
        this.selectedQuestion = selectedQuestion;
        this.mention = mention;

        questionSelectMenu = createDynamic("question", FaqAnswerSelectMenu::new);
        mentionSelectMenu = createDynamic("mention", FaqMentionSelectMenu::new);
        submitButton = createDynamic("submit", FaqAnswerSubmitButton::new);
        cancelButton = createDynamic("cancel", FaqAnswerCancelButton::new);
    }

    public static EddieComponentFactory<FaqAnswerComponent> factory(FaqComponent component, DynamicRegister dynamic,
        Collection<FaqComponent.Question> questions) {
        return config -> new FaqAnswerComponent(config, component, dynamic, questions);
    }

    @Override
    public MessageCreateData getMessage() {
        List<ContainerChildComponent> components = new ArrayList<>();

        if (this.questions.isEmpty()) {
            components.add(
                TextDisplay.of("No FAQs yet. Use the button below to create a new FAQ.")
            );
        } else {
            components.addAll(List.of(
                ActionRow.of(
                    questionSelectMenu.getEntity()
                ),
                ActionRow.of(
                    mentionSelectMenu.getEntity()
                ),
                Separator.createDivider(Separator.Spacing.LARGE)
            ));
        }

        List<ActionRowChildComponent> bottomButtons = new ArrayList<>(List.of(
            cancelButton.getEntity()
        ));

        if (this.selectedQuestion != null) {
            bottomButtons.add(submitButton.getEntity());
        }

        components.add(
            ActionRow.of(bottomButtons)
        );

        Container container = Container.of(components);

        return new MessageCreateBuilder()
            .useComponentsV2()
            .setComponents(container)
            .build();
    }

    @Override
    public EddieComponentFactory<FaqAnswerComponent> getCloningFactory() {
        return factory(this.questions, this.selectedQuestion, this.mention);
    }

    public EddieComponentFactory<FaqAnswerComponent> factory(Collection<FaqComponent.Question> questions,
        @Nullable FaqComponent.Question selectedQuestion,
        @Nullable IMentionable mention) {
        return factory(this.component, getDynamic(), questions, selectedQuestion, mention);
    }

    public static EddieComponentFactory<FaqAnswerComponent> factory(FaqComponent component, DynamicRegister dynamic,
        Collection<FaqComponent.Question> questions,
        @Nullable FaqComponent.Question selectedQuestion,
        @Nullable IMentionable mention) {
        return config -> new FaqAnswerComponent(config, component, dynamic, questions, selectedQuestion, mention);
    }

    public Collection<FaqComponent.Question> getQuestions() {
        return questions;
    }

    public @Nullable FaqComponent.Question getSelectedQuestion() {
        return selectedQuestion;
    }

    public @Nullable IMentionable getMention() {
        return mention;
    }

    @Override
    public FaqAnswerComponent getComponent() {
        return this;
    }

    public FaqComponent getParentComponent() {
        return this.component;
    }

}
