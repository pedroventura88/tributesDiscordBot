package br.com.discordBot.tributes.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    DataUtils() {
        throw new IllegalStateException("Utilitary Class!");
    }

    static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    static final DateTimeFormatter DATE_TIME_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static final DateTimeFormatter DATE_TIME_BR_HR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static String convertLocalDateToString(LocalDate data) {
        return data.format(DATE_TIME);
    }

    public static String convertLocalDateToDateBr (LocalDateTime data) {
        return data.format(DATE_TIME_BR);
    }

    public static String convertLocalDateTimeToBRFull(LocalDateTime data) {
        return data.format(DATE_TIME_BR_HR);
    }
}
