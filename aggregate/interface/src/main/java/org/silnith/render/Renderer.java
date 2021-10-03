package org.silnith.render;

import org.w3c.dom.Document;


/**
 * A service that can render views of objects.
 */
public interface Renderer {

    /**
     * Render the object into a document.
     * 
     * @param object the object to render
     * @return a document representing the object
     * @throws RenderException if there was any problem generating the document
     */
    Document renderAsDocument(Object object) throws RenderException;

}
