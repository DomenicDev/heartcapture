package de.cassisi.hearth.ui.lang;


/**
 * Helper interface to easily get strings from the language resource bundle.
 */
public interface LanguageReceiver {

    /**
     * Returns the string value for the specified key of the default language resource bundle.
     * @param key the key to get the value from
     * @return the value for the specified key.
     */
    default String getString(String key) {
        return LanguageResourceProvider.getLanguageBundle().getString(key);
    }


}
