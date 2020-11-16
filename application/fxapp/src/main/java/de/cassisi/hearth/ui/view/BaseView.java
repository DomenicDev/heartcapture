package de.cassisi.hearth.ui.view;

import com.google.common.eventbus.EventBus;
import de.cassisi.hearth.ui.enums.MessageType;
import de.cassisi.hearth.ui.event.ShowMessageEvent;
import de.cassisi.hearth.ui.lang.LanguageResourceProvider;
import de.cassisi.hearth.ui.preference.UserPreference;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import javafx.stage.Window;

import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public abstract class BaseView {

    private final EventBus eventBus = EventBusProvider.getEventBus();
    private final ResourceBundle resourceBundle = LanguageResourceProvider.getLanguageBundle();
    private final Preferences preferences = UserPreference.getInstance().getPreferences();

    protected void post(Object o) {
        if (o != null) {
            eventBus.post(o);
        }
    }

    public Preferences getPreferences() {
        return preferences;
    }

    protected String getString(String key) {
        return resourceBundle.getString(key);
    }

    protected Window getWindow() {
        throw new UnsupportedOperationException("You need to override this method in the subclass for proper usage!");
    }

    protected void postWarningMessage(String message) {
        post(new ShowMessageEvent(message, MessageType.WARNING));
    }

}
