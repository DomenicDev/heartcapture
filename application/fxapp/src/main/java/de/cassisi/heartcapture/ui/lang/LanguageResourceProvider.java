package de.cassisi.heartcapture.ui.lang;

import java.util.ResourceBundle;

public class LanguageResourceProvider {

    private static final ResourceBundle LANGUAGE_BUNDLE = ResourceBundle.getBundle("lang/lang");

    public static ResourceBundle getLanguageBundle() {
        return LANGUAGE_BUNDLE;
    }

}
