package hu.gigsystem.szamlazz4j.model.invoice.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * Enum for supported freight providers
 *
 * @author Tamás Tóth
 * @version 1.0.0
 * @since 5th of July, 2025
 */
@AllArgsConstructor
public enum FreightProvider {
    TOF("TOF"),
    PPP("PPP"),
    SPRINTER("SPRINTER"),
    FOXPOST("FOXPOST"),
    MPL("MPL"),
    GLS("GLS"),
    EMPTY("EMPTY");

    private final String code;

    /**
     * @return the freight provider code used by Szamlazz API.
     */
    @JsonValue
    public String getCode() {
        return code;
    }
}