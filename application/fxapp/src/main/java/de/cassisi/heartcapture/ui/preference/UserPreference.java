package de.cassisi.heartcapture.ui.preference;

import java.util.prefs.Preferences;

public class UserPreference {

    private final Preferences preferences = Preferences.userRoot();

    private static final UserPreference userPreference = new UserPreference();

    private UserPreference() {}

    public static UserPreference getInstance() {
        return userPreference;
    }

    public Preferences getPreferences() {
        return preferences;
    }
}
