package org.silnith.render;

/**
 * The base exception for rendering problems.
 */
public class RenderException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public RenderException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public RenderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public RenderException(final String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public RenderException(final Throwable cause) {
        super(cause);
    }

}
