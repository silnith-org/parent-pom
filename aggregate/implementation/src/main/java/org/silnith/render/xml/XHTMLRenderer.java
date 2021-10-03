package org.silnith.render.xml;

import java.io.StringReader;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Typed;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.xml.XMLConstants;

import org.silnith.render.RenderException;
import org.silnith.render.Renderer;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;


/**
 * A {@link Renderer} that produces XHTML.
 */
@XHTML
@ApplicationScoped
@Typed({ Renderer.class, })
public class XHTMLRenderer implements Renderer {

    private static final String XHTML_NAMESPACE_URI = "http://www.w3.org/1999/xhtml";
    private static final String MATHML_NAMESPACE_URI = "http://www.w3.org/1998/Math/MathML";

    private final String xhtmlPrefix = "";

    private final DOMImplementation domImplementation;
    private final Jsonb jsonb;
    private final JsonReaderFactory jsonReaderFactory;

    @Inject
    public XHTMLRenderer(final DOMImplementationRegistry domImplementationRegistry, final Jsonb jsonb,
            final JsonReaderFactory jsonReaderFactory) {
        super();
        domImplementation = domImplementationRegistry.getDOMImplementation("XML 1.0");
        this.jsonb = jsonb;
        this.jsonReaderFactory = jsonReaderFactory;
    }

    private JsonValue convertToJsonValue(final Object object) {
        if (object == null) {
            return JsonValue.NULL;
        }

        final String json = jsonb.toJson(object);
        try (final JsonReader jsonReader = jsonReaderFactory.createReader(new StringReader(json))) {
            return jsonReader.readValue();
        }
    }

    @Override
    public Document renderAsDocument(final Object object) throws RenderException {
        final DocumentType documentType = domImplementation.createDocumentType(createQualifiedName(xhtmlPrefix, "html"),
                "-//W3C//DTD XHTML 1.0 Strict//EN", "http://www.w3.org/2002/08/xhtml/xhtml1-strict.dtd");
        final Document document = domImplementation.createDocument(XHTML_NAMESPACE_URI,
                createQualifiedName(xhtmlPrefix, "html"), documentType);

        final Element titleElement = document.createElementNS(XHTML_NAMESPACE_URI,
                createQualifiedName(xhtmlPrefix, "title"));
        final String typeName = getTypeName(object);
        titleElement.appendChild(document.createTextNode(typeName));

        final Element headElement = document.createElementNS(XHTML_NAMESPACE_URI,
                createQualifiedName(xhtmlPrefix, "head"));
        headElement.appendChild(titleElement);

        final Element bodyElement = document.createElementNS(XHTML_NAMESPACE_URI,
                createQualifiedName(xhtmlPrefix, "body"));
        bodyElement.appendChild(renderValue(document, convertToJsonValue(object)));

        final String languageTag = Locale.US.toLanguageTag();

        final Element htmlElement = document.getDocumentElement();
        if (xhtmlPrefix == null || xhtmlPrefix.isEmpty()) {
            htmlElement.setAttribute("lang", languageTag);
        } else {
            htmlElement.setAttributeNS(XHTML_NAMESPACE_URI, createQualifiedName(xhtmlPrefix, "lang"), languageTag);
        }
        htmlElement.setAttributeNS(XMLConstants.XML_NS_URI, createQualifiedName(XMLConstants.XML_NS_PREFIX, "lang"),
                languageTag);
        htmlElement.setAttributeNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "xsi:schemaLocation", String.format(
                Locale.ENGLISH, "%s %s", XHTML_NAMESPACE_URI, "http://www.w3.org/2002/08/xhtml/xhtml1-strict.xsd"));
        htmlElement.appendChild(headElement);
        htmlElement.appendChild(bodyElement);

        return document;
    }

    private String getTypeName(final Object object) {
        if (object == null) {
            return "null";
        } else {
            return object.getClass().getTypeName();
        }
    }

    private Element renderObject(final Document document, final JsonObject jsonObject) {
        final Element dlElement = document.createElementNS(XHTML_NAMESPACE_URI, createQualifiedName(xhtmlPrefix, "dl"));

        for (final Map.Entry<String, JsonValue> entry : jsonObject.entrySet()) {
            final String key = entry.getKey();
            final JsonValue value = entry.getValue();

            final Element dtElement = document.createElementNS(XHTML_NAMESPACE_URI,
                    createQualifiedName(xhtmlPrefix, "dt"));
            dtElement.appendChild(document.createTextNode(key));

            final Element ddElement = document.createElementNS(XHTML_NAMESPACE_URI,
                    createQualifiedName(xhtmlPrefix, "dd"));
            ddElement.appendChild(renderValue(document, value));

            dlElement.appendChild(dtElement);
            dlElement.appendChild(ddElement);
        }

        return dlElement;
    }

    private Element renderArray(final Document document, final JsonArray jsonArray) {
        final Element olElement = document.createElementNS(XHTML_NAMESPACE_URI, createQualifiedName(xhtmlPrefix, "ol"));

        for (final JsonValue value : jsonArray) {
            final Element liElement = document.createElementNS(XHTML_NAMESPACE_URI,
                    createQualifiedName(xhtmlPrefix, "li"));
            liElement.appendChild(renderValue(document, value));

            olElement.appendChild(liElement);
        }

        return olElement;
    }

    private Element renderValue(final Document document, final JsonValue jsonValue) {
        switch (jsonValue.getValueType()) {
        case ARRAY:
            return renderArray(document, jsonValue.asJsonArray());
        case OBJECT:
            return renderObject(document, jsonValue.asJsonObject());
        default: {
            final Element pElement = document.createElementNS(XHTML_NAMESPACE_URI,
                    createQualifiedName(xhtmlPrefix, "p"));
            pElement.appendChild(document.createTextNode(jsonValue.toString()));
            return pElement;
        }
        }
    }

    private String createQualifiedName(final String prefix, final String tag) {
        if (prefix == null || prefix.isEmpty()) {
            return tag;
        } else {
            return String.join(":", prefix, tag);
        }
    }

}
