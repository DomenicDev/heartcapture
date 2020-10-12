package de.cassisi.hearth.ui.utils;

import com.google.common.eventbus.EventBus;

public final class EventBusProvider {

    private static final EventBus EVENTBUS = new EventBus("GUI-EVENT-BUS");

    public static EventBus getEventBus() {
        return EVENTBUS;
    }

}
