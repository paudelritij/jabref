package org.jabref.logic.formatter.minifier;

import java.util.Objects;

import org.jabref.logic.cleanup.Formatter;
import org.jabref.logic.l10n.Localization;

/**
 * Replaces three or more authors with and others
 */
public class MinifyNameListFormatter extends Formatter {
    @Override
    public String getName() {
        return Localization.lang("Minify list of person names");
    }

    @Override
    public String getKey() {
        return "minify_name_list";
    }

    /**
     * Replaces three or more authors with and others.
     *
     * <h4>Example</h4>
     * <pre>{@code
     *     Stefan Kolb -> Stefan Kolb
     *     Stefan Kolb and Simon Harrer -> Stefan Kolb and Simon Harrer
     *     Stefan Kolb and Simon Harrer and Jörg Lenhard -> Stefan Kolb and others
     * }</pre>
     */
    @Override
    public String format(String value) {
        Objects.requireNonNull(value);

        if (value.isEmpty()) {
            // nothing to do
            return value;
        }

        return abbreviateAuthor(value);
    }

    @Override
    public String getDescription() {
        return Localization.lang("Shortens lists of persons if there are more than 2 persons to \"et al.\".");
    }

    @Override
    public String getExampleInput() {
        return "Stefan Kolb and Simon Harrer and Oliver Kopp";
    }

    private String abbreviateAuthor(String authorField) {
        // single author
        String authorSeparator = " and ";

        if (!authorField.contains(authorSeparator)) {
            return authorField;
        }

        String[] authors = authorField.split(authorSeparator);

        // trim authors
        for (int i = 0; i < authors.length; i++) {
            authors[i] = authors[i].trim();
        }

        // already abbreviated
        if ("others".equals(authors[authors.length - 1]) && (authors.length == 2)) {
            return authorField;
        }

        // abbreviate
        if (authors.length < 3) {
            return authorField;
        }

        return authors[0] + authorSeparator + "others";
    }
}
