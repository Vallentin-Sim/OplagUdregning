package GUI;

public class LanguageSettings {
    private static final LanguageSettings instance = new LanguageSettings();

    private Language language = Language.DANISH;

    private LanguageSettings() {
    }

    public static LanguageSettings getInstance() {
        return instance;
    }

    public Language getLanguage() {
        return language;
    }

    public void toggleLanguage() {
        language = language == Language.DANISH
                ? Language.ENGLISH
                : Language.DANISH;
    }
}
