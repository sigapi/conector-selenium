package br.edu.fatecsbc.sigapi.conector.selenium.helper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupHelper {

    private final Document doc;

    private JsoupHelper(final String html) {
        doc = Jsoup.parse(html);
    }

    public static final JsoupHelper create(final String html) {
        if (StringUtils.isNotBlank(html)) {
            return new JsoupHelper(html);
        }

        return null;
    }

    public Document getDocument() {
        return doc;
    }

    public String getImageSrcById(final String id) {

        final Element element = getElementById(id);
        if (element != null) {

            Element image = null;
            if (StringUtils.equalsIgnoreCase("img", element.tagName())) {
                image = element;
            } else {
                final Elements elements = element.getElementsByTag("img");
                if (elements != null) {
                    image = elements.first();
                }
            }

            if (image != null) {
                final String src = StringUtils.trimToNull(image.attr("src"));
                if (src != null) {
                    // http://stackoverflow.com/a/26842113
                    return src.replaceAll("(?<!(http:|https:))[//]+", "/");
                }
            }
        }

        return null;

    }

    public float getTextAsFloatFromElementById(final String id) {

        final String text = getTextFromElementById(id);
        return NumberUtils.toFloat(text);

    }

    public String getTextFromElementById(final String id) {
        return getTextFromElementById(id, false);
    }

    public String getTextFromElementById(final String id, final boolean capitalize) {

        final Element elementById = getElementById(id);
        if (elementById != null) {

            String text = elementById.text();
            text = StringUtils.trimToNull(text);

            if (capitalize) {
                return StringHelper.capitalize(text);
            }

            return text;

        }

        return null;

    }

    public Element getElementById(final String id) {
        return getElement(String.format("#%s", id));
    }

    public Element getElement(final String selector) {

        final Elements elements = getElements(selector);
        if (elements != null) {
            return elements.first();
        }

        return null;

    }

    public Elements getElementsByClass(final String className) {
        return getElements(String.format(".%s", className));
    }

    private Elements getElements(final String selector) {
        return doc.select(selector);
    }

}
