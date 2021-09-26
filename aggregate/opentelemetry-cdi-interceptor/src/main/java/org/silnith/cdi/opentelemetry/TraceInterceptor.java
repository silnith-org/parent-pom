package org.silnith.cdi.opentelemetry;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.Tracer;


/**
 * An interceptor that records details of the invocation and exports those
 * details using OpenTelemetry.
 */
@Traced
@Interceptor
public class TraceInterceptor {

    /**
     * Absolute file path of the code.
     */
    private static final AttributeKey<String> CODE_FILEPATH = AttributeKey.stringKey("code.filepath");
    /**
     * Function or method name.
     */
    private static final AttributeKey<String> CODE_FUNCTION = AttributeKey.stringKey("code.function");
    /**
     * Line number of the code.
     */
    private static final AttributeKey<Long> CODE_LINENO = AttributeKey.longKey("code.lineno");
    /**
     * Class name or namespace.
     */
    private static final AttributeKey<String> CODE_NAMESPACE = AttributeKey.stringKey("code.namespace");
    /**
     * {@code true} if the exception propagates beyond the interceptor.
     */
    private static final AttributeKey<Boolean> EXCEPTION_ESCAPED = AttributeKey.booleanKey("exception.escaped");
    /**
     * The exception message.
     */
    private static final AttributeKey<String> EXCEPTION_MESSAGE = AttributeKey.stringKey("exception.message");
    /**
     * The stack trace.
     */
    private static final AttributeKey<String> EXCEPTION_STACKTRACE = AttributeKey.stringKey("exception.stacktrace");
    /**
     * Exception type.
     */
    private static final AttributeKey<String> EXCEPTION_TYPE = AttributeKey.stringKey("exception.type");
    /**
     * String name for the managed thread.
     */
    private static final AttributeKey<String> THREAD_NAME = AttributeKey.stringKey("thread.name");
    /**
     * Numeric identifier for the managed thread.
     */
    private static final AttributeKey<Long> THREAD_ID = AttributeKey.longKey("thread.id");
    /**
     * The network transport.
     */
    private static final AttributeKey<String> NET_TRANSPORT = AttributeKey.stringKey("net.transport");

    private Tracer tracer;

    @Inject
    public TraceInterceptor() {
        super();
    }

    @Inject
    public void setTracer(@OpenTelemetryCDI final Tracer tracer) {
        this.tracer = tracer;
    }

    @AroundInvoke
    public Object aroundInvoke(final InvocationContext invocationContext) throws Exception {
        final Method method = invocationContext.getMethod();
        final Class<?> declaringClass = method.getDeclaringClass();
        final Thread currentThread = Thread.currentThread();

        final SpanBuilder spanBuilder = tracer.spanBuilder(method.toGenericString());
        spanBuilder.setAttribute(NET_TRANSPORT, "inproc");
        spanBuilder.setAttribute(THREAD_ID, currentThread.getId());
        spanBuilder.setAttribute(THREAD_NAME, currentThread.getName());
        spanBuilder.setAttribute(CODE_FUNCTION, method.getName());
        spanBuilder.setAttribute(CODE_NAMESPACE, declaringClass.getTypeName());
        final Span span = spanBuilder.startSpan();
        try {
            return invocationContext.proceed();
        } catch (final Exception e) {
            final AttributesBuilder attributesBuilder = Attributes.builder();
            attributesBuilder.put(EXCEPTION_TYPE, e.getClass().getTypeName());
            attributesBuilder.put(EXCEPTION_MESSAGE, e.getLocalizedMessage());
            attributesBuilder.put(EXCEPTION_STACKTRACE, buildStackTrace(Arrays.asList(e.getStackTrace())));
            attributesBuilder.put(EXCEPTION_ESCAPED, true);
            span.recordException(e, attributesBuilder.build());
            throw e;
        } finally {
            span.end();
        }
    }

    private String buildStackTrace(final List<StackTraceElement> stackTraceElements) {
        return String.join("\n",
                stackTraceElements.stream().map(StackTraceElement::toString).collect(Collectors.toList()));
    }

}
