package br.edu.fatecsbc.sigapi.conector.selenium.helper;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class StringHelper {

    private static final Collection<String> CAPITALIZE_IGNORE = Arrays.asList("De", "Da", "Do", "A", "O", "E", "Em",
        "Para");

    private StringHelper() {}

    public static final String capitalize(final String s) {

        final String text = StringUtils.trimToNull(s);

        if (text != null) {

            String result = WordUtils.capitalizeFully(text, '"', ' ');

            for (final String ignored : CAPITALIZE_IGNORE) {
                result = result.replace(String.format(" %s ", ignored), String.format(" %s ", ignored.toLowerCase()));
            }

            return result;
        }

        return text;

    }

}
