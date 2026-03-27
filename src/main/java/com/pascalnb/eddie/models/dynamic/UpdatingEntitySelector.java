package com.pascalnb.eddie.models.dynamic;

import com.pascalnb.eddie.models.EddieComponent;
import com.pascalnb.eddie.models.EddieEntitySelector;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;

public abstract class UpdatingEntitySelector<T extends EddieComponent & UpdatingComponent<T>> extends
    EddieEntitySelector<T>
    implements UpdatingSubcomponent<EntitySelectInteractionEvent, T> {

    public UpdatingEntitySelector(T component, String id) {
        super(component, id);
    }

}

