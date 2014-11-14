package biz.rightshift.websocket.server.encoder;

import biz.rightshift.websocket.server.exception.ProtocolException;
import biz.rightshift.websocket.server.protocol.ProtocolRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

/**
 * Decodes the request String.
 */
public class ProtocolRequestDecoder implements Decoder.Text<ProtocolRequest> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ProtocolRequest decode(final String s) throws DecodeException {
        try {
            return objectMapper.readValue(s, ProtocolRequest.class);
        } catch (IOException ex) {
            throw new ProtocolException("Invalid request - malformed.");
        }
    }

    @Override
    public boolean willDecode(final String s) {
        return false;
    }

    @Override
    public void init(final EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
