package biz.rightshift.websocket.server;

import biz.rightshift.websocket.server.config.ContextConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *  Application entry point.
 */
public final class WebSocketServer {

    /**
     * Supress instantiation.
     */
    private WebSocketServer() {
        throw new IllegalStateException();
    }

    /**
     * Creates Spring context.
     *
     * @param args The command line arguments.
     */
    public static void main(final String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ContextConfig.class);
        context.refresh();
        context.registerShutdownHook();
    }
}
