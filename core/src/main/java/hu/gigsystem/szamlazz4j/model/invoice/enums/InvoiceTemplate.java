package hu.gigsystem.szamlazz4j.model.invoice.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * Enum for supported invoice templates.
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 3rd of July, 2025
 */
@AllArgsConstructor
public enum InvoiceTemplate {
    SZLA_MOST("SzlaMost"),
    SZLA_ALAP("SzlaAlap"),
    SZLA_NO_ENV("SzlaNoEnv"),
    SZLA_8CM("Szla8cm"),
    SZLA_TOMB("SzlaTomb"),
    SZLA_FUVARLEVELES_ALAP("SzlaFuvarlevelesAlap");

    private final String code;

    /**
     * @return the code used by Szamlazz API.
     */
    @JsonValue
    public String getCode() {
        return code;
    }
}