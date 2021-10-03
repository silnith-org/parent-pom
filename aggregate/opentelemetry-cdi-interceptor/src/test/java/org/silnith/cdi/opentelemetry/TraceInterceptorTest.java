package org.silnith.cdi.opentelemetry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.Tracer;


@ExtendWith({ MockitoExtension.class, })
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class TraceInterceptorTest {

    @Mock
    Tracer tracer;

    @Mock
    SpanBuilder spanBuilder;

    @Mock
    Span span;

    TraceInterceptor traceInterceptor;

    @Mock
    InvocationContext invocationContext;

    static Method method;

    public int name() {
        return 1;
    }

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        method = TraceInterceptorTest.class.getDeclaredMethod("name");
    }

    @BeforeEach
    void setUp() throws Exception {
        traceInterceptor = new TraceInterceptor();
        traceInterceptor.setTracer(tracer);
    }

    @Test
    void testAroundInvoke() throws Exception {
        final Object result = new Object();
        Mockito.when(invocationContext.proceed()).thenReturn(result);
        Mockito.when(invocationContext.getMethod()).thenReturn(method);

        Mockito.when(tracer.spanBuilder(ArgumentMatchers.anyString())).thenReturn(spanBuilder);

        Mockito.when(spanBuilder.startSpan()).thenReturn(span);

        final Object actual = traceInterceptor.aroundInvoke(invocationContext);

        assertEquals(result, actual);

        Mockito.verify(invocationContext).proceed();

        Mockito.verify(tracer).spanBuilder(
                ArgumentMatchers.eq("public int org.silnith.cdi.opentelemetry.TraceInterceptorTest.name()"));

        Mockito.verify(spanBuilder, Mockito.times(4)).setAttribute(ArgumentMatchers.any(AttributeKey.class),
                ArgumentMatchers.anyString());
        Mockito.verify(spanBuilder, Mockito.times(1)).setAttribute(ArgumentMatchers.any(AttributeKey.class),
                ArgumentMatchers.anyLong());
        Mockito.verify(spanBuilder).startSpan();

        Mockito.verify(span).end();
    }

    @Test
    void testAroundInvokeException() throws Exception {
        Mockito.when(invocationContext.proceed()).thenThrow(Exception.class);
        Mockito.when(invocationContext.getMethod()).thenReturn(method);

        Mockito.when(tracer.spanBuilder(ArgumentMatchers.anyString())).thenReturn(spanBuilder);

        Mockito.when(spanBuilder.startSpan()).thenReturn(span);

        assertThrows(Exception.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                traceInterceptor.aroundInvoke(invocationContext);
            }

        });

        Mockito.verify(invocationContext).proceed();

        Mockito.verify(tracer).spanBuilder(
                ArgumentMatchers.eq("public int org.silnith.cdi.opentelemetry.TraceInterceptorTest.name()"));

        Mockito.verify(spanBuilder, Mockito.times(4)).setAttribute(ArgumentMatchers.any(AttributeKey.class),
                ArgumentMatchers.anyString());
        Mockito.verify(spanBuilder, Mockito.times(1)).setAttribute(ArgumentMatchers.any(AttributeKey.class),
                ArgumentMatchers.anyLong());
        Mockito.verify(spanBuilder).startSpan();

        Mockito.verify(span).recordException(ArgumentMatchers.any(Exception.class),
                ArgumentMatchers.any(Attributes.class));
        Mockito.verify(span).end();
    }

}
