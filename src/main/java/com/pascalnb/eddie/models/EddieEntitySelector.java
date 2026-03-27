package com.pascalnb.eddie.models;

import net.dv8tion.jda.api.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;

public abstract class EddieEntitySelector<T extends EddieComponent> extends
    EddieSubcomponentBase<EntitySelectMenu, EntitySelectInteractionEvent, T> {

    public EddieEntitySelector(T component, String id) {
        super(component, id);
    }

    @Override
    public Class<EntitySelectInteractionEvent> getEventType() {
        return EntitySelectInteractionEvent.class;
    }

    @Override
    public Class<EntitySelectMenu> getEntityType() {
        return EntitySelectMenu.class;
    }

}
