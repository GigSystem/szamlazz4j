package hu.gigsystem.szamlazz4j.model.invoice.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * Enum for supported invoice languages.
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@AllArgsConstructor
public enum InvoiceLanguage {
    HU("hu"),
    EN("en"),
    DE("de"),
    IT("it"),
    RO("ro"),
    SK("sk"),
    HR("hr"),
    FR("fr"),
    ES("es"),
    CZ("cz"),
    PL("pl"),
    BG("bg"),
    NL("nl"),
    RU("ru"),
    SI("si");

    private final String code;

    /**
     * @return the language code used by Szamlazz API.
     */
    @JsonValue
    public String getCode() {
        return code;
    }
}