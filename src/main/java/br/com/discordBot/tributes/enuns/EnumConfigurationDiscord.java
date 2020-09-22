package br.com.discordBot.tributes.enuns;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Slf4j
public enum EnumConfigurationDiscord implements Serializable {

    DISCORD_TOKEN("discord.bot.token");

    private String key;
    private boolean mandatory;
    private String defaultValue;

    EnumConfigurationDiscord(String key) {
        this(key, true);
    }

    EnumConfigurationDiscord(String key, boolean mandatory, String defaultValue) {
        this.key = key;
        this.mandatory = mandatory;
        this.defaultValue = defaultValue;
    }

    EnumConfigurationDiscord(String key, boolean mandatory) {
        this(key, mandatory, null);
    }

    public String getKey() {
        return key;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public final String getValue() {
        String value = System.getProperty(this.getKey());
        if (StringUtils.isEmpty(value)) {
            if (isMandatory()) {
                log.warn("Property " + toString() + " = [" + value + "]" + " not found!");
            }
            value = defaultValue;
        }
        return value;
    }

    public final boolean asBoolean() {
        try {
            return Boolean.parseBoolean(getValue());
        } catch (Exception e) {
            log.warn("Failed to convert property value [" + toString() + "] to BOOLEAN!", e);
        }
        return false;
    }

    public final long asLong() {
        try {
            return Long.parseLong(getValue().replace("_", ""));
        } catch (Exception e) {
            log.warn("Failed to convert property value [" + toString() + "] to LONG!");
        }
        return -1L;
    }
}
