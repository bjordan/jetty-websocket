package org.barryjordan.websocket.server.encoder;

import org.barryjordan.websocket.server.protocol.ProtocolResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

/**
 * Encodes response object as a JSON String.
 */
public class ProtocolResponseEncoder implements Encoder.Text<ProtocolResponse> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String encode(final ProtocolResponse protocolResponse) throws EncodeException {
        try {
            return objectMapper.writeValueAsString(protocolResponse);
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
