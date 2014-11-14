package biz.rightshift.websocket.server.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Maintains sessions locally.
 */
public class ConnectionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);
    private static final Map<String, Session> CONNECTIONS = new ConcurrentHashMap<>();

    /**
     * Adds a webscocket connections.
     *
     * @param session The session.
     */
    public void addConnection(final Session session) {
        CONNECTIONS.put(session.getId(), session);
        LOGGER.info("Connection Added: {}", session.getId());
    }

    /**
     * Removes a websocket connections.
     *
     * @param session The websocket connections.
     */
    public void removeConnection(final Session session) {
        CONNECTIONS.remove(session.getId());
        LOGGER.info("Connection Removed: {}", session.getId());
    }

    /**
     * Clears all connections.
     */
    public void clearAll() {
        CONNECTIONS.clear();
    }

    /**
     *
     * @return The number of active connections.
     */
    public int numConnections() {
        return CONNECTIONS.size();
    }
}
