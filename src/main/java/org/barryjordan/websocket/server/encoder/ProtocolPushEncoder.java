package org.barryjordan.websocket.server.encoder;

import org.barryjordan.websocket.server.protocol.ProtocolPush;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

/**
 * Encodes a ProtocolPush as a JSON String.
 */
public class ProtocolPushEncoder implements Encoder.Text<ProtocolPush> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String encode(final ProtocolPush protocolPush) throws EncodeException {
        try {
            return objectMapper.writeValueAsString(protocolPush);
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public void init(final EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
