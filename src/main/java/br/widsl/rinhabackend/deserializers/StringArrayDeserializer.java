package br.widsl.rinhabackend.deserializers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import br.widsl.rinhabackend.exception.impl.BadRequestException;

@Component
public class StringArrayDeserializer extends JsonDeserializer<String[]> {

    @Override
    public String[] deserialize(JsonParser p, DeserializationContext context) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (node == null)
            return new String[0];

        if (node.isArray()) {
            int size = node.size();
            String[] result = new String[size];
            for (int i = 0; i < size; i++) {
                JsonNode element = node.get(i);
                if (!element.isTextual()) {
                    throw new BadRequestException();
                }
                result[i] = element.asText();
            }
            return result;
        } else {
            throw new BadRequestException();
        }
    }
}
